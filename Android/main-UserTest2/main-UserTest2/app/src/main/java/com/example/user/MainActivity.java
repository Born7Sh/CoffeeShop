package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

// 사실상 간단한 main엑티비티
// #####참고사항######
// Fragment로 구성하는 엑티비티들이 많음 그래서 그냥 Activity에서 하는 방법은 안될가능성이 큼
// Activity에서 oncreate에서 구현하는데 Fragement에서는 onCreateView에서 구현해야함
// 이런거 말고도 this를 다르게 써야한다는 것들이 있기는 한데 모르겠으면 검색이나 나한테 물어봐주

public class MainActivity extends AppCompatActivity {

    ViewPager vp;
    ViewPointAdapter adapter;
    int Number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int l=0;
        Intent intent = new Intent (getApplicationContext(), Activity_Login.class);
        startActivity(intent);


        setContentView(R.layout.activity_main);


        //HSJ 2020-08-22 추가1
        //유저 정보 저장
        User user = (User)getApplication();


        vp = findViewById(R.id.viewpager);
        adapter = new ViewPointAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.mipmap.fixedhome);
        images.add(R.mipmap.fixedheart);
        images.add(R.mipmap.fixedstar);
        images.add(R.mipmap.mainuser);

        for (int i = 0; i < 4; i++) tab.getTabAt(i).setIcon(images.get(i));

        adapter.setRestet();
        LinearLayout tabStip = (LinearLayout) tab.getChildAt(0);
    }

    public void setFragment(String a, String b) {
        // 지도로 가는거 눌렀을 때 이렇게 세팅해줘야함
        adapter.getMapItem(a,b);

        vp.setAdapter(adapter);
        //vp.setCurrentItem(0);
        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.mipmap.fixedhome);
        images.add(R.mipmap.fixedheart);
        images.add(R.mipmap.fixedstar);
        images.add(R.mipmap.mainuser);

        for (int i = 0; i < 4; i++) tab.getTabAt(i).setIcon(images.get(i));
    }

    //뒤로가기 2번 클릭 시 종료
    private long lastTimeBackPressed; //뒤로가기 버튼이 클릭된 시간
    @Override
    public void onBackPressed()
    {
        //2초 이내에 뒤로가기 버튼을 재 클릭 시 앱 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000)
        {
            finish();
            return;
        }
        //'뒤로' 버튼 한번 클릭 시 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
