package com.xygj.app.jinrirong.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xygj.app.R;
import com.xygj.app.common.utils.LogUtil;
import com.xygj.app.common.widget.CustomDialog;
import com.xygj.app.jinrirong.common.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.Intent.EXTRA_TITLE;

public class MyWebViewActivity extends BaseActivity {

    private static final String EXTRA_TYPE = "mCurrentType";

    //认证相关type，需要检测服务器返回的 json 字符串
    public static final int TYPE_BANK_CERT = 0x11;
    public static final int TYPE_MOBILE_CERT = 0x22;

    //银行支付type，需要检测服务器返回的 json 字符串
    public static final int TYPE_BANK_PAYMENT = 0x33;

    //当前打开的type类型，-1 为默认类型，表示仅打开url/文本
    private int mCurrentType = -1;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.pb_web)
    ProgressBar mProgressBar;
    @BindView(R.id.wv_web)
    WebView mWebView;

    String mUrl;
    String mTitle;

    /**
     * 获取intent，启动网页或打开内容
     *
     * @param context
     * @param title   要显示的标题
     * @param url     url、文本/html 片段
     * @return
     */
    public static Intent getIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_TEXT, url);
        return intent;
    }

    /**
     * 获取intent，打开特定的页面，如：在线支付，用于检测服务器返回的结果（json字符串）
     *
     * @param context
     * @param title   要显示的标题
     * @param url     url 地址
     * @param type    本类所定义的常量值，如 TYPE_BANK_PAYMENT
     * @return
     */
    public static Intent getIntent(Context context, String title, String url, int type) {
        return getIntent(context, title, url).putExtra(EXTRA_TYPE, type);
    }

    @Override
    protected int setContentLayoutRes() {
        return R.layout.activity_my_web_view;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView() {
        //获取intent里的值并设置标题
        Intent data = getIntent();
        mUrl = data.getStringExtra(EXTRA_TEXT);
        mTitle = data.getStringExtra(EXTRA_TITLE);
        mCurrentType = data.getIntExtra(EXTRA_TYPE, -1);
        mTvTitle.setText(TextUtils.isEmpty(mTitle) ? getResources().getString(R.string.app_name) : mTitle);
        //WebView 配置相关
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String ua = webSettings.getUserAgentString();
        mWebView.getSettings().setUserAgentString(ua);
        //开启这个以后，就可以访问了 mlgb
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);
//        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(myWebViewClient);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent intent = new Intent(ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        //如果以http为起始，说明是url地址，否则作为文本打开内容
        if (mUrl.startsWith("http")) {
            if (mUrl.contains("Wachet")) {
                Map<String, String> extraHeaders = new HashMap<>();
                extraHeaders.put("Referer", "http://jrr.ahceshi.com");
                mWebView.loadUrl(mUrl, extraHeaders);
            } else {
                mWebView.loadUrl(mUrl);
            }
        } else {
//            mWebView.loadData(mUrl, "text/html;charset=UTF-8", null);
            mWebView.loadDataWithBaseURL(null, getHtmlData(mUrl), "text/html;charset=UTF-8", null, null);
        }
    }


    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body><html>";
    }

    @Override
    protected void initData() {
        requestTranslucentStatusBar(Color.parseColor("#FFFFFF"), true);
    }

    /**
     * 逻辑处理
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
            LogUtil.i(html);
            try {
                html = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
                JSONObject result = new JSONObject(html);
                if (result.optInt("result") == 1) {
                    //如果是银行卡还款/支付结果成功了，跳转到订单状态页面
                    if (mCurrentType == TYPE_BANK_PAYMENT) {
//                        startActivity(new Intent(MyWebViewActivity.this, OrderSucceedActivity.class));
                        MyWebViewActivity.this.finish();
                        return;
                    }
                }
                showResult(result.optString("message"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showResult(String message) {
        Dialog dialog = new CustomDialog.Builder(this)
                .setTitle("提示")
                .setContent(message)
//                .setSingleBtn(true)
                .setListener(new CustomDialog.OnButtonClickListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {

                    }
                }).build();
        dialog.setCancelable(false);
        dialog.show();
    }

    private static final String TAG = "MyWebViewActivity";

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e(TAG, "shouldOverrideUrlLoading: " + url);
            // 微信H5支付协议
            if (url.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                finish();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (mCurrentType == -1) return;
            if (mCurrentType == TYPE_BANK_CERT || mCurrentType == TYPE_MOBILE_CERT || mCurrentType == TYPE_BANK_PAYMENT) {
                view.loadUrl("javascript:window.java_obj.getSource(document.getElementsByTagName('body')[0].innerHTML);");
            }

        }
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
