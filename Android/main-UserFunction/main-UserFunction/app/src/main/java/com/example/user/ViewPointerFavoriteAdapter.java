package com.example.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

// Fragment 구현때문에 만든 Adapter클래스
// 4가지 탭을 관리함 (홈/즐겨찾기/추천카페/개인정보)
public class ViewPointerFavoriteAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> items;
    private ArrayList<String> itexts = new ArrayList<String>();

    public ViewPointerFavoriteAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new ChildFragment_Favorite());
        items.add(new ChildFragment_Visited());

        itexts.add("즐겨찾기");
        itexts.add("방문 장소");

    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return itexts.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
