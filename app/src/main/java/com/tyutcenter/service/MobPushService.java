package com.tyutcenter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;
import com.tyutcenter.activity.ExpressDetailActivity;
import com.tyutcenter.data.UserData;
import com.tyutcenter.model.Message;
import com.tyutcenter.utils.CommonUtil;

import java.util.HashMap;

public class MobPushService extends Service{

    private MobPushReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initPushService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MobPush.removePushReceiver(mReceiver);
    }

    public void initPushService(){
        MobPush.addTags(new String[]{"android"});
        MobPush.setAlias(UserData.getUser().getId());
        mReceiver = new MobPushReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage message) {
//              setMessage(message);
            }
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息

            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage message) {
                //接收通知消息被点击事件
                setMessage(message);
            }
            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {
                //接收tags的增改删查操作
            }
            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {
                //接收alias的增改删查操作
            }
        };
        MobPush.addPushReceiver(mReceiver);
    }

    private void setMessage(MobPushNotifyMessage message){
        //接收自定义消息
        Message temp = new Message();
        HashMap<String, String> extrasMap = message.getExtrasMap();
        if (extrasMap != null && extrasMap.size() > 0) {
            temp.setId(Integer.parseInt(CommonUtil.isStrEmpty(extrasMap.get("id") + "") ? "0" : extrasMap.get("id")));
            temp.setHead_url(extrasMap.get("head_url"));
            temp.setMsg_content(extrasMap.get("msg_content"));
            temp.setMsg_date(extrasMap.get("msg_date"));
            temp.setMsg_imgs(extrasMap.get("msg_imgs"));
            temp.setMsg_title(extrasMap.get("msg_title"));
            temp.setMsg_type(extrasMap.get("msg_type"));
            temp.setPublish_person(Integer.parseInt(CommonUtil.isStrEmpty(extrasMap.get("publish_person")) ? "0" : extrasMap.get("publish_person")));
            temp.setPublish_person_name(extrasMap.get("publish_person_name"));

            Intent intent = new Intent(this,ExpressDetailActivity.class);
            intent.putExtra("message",message);
            startActivity(intent);
        }
    }
}
