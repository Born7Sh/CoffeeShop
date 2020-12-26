package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */
// 즐겨찾기탭으로 하위 탭들인 Child_visited, Child_Favorite를 관리함
// 두 탭의 왕래가 페이지 넘어가듯이 이동해야하기 때문에 viewPage로 Adapter를 통해 구현함
public class Fragment_Favorite extends Fragment {
    ViewPager vp;
    ViewPointerFavoriteAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Fragment fragment1;

    public Fragment_Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Favorite newInstance(String param1, String param2) {
        Fragment_Favorite fragment = new Fragment_Favorite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__favorite, container, false);

            vp = view.findViewById(R.id.FavoriteViewpager);
            adapter = new ViewPointerFavoriteAdapter(getChildFragmentManager());
            // Fragment 안에서 viewPager 쓰려면 getFragmentManager (X) getChildFragmentManager() 써야함
            vp.setAdapter(adapter);

            TabLayout tab = view.findViewById(R.id.tab);

            tab.setupWithViewPager(vp);


            return view;


    }
}


