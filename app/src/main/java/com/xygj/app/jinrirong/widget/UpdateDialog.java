package com.xygj.app.jinrirong.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xygj.app.R;


/**
 * 自定义dialog
 * Created by xuyougen on 2018/3/5.
 * <p>
 * 使用方法
 * new CustomDialog.Builder({@link Context})
 * .setTitle("标题")
 * .setContent("提示内容")
 * .setListener({@link OnButtonClickListener))
 * .build()
 * .show();
 */

public class UpdateDialog extends Dialog {
    public UpdateDialog(@NonNull Context context) {
        super(context);
    }

    public UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置dialog 窗体的宽度
        //getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mContent;
        private String mConfirmText;
        private String mCancelText;

        //单按钮模式(仅确认键)
        private boolean mIsSingleBtn;

        private OnButtonClickListener mListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setListener(OnButtonClickListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setSingleBtn(boolean singleBtn) {
            mIsSingleBtn = singleBtn;
            return this;
        }

        public Builder setConfirmText(String confirmText) {
            mConfirmText = confirmText;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            mCancelText = cancelText;
            return this;
        }

        public UpdateDialog build() {
            final UpdateDialog dialog = new UpdateDialog(mContext, R.style.CustomDialog);

            View layout = View.inflate(mContext, R.layout.layout_update_dialog, null);
            TextView title = layout.findViewById(R.id.tv_title_custom_dialog);
            WebView webView = layout.findViewById(R.id.wv_update);

            //设置标题和内容
            if (mTitle != null) title.setText(mTitle);
            if (mContent != null) webView.loadData(mContent, "text/html;charset=UTF-8", null);


            TextView confirm = layout.findViewById(R.id.tv_confirm_custom_dialog);
            TextView cancel = layout.findViewById(R.id.tv_cancel_custom_dialog);

            if (mConfirmText != null)
                confirm.setText(mConfirmText);

            if (mCancelText != null)
                cancel.setText(mCancelText);

            if (mIsSingleBtn) {
                cancel.setVisibility(View.GONE);
                View line = layout.findViewById(R.id.v_line);
                line.setVisibility(View.GONE);
            }

            //设置监听
            if (mListener != null) {
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onConfirm(dialog);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCancel(dialog);
                    }
                });
            }
            //将布局添加到 dialog 中
            dialog.setContentView(layout);
            return dialog;
        }
    }

    /**
     * 监听接口
     */
    public interface OnButtonClickListener {
        void onConfirm(Dialog dialog);

        void onCancel(Dialog dialog);
    }
}
