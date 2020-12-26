package com.example.user;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComCafeImage {
    int cno;
    String img1;
    String img2;
    String img3;
    String img4;
    String img5;
    final List<CafeImage> cafePictureList = new ArrayList<>();

    public ComCafeImage(String img1, String img2, String img3, String img4, String img5){
        this.img1= img1;
        this.img2= img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
    }

    public ComCafeImage(int cno){
        this.img1 = "null";
        this.img2 = "null";
        this.img3 = "null";
        this.img4 = "null";
        this.img5 = "null";
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<CafeImage>> call3;
        call3 = retroservice.getCafeImages(cno);


        call3.enqueue(new Callback<List<CafeImage>>(){
            @Override
            public void onResponse(Call<List<CafeImage>> call3, Response<List<CafeImage>> response3){
                if(response3.isSuccessful()){
                    List<CafeImage> res = response3.body();
                    cafePictureList.add(res.get(0));
                }
            }
            @Override
            public void onFailure(Call<List<CafeImage>> call3, Throwable t3){
                Log.v("카페이미지불러오기","오류");
            }
        });

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
