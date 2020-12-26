package com.example.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CafeImage implements Serializable {
    int cno;
    String img1;
    String img2;
    String img3;
    String img4;
    String img5;

    public CafeImage(String img1, String img2, String img3, String img4, String img5){
        this.img1= img1;
        this.img2= img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;

    }

    public String getImg1(){
        return img1;
    }
    public String getImg2(){
        return img2;
    }
    public String getImg3(){
        return img3;
    }
    public String getImg4(){
        return img4;
    }
    public String getImg5(){
        return img5;
    }
}
