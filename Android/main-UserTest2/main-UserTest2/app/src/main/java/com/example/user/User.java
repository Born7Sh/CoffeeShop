package com.example.user;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class User extends Application {
    private int uno;
    private String uid;
    private int bookId;
    private int checkId;
    private boolean isLogined;
    private String rsvStart;
    private String rsvEnd;
    private ArrayList<Integer> arrVisited = new ArrayList<>();

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

    //2020-09-14 HSJ 알림을 위한 예약 시작/종료
    public void setRsvTime(String rsvStart, String rsvEnd){
        this.rsvStart = rsvStart;
        this.rsvEnd = rsvEnd;
    }
    public String getRsvStart(){
        return this.rsvStart;
    }
    public String getRsvEnd(){
        return this.rsvEnd;
    }

    //카페 방문 확인
    public void setVisited(int cafeId){
        if(!arrVisited.contains(cafeId)) {
            this.arrVisited.add(cafeId);
        }
    }
    public Boolean isVisited(int cafeId){
        return arrVisited.contains(cafeId);
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
                .setContentTitle("예약 시간 알림")
                .setContentText(this.getRsvStart() + " ~ " + this.getRsvEnd())
                .setSmallIcon(R.drawable.button_round_brown);
        return notiBuilder;

    }
    public void sendNoti(){
        NotificationCompat.Builder notiBuilder = getNotifiactionBuilder();
        mNotificationManager.notify(NOTIFICATION_ID,notiBuilder.build());
    }
}
