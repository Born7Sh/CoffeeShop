package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Reviewlist extends AppCompatActivity {
    RecyclerView reviewView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reviewlist);

        Intent intent = getIntent();
        int cafePid = intent.getExtras().getInt("CafePid");
        reviewView = findViewById(R.id.ReviewList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewView.setLayoutManager(linearLayoutManager);

        final ReviewAdapter adapter = new ReviewAdapter(Activity_Reviewlist.this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        RetroService retroservice = retrofit.create(RetroService.class);


        Call<List<Review>> call3 = retroservice.getReview(cafePid);

        call3.enqueue(new Callback<List<Review>>(){
            @Override
            public void onResponse(Call<List<Review>> call2, Response<List<Review>> response2){
                if(response2.isSuccessful()){
                    final List<Review> re1 = response2.body();
                    Log.v("리뷰내용",re1.get(0).review);

                    //URLThread
                    Bitmap bm;

                    for(int i=0; i<re1.size();i++) {
                        bm = getImFromURL(re1.get(i).picture);
                        adapter.addItem(new ReviewData(String.valueOf(re1.get(i).uid),"10","남",re1.get(i).create_dt,re1.get(i).review,String.valueOf(re1.get(i).star),bm));
                    }
                    reviewView.setAdapter(adapter);
                }
                Log.v("리뷰받아오기",response2.message());
            }

            @Override
            public void onFailure(Call<List<Review>> call2, Throwable t2){
                Log.v("리뷰 바당오기","실패");
            }
        });

    }
    //2020-08-25 HSJ url로 서버에서 이미지 받아오기
    public Bitmap getImFromURL(final String ImUrl){
        final Bitmap[] bms = new Bitmap[1];
        Thread mThread = new Thread() {
            @Override
            public void run()
            {
                try {
                    Bitmap bm;
                    URL url = new URL(ImUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bm = BitmapFactory.decodeStream(is);

                    is.close();
                    bms[0] =bm;

                } catch (Exception e) {
                    Log.v("로딩오류", e.getMessage());
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
        }catch(Exception e2){

        }
        return bms[0];
    }
}
