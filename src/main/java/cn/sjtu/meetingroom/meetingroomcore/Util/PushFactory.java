package cn.sjtu.meetingroom.meetingroomcore.Util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.utils.ParameterHelper;

import java.util.Date;

public class PushFactory {
    //need to set head body and deviceIds
    public static void push(String deviceId, String title, String body){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5EWHbyOoFdAt"
                , "7wXWFywaAI4hlyM3JNbFEF0BjuKS9g");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(25610645l);
        pushRequest.setTarget("DEVICE"); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
        pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("ANDROID"); // 设备类型 ANDROID iOS ALL.
        pushRequest.setTargetValue(deviceId);
        // 推送配置: Android
        pushRequest.setTitle(title);
        pushRequest.setBody(body);
        pushRequest.setAndroidNotifyType("BOTH");//通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音
        pushRequest.setAndroidNotificationBarType(1);//通知栏自定义样式0-100
        pushRequest.setAndroidNotificationBarPriority(1);//通知栏自定义样式0-100
        pushRequest.setAndroidMusic("default"); // Android通知音乐
        // 指定notificaitonchannel id
        pushRequest.setAndroidNotificationChannel("1");
        // 推送控制
        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        try {
            client.getAcsResponse(pushRequest);
        }
        catch (Exception e) {
            return;
        }
    }
}
