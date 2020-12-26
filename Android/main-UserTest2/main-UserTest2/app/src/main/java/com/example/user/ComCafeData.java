package com.example.user;

import com.google.gson.annotations.SerializedName;

public class ComCafeData {
    @SerializedName("id")
    int id;
    String cafe_name;
    float x;
    float y;
    String start_time;
    String end_time;
    String phone;
    String notice;
    float star;
    String com_num;
    boolean business;
    int seat_curr;
    int seat_total;
    String picture;
    String tag1;
    String tag2;
}
