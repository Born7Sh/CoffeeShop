package com.example.user;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Booking1 {
    int id;

    @SerializedName("cno")
    int cno;

    @SerializedName("sno")
    int sno;

    boolean t0000;
    boolean t0030;
    boolean t0100;
    boolean t0130;
    boolean t0200;
    boolean t0230;
    boolean t0300;
    boolean t0330;
    boolean t0400;
    boolean t0430;
    boolean t0500;
    boolean t0530;
    boolean t0600;
    boolean t0630;
    boolean t0700;
    boolean t0730;
    boolean t0800;
    boolean t0830;


    public void getCno(int c_no){
        this.cno = c_no;
    }

    public void getSno(int s_no){
        this.sno = s_no;
    }
}
