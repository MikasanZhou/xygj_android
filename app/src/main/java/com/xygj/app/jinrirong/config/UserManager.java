package com.xygj.app.jinrirong.config;

import android.content.Context;

import com.xygj.app.common.utils.SPUtils;
import com.xygj.app.jinrirong.MyApplication;

/**
 * 用户信息管理类
 * Created by xuyougen on 2018/3/12.
 */

public class UserManager {
    private static UserManager INSTANCE = null;
    private Context mContext;

    private UserManager(Context context) {
        mContext = context;
    }

    public static UserManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserManager(MyApplication.getContext());
        }
        return INSTANCE;
    }

    /**
     * 保存登录信息到本地
     *
     * @param phone
     * @param token
     * @param uid
     */
    public void saveLoginData(String phone, String token, String uid) {
        SPUtils.put(mContext, "phone", phone);
        SPUtils.put(mContext, "token", token);
        SPUtils.put(mContext, "uid", uid);
    }

    public void saveLoginData(String phone, String token) {
        SPUtils.put(mContext, "phone", phone);
        SPUtils.put(mContext, "token", token);
    }

    public void saveMemberType(int type) {
        SPUtils.put(mContext, "member_type", type);
    }

    public int getMemberType() {
        return (int) SPUtils.get(mContext, "member_type", -1);
    }

    /**
     * 保存登录信息到本地
     *
     * @param phone
     * @param token
     * @param uid
     * @param nickName
     */
    public void saveLoginData(String phone, String token, String uid, String nickName) {
        saveLoginData(phone, token, uid);
        saveNickName(nickName);
    }

    /**
     * 清除本地的用户登录信息
     */
    public void clearLoginData() {
        SPUtils.remove(mContext, "token");
        SPUtils.remove(mContext, "phone");
        //SPUtils.remove(mContext, "uid");
        //SPUtils.remove(mContext, "nickName");
    }

    /**
     * 保存已阅读 最新消息id
     *
     * @param msgId
     */
    public void saveLatestMsgId(int msgId) {
        SPUtils.put(mContext, "msg_id", msgId);
    }

    /**
     * 获取本地保存 最新消息id
     *
     * @return
     */
    public int getLatestMsgId() {
        return (int) SPUtils.get(mContext, "msg_id", -1);
    }

    public String getPhone() {
        return (String) SPUtils.get(mContext, "phone", "");
    }

    public String getToken() {
        return (String) SPUtils.get(mContext, "token", "");
    }

    public String getUid() {
        return (String) SPUtils.get(mContext, "uid", "");
    }

    public boolean isLogin() {
        return getToken().length() != 0;
    }

    public void saveNickName(String nickName) {
        SPUtils.put(mContext, "nickName", nickName);
    }

    public String getNickName() {
        return (String) SPUtils.get(mContext, "nickName", "");
    }

    public void saveDeviceToken(String deviceToken) {
        SPUtils.put(mContext, "deviceToken", deviceToken);
    }

    public String getDeviceToken() {
        return (String) SPUtils.get(mContext, "deviceToken", "");
    }

    public void saveRoleName(String role) {
        SPUtils.put(mContext, "roleName", role);
    }

    public String getRoleName() {
        return (String) SPUtils.get(mContext, "roleName", "");
    }
}
