package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity_Announce_Confirmatiom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__announce__confirmatiom);

        TextView announceTitle = (TextView) findViewById(R.id.AnnounceTitle);
        TextView announce = (TextView) findViewById(R.id.Announce);
        TextView announceDate = (TextView) findViewById(R.id.AnnounceDate);

        Intent intent =  getIntent();

        String AnnounceTitle = intent.getExtras().getString("AnnounceTitle");
        String AnnounceDate = intent.getExtras().getString("AnnounceDate");
        String Announce = intent.getExtras().getString("Announce");

        announceTitle.setText(AnnounceTitle);
        announceDate.setText(AnnounceDate);
        announce.setText(Announce);

    }
}
