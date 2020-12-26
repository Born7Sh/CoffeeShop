package com.example.user;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Recommend#newInstance} factory method to
 * create an instance of this fragment.
 */

// 추천카페 탭임 recycle뷰 빼고는 특이사항없음
public class Fragment_Recommend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CafeData> cafedata1 = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Recommend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Recommend.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Recommend newInstance(String param1, String param2) {
        Fragment_Recommend fragment = new Fragment_Recommend();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment_Recommend newInstance() {
        Fragment_Recommend fragment = new Fragment_Recommend();
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
        View view = inflater.inflate(R.layout.fragment__recommend, container, false);

        cafedata1.clear();
        //cafedata1.add(new CafeData("호민다","12:30","14:30","asdasd",1,1,1,"123","호민다카페"));
       // cafedata1.add(new CafeData("달리는 커피","10:30","14:30","asdasd",2,1,1,"123","달리는커피"));
       // cafedata1.add(new CafeData("탈주닌자클랜","11:30","19:30","asdasd",3,1,1,"123","카페탈주닌자"));

        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.RecommendList);
        layoutManager = new LinearLayoutManager(context);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        CafeAdapter adapter = new CafeAdapter(context, cafedata1);
        recyclerView.setAdapter(adapter);


        final SwipeRefreshLayout SwipeRecommend = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRecommend);
        SwipeRecommend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cafedata1.clear();
                //cafedata1.add(new CafeData("호로로롤","12:30","14:30","asdasd",1,1,1,"123","카페호로롤"));
                //cafedata1.add(new CafeData("람머스는 웃고있다.","10:30","14:30","asdasd",2,1,1,"123","카페람머스는웃고있다"));

                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                recyclerView.setAdapter(adapter);
                SwipeRecommend.setRefreshing(false);
            }
        });


        return view;
    }
}
