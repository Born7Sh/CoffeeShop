package com.example.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

// Fragment 구현때문에 만든 Adapter클래스
// 4가지 탭을 관리함 (홈/즐겨찾기/추천카페/개인정보)
public class ViewPointAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> items;

    public ViewPointAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new Fragment_Home());
        items.add(new Fragment_Recommend());
        items.add(new Fragment_Favorite());
        items.add(new Fragment_Info());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    public Fragment getMapItem(String a, String b) {
        items.set(0, Fragment_Home.newInstance(a,b));
        return items.get(0);
    }

    public void setRestet(){
        items.set(0, new Fragment_Home());
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
