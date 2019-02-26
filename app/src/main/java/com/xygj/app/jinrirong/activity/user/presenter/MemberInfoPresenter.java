package com.xygj.app.jinrirong.activity.user.presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.xygj.app.common.base.BasePresenter;
import com.xygj.app.common.utils.UploadUtils;
import com.xygj.app.jinrirong.activity.user.view.MemberInfoView;
import com.xygj.app.jinrirong.config.ApiFactory;
import com.xygj.app.jinrirong.config.Constants;
import com.xygj.app.jinrirong.config.RetrofitHelper;
import com.xygj.app.jinrirong.model.HttpRespond;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Yangli on 2018/4/19.
 */

public class MemberInfoPresenter extends BasePresenter<MemberInfoView> {
    private String headImgUrl;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void requestMemberInfo(String token) {
        mView.showLoadingView();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().requestMemberInfo(requestBody), new Consumer<HttpRespond<String>>() {
            @Override
            public void accept(HttpRespond<String> stringHttpRespond) throws Exception {
                mView.hideLoadingView();
                mView.onGetMemberInfo(stringHttpRespond);
            }
        });
    }

    public void submitUserInfo(String token, String userInfo) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("token", token);
            requestData.put("client", Constants.CLIENT);
            requestData.put("package", Constants.PACKAGE);
            requestData.put("ver", Constants.VER);
            requestData.put("dynamic", userInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        addTask(RetrofitHelper.getInstance().getService().submitMemberInfo(requestBody), new Consumer<HttpRespond>() {
            @Override
            public void accept(HttpRespond respond) throws Exception {
                mView.onSubmitMemberInfoDone(respond);
            }
        });
    }

    public void uploadImage(final String filesrc, final String token) {
        new Thread(new Runnable() { //开启线程上传文件
            @Override
            public void run() {
                final Map<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("client", Constants.CLIENT);
                params.put("package", Constants.PACKAGE);
                params.put("ver", Constants.VER);
                File file = new File(filesrc);
                file.length();
                String result = UploadUtils.uploadFile(file, (ApiFactory.BASE_URL +
                        ApiFactory.UPLOAD_HEAD_IMG), params);
                Log.e("upload", "uploadImage: " + result);
                if (result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        String path = json.getString("path");
                        String filepath = json.getString("filepath");
                        Log.e("upload", "uploadImage: " + path);
                        Log.e("upload", "uploadImage: " + filepath);
                        setHeadImgUrl(path);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("upload", "uploadImage: 头像上传失败");
                }
            }
        }).start();
    }
}
