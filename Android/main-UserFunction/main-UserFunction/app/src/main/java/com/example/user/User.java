package com.example.user;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class User extends Application {
    private int uno;
    private String uid;
    private int bookId;
    private int checkId;
    private boolean isLogined;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

    public int getUno(){
        return this.uno;
    }

    public String getUid(){
        return this.uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setUno(int uno){
        this.uno = uno;
    }

    public void setBookId(int id){ this.bookId = id;}

    public int getBookId(){
        return this.bookId;
    }
    public void setCheckId(int id){
        this.checkId = id;
    }
    public int getCheckId(){
        return this.checkId;
    }

    public void setIsLogin(){
        this.isLogined = true;
    }
    public void setNotLogin(){
        this.isLogined = false;
    }

    public boolean getIsLogin(){
        return this.isLogined;
    }

    //알림 추가하기
    public void setPush() {
    }

    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Test",mNotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);

        }
    }
    public NotificationCompat.Builder getNotifiactionBuilder(){
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                .setContentTitle("알림 메시지")
                .setContentText("알림 내용")
                .setSmallIcon(R.drawable.button_round_brown);
        return notiBuilder;

    }
    public void sendNoti(){
        NotificationCompat.Builder notiBuilder = getNotifiactionBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,notiBuilder.build());
    }
}
