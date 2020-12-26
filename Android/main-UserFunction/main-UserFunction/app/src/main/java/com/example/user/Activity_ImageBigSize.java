package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class Activity_ImageBigSize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__image_big_size);

        ImageView BigImage = (ImageView) findViewById(R.id.BigImage);

        Intent intent = getIntent();
        Integer a = intent.getExtras().getInt("ImageNum"); // null 방지를 위해서 함

        if (a == 0 || a == null) {
            Bitmap b = intent.getParcelableExtra("image");
            BigImage.setImageResource(R.mipmap.arrowleft); // 일단 나오는지 확인하기 위해서 있는 이미지 출력
            //BigImage.setImageBitmap(b); // 확인할때에는 이걸로 교체하면 될거
        } else {
            BigImage.setImageResource(a);
        }

    }
}
