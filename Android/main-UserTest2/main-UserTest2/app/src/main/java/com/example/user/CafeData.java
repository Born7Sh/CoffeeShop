package com.example.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CafeData implements Serializable {
    private String CafeEnd;
    private String CafeStart;
    private String CafeGrade;
    private String CafeName;
    private int CafePid;
    private String CafePhone;
    private String CafeIntro;
    private double CafeLatitude;
    private double CafeLongitude;
    private CafeImage cafePicture;
    private boolean business;
    private int seat_total;
    private String tag1;
    private String tag2;
    /*
    public CafeData(String CafeName, String CafeStart, String CafeEnd, String CafeGrade, int CafePid, double CafeLatitude, double CafeLongitude,String CafePhone,String CafeIntro,CafeImage cafeImage){
        this.CafeName = CafeName;
        this.CafeStart = CafeStart;
        this.CafeEnd = CafeEnd;
        this.CafeGrade = CafeGrade;
        this.CafePid = CafePid;
        this.CafeLatitude = CafeLatitude;
        this.CafeLongitude = CafeLongitude;
        this.CafePhone = CafePhone;
        this.CafeIntro = CafeIntro;
        this.cafePicture = cafeImage;
    }
     */
    public CafeData(String CafeName, String CafeStart, String CafeEnd, String CafeGrade, int CafePid, double CafeLatitude, double CafeLongitude,String CafePhone,String CafeIntro,int seat_total,boolean business
    ,String tag1, String tag2){
        this.CafeName = CafeName;
        this.CafeStart = CafeStart;
        this.CafeEnd = CafeEnd;
        this.CafeGrade = CafeGrade;
        this.CafePid = CafePid;
        this.CafeLatitude = CafeLatitude;
        this.CafeLongitude = CafeLongitude;
        this.CafePhone = CafePhone;
        this.CafeIntro = CafeIntro;
        this.seat_total = seat_total;
        this.business = business;
        this.tag1 = tag1;
        this.tag2 = tag2;
    }

    public double getCafeLatitude() {
        return CafeLatitude;
    }

    public double getCafeLongitude() {
        return CafeLongitude;
    }

    public void setCafeLatitude(double cafeLatitude) {
        CafeLatitude = cafeLatitude;
    }

    public void setCafeLongitude(double cafeLongitude) {
        CafeLongitude = cafeLongitude;
    }

    public int getCafePid() {
        return CafePid;
    }

    public void setCafePid(int cafePid) {
        CafePid = cafePid;
    }

    public void setCafePicture(CafeImage cafeImage){
        this.cafePicture = cafeImage;
    }

    public String getCafeEnd() {
        return CafeEnd;
    }

    public String getCafeGrade() {
        return CafeGrade;
    }

    public String getCafeName() {
        return CafeName;
    }

    public String getCafeStart() {
        return CafeStart;
    }

    //카페 영업 중인지 확인받기
    public boolean getCafeBusiness(){
        return this.business;
    }

    //HSJ 2020-08-23 추가
    //카페 전화번호, 소개받아오기
    public String getCafePhone(){
        return this.CafePhone;
    }
    public String getCafeIntro(){
        return this.CafeIntro;
    }
    //HSJ 2020-08-26 추가
    //getCafePicture
    //getCafeSeat
    public int getCafeSeat(){
        return this.seat_total;
    }
    public CafeImage getCafePicture(){
        return this.cafePicture;
    }
    public void setCafeEnd(String cafeEnd) {
        CafeEnd = cafeEnd;
    }

    public void setCafeGrade(String cafeGrade) {
        CafeGrade = cafeGrade;
    }

    public void setCafeName(String cafeName) {
        CafeName = cafeName;
    }

    public void setCafeStart(String cafeStart) {
        CafeStart = cafeStart;
    }
}
