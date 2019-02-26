package com.xygj.app.jinrirong.activity.user.changejie;

import android.util.Log;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.config.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChangJieCodePresenter extends BasePresenter<ChangJieCodeView> {


    //    OrderNo 订单号不存在
//    BankNo 卡号不能为空
//    CertNo 证件号不能为空
//    CertName 姓名不能为空
//    CertPhone 手机号不能为空
    public void sendPaySmsCode(String orderNO, String bankNo, String certNo, String certName, String certPhone, ChangJieCodeActivity.PayType payType) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", UserManager.getInstance().getToken());
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            JSONObject data = new JSONObject();
            data.put("OrderNo", orderNO);
            data.put("BankNo", bankNo);
            data.put("CertNo", certNo);
            data.put("CertName", certName);
            data.put("CertPhone", certPhone);
            requestData.put("dynamic", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        getView().showLoadingView();
        switch (payType){
            case ZhengXin:
                addTask(RetrofitHelper.getInstance().getService().changJie_sendPaySmsCodeZX(requestBody), new Consumer<String>() {
                    @Override
                    public void accept(String respond) throws Exception {
                        getView().hideLoadingView();
                        getView().onSendPaySmsCode(respond);
                    }
                });
                break;
            case DaiLi:
                addTask(RetrofitHelper.getInstance().getService().changJie_sendPaySmsCode(requestBody), new Consumer<String>() {
                    @Override
                    public void accept(String respond) throws Exception {
                        getView().hideLoadingView();
                        getView().onSendPaySmsCode(respond);
                    }
                });
                break;
        }

    }

    //    OrderNo 订单号不能为空
//    Smscode 短信验证码
    public void commitPay(String orderId, String msgcode, ChangJieCodeActivity.PayType payType) {
        getView().showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", UserManager.getInstance().getToken());
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            JSONObject data = new JSONObject();
            data.put("OrderNo", orderId);
            data.put("Smscode", msgcode);
            requestData.put("dynamic", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("data---支付参数", requestData.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        switch (payType){
            case DaiLi:
                addTask(RetrofitHelper.getInstance().getService().changJie_commitPay(requestBody), new Consumer<String>() {
                    @Override
                    public void accept(String respond) throws Exception {
                        getView().hideLoadingView();
                        getView().onPayFinish(respond);
                    }
                });
                break;
            case ZhengXin:
                addTask(RetrofitHelper.getInstance().getService().changJie_commitPayZX(requestBody), new Consumer<String>() {
                    @Override
                    public void accept(String respond) throws Exception {
                        getView().hideLoadingView();
                        getView().onPayFinish(respond);
                    }
                });
                break;
        }

    }
}
