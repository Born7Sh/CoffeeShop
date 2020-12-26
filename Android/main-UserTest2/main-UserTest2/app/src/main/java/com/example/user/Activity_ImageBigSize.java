package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_ImageBigSize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__image_big_size);

        ImageView BigImage = (ImageView) findViewById(R.id.BigImage);

        Intent intent = getIntent();
        String a = intent.getExtras().getString("Image"); // null 방지를 위해서 함
        Bitmap bm = getImFromURL(a);
        if (a.equals("0") || a == null) {
            Bitmap b = intent.getParcelableExtra("image");
            //BigImage.setImageResource(R.mipmap.arrowleft); // 일단 나오는지 확인하기 위해서 있는 이미지 출력

            BigImage.setImageBitmap(bm); // 확인할때에는 이걸로 교체하면 될거
        } else {
            BigImage.setImageBitmap(bm);
        }

    }
    //2020-08-25 HSJ url로 서버에서 이미지 받아오기
    public Bitmap getImFromURL(final String ImUrl){
        if(ImUrl==null){
            return null;
        }
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
