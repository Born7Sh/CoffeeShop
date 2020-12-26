package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_MapInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__map_info);

        Button btn = (Button) findViewById(R.id.btn1);
        TextView CafeName = (TextView) findViewById(R.id.CafeName);
        TextView CafeStart = (TextView) findViewById(R.id.CafeStart);
        TextView CafeEnd = (TextView) findViewById(R.id.CafeEnd);
        Intent intent = getIntent();

        final String CafeN = intent.getStringExtra("CafeName"); // 카페 이름
        final String CafeT = intent.getStringExtra("CafeStart"); // 카페 시간
        final String CafeG = intent.getStringExtra("CafeEnd"); // 카페 점수
        final String CafeP = intent.getStringExtra("CafePhone");
        final String CafeI = intent.getStringExtra("CafeInfo");
        //여기서 변수로 써줘야지 setOnclick 때 intent 쓸수있음

        CafeName.setText(CafeN);
        CafeStart.setText(CafeT);
        CafeEnd.setText(CafeG);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_CafeInfo.class);
                intent.putExtra("CafeName", CafeN);
                intent.putExtra("CafeTime", CafeT);
                intent.putExtra("CafeGrade", CafeG);

                // 전화번호 / 설명 / 사진 같은건 데이터베이스에서 받아서 보내야됨
                intent.putExtra("CafePhone", CafeP);
                intent.putExtra("CafeInfo", CafeI);
                intent.putExtra("CafeEvent", "바나나");
                startActivity(intent);
            }
        });

    }
}
