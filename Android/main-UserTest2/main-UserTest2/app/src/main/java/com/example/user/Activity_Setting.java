package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
//2020-09-11 JHM (Setting 페이지 구현 및 변수 처리)
public class Activity_Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__setting);

        Button Sound = (Button) findViewById(R.id.Sound);
        Button Alert = (Button) findViewById(R.id.Alert);
        Button LoginType = (Button) findViewById(R.id.LoginType);

        TextView Version = (TextView) findViewById(R.id.Version);

        Button Delete = (Button) findViewById(R.id.Delete);
    }
}
