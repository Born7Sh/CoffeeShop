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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        final View view = inflater.inflate(R.layout.fragment__recommend, container, false);

        cafedata1.clear();
        //cafedata1.add(new CafeData("호민다","12:30","14:30","asdasd",1,1,1,"123","호민다카페"));
       // cafedata1.add(new CafeData("달리는 커피","10:30","14:30","asdasd",2,1,1,"123","달리는커피"));
       // cafedata1.add(new CafeData("탈주닌자클랜","11:30","19:30","asdasd",3,1,1,"123","카페탈주닌자"));

        //2020--09-15 HSJ 이벤트 중인 카페 받아오기
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<ComCafeData>> call = retroservice.getEventCafe(true);

        call.enqueue(new Callback<List<ComCafeData>>() {
            @Override
            public void onResponse(Call<List<ComCafeData>> call, Response<List<ComCafeData>> response) {
             if(response.isSuccessful()){
                 List<ComCafeData> re1 = response.body();
                 for(int i=0;i<re1.size();i++){
                     cafedata1.add(new CafeData(re1.get(i).cafe_name, re1.get(i).start_time, re1.get(i).end_time, String.valueOf(re1.get(i).star),
                             re1.get(i).id, re1.get(i).x, re1.get(i).y, re1.get(i).phone, re1.get(i).notice,re1.get(i).seat_total,re1.get(i).business,
                             re1.get(i).tag1,re1.get(i).tag2));
                 }
                 context = view.getContext();
                 recyclerView = (RecyclerView) view.findViewById(R.id.RecommendList);
                 layoutManager = new LinearLayoutManager(context);

                 layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                 recyclerView.setLayoutManager(layoutManager);

                 CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                 recyclerView.setAdapter(adapter);
             }
            }

            @Override
            public void onFailure(Call<List<ComCafeData>> call, Throwable t) {

            }
        });
        //2020-09-15 HSJ



        final SwipeRefreshLayout SwipeRecommend = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRecommend);
        SwipeRecommend.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cafedata1.clear();
                //cafedata1.add(new CafeData("호로로롤","12:30","14:30","asdasd",1,1,1,"123","카페호로롤"));
                //cafedata1.add(new CafeData("람머스는 웃고있다.","10:30","14:30","asdasd",2,1,1,"123","카페람머스는웃고있다"));

                //2020--09-15 HSJ 이벤트 중인 카페 받아오기
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                Call<List<ComCafeData>> call = retroservice.getEventCafe(true);

                call.enqueue(new Callback<List<ComCafeData>>() {
                    @Override
                    public void onResponse(Call<List<ComCafeData>> call, Response<List<ComCafeData>> response) {
                        if(response.isSuccessful()){
                            List<ComCafeData> re1 = response.body();
                            for(int i=0;i<re1.size();i++){
                                cafedata1.add(new CafeData(re1.get(i).cafe_name, re1.get(i).start_time, re1.get(i).end_time, String.valueOf(re1.get(i).star),
                                        re1.get(i).id, re1.get(i).x, re1.get(i).y, re1.get(i).phone, re1.get(i).notice,re1.get(i).seat_total,re1.get(i).business,
                                        re1.get(i).tag1,re1.get(i).tag2));
                            }
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                            recyclerView.setAdapter(adapter);
                            SwipeRecommend.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ComCafeData>> call, Throwable t) {

                    }
                });

            }
        });
        //2020-09-15 HSJ


        return view;
    }
}
