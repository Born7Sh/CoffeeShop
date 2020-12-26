package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class Activity_Announce extends AppCompatActivity {

    RecyclerView reviewView;
    ArrayList<AnnounceData> announceList = new ArrayList<>();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        reviewView = findViewById(R.id.AnnounceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewView.setLayoutManager(linearLayoutManager);

        AnnounceData data1 = new AnnounceData(1,"공지사항1","200201212", "공지사항 1 입니다.");
        AnnounceData data2 = new AnnounceData(2,"공지사항2","200201213", "공지사항 2 입니다.");
        AnnounceData data3 = new AnnounceData(3,"공지사항3","200201214", "공지사항 3 입니다.");

        announceList.add(data1);
        announceList.add(data2);
        announceList.add(data3);

        AnnounceAdapter adapter = new AnnounceAdapter(Activity_Announce.this, announceList);

        reviewView.setAdapter(adapter);
    }
}
