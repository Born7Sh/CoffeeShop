package com.example.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// 홈탭으로 하위 탭들 Child_map Child_List 을 관리함
// 단 다른 탭들과는 다르게 페이지 넘기는것처럼 넘어갈 필요는 없고 버튼식으로 구현할 것이기에 그냥 Fragment 간의 이동으로 구현했다.
public class Fragment_Home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Home() {
        // Required empty public constructor
    }

    public Fragment_Home(String param1, String param2) {
        // Required empty public constructor
    }

    public static Fragment_Home newInstance(String param1, String param2) {

        Fragment_Home fragment = new Fragment_Home();
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
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        final Button List = (Button) view.findViewById(R.id.List);

        if(mParam1 != null && mParam2 != null){
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, ChildFragment_Map.newInstance (mParam1,mParam2)).commitAllowingStateLoss();
        }
        else {getFragmentManager().beginTransaction().add(R.id.frameLayout, new ChildFragment_Map()).commitAllowingStateLoss();}
        // 버튼 눌렀을 때 화면전환을 하는것 (Fragment간의 전환)
        // 하지만 배경을 투명하게 하면 밑에있는 Fragment가 보임
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (List.getText().equals("목록")) {
                    List.setText("지도");
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ChildFragment_List()).remove(new ChildFragment_Map()).commitAllowingStateLoss();

                } else if (List.getText().equals("지도")) {
                    List.setText("목록");
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ChildFragment_Map()).remove(new ChildFragment_List()).commitAllowingStateLoss();
                }

            }
        });

        return view;
    }

}
