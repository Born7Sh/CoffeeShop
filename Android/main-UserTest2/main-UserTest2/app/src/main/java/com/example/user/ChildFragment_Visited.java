package com.example.user;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
 * Use the {@link ChildFragment_Visited#newInstance} factory method to
 * create an instance of this fragment.
 */

// 즐겨찾기 페이지에서 파생된 2개의 탭중에 하나인 즐겨찾기-방문한 카페 목록임
// recycle뷰 쓴건 빼고는 특이사항없음

public class ChildFragment_Visited extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CafeData> cafedata2 = new ArrayList<>();

    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildFragment_Visited() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment_Visited.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildFragment_Visited newInstance(String param1, String param2) {
        ChildFragment_Visited fragment = new ChildFragment_Visited();
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
        final View view = inflater.inflate(R.layout.fragment_child__visited, container, false);

        //새로고침
        final SwipeRefreshLayout SwipeVisited = (SwipeRefreshLayout) view.findViewById(R.id.SwipeVisited);

        cafedata2.clear();
        //HSJ 2020-08-22 추가 예약 기록 받아오기
        final User user = (User)getActivity().getApplication();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<Check>> call = retroservice.requestUserCheck(user.getUno());

        call.enqueue(new Callback<List<Check>>(){
            @Override
            public void onResponse(Call<List<Check>> call, Response<List<Check>> response){
                if(response.isSuccessful()){
                    Log.v("유저체크알림", "성공");
                    List<Check> re1= response.body();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                    RetroService retroservice = retrofit.create(RetroService.class);
                    for(int i=0; i<re1.size(); i++){
                        //서버에 cno로 카페 정보 요청하기(전역변수로 쓸까? 느리면 생각해보자)
                       // User user = (User)getActivity().getApplication();

                        //유저 방문 기록에 추가
                        user.setVisited(re1.get(i).cno);

                        Call<ComCafeData> call2 = retroservice.requestCafe(re1.get(i).cno);



                        call2.enqueue(new Callback<ComCafeData>(){
                           @Override
                           public void onResponse(Call<ComCafeData> call2, Response<ComCafeData> response2){
                                if(response2.isSuccessful()){
                                    Log.v("카페체크알림","성공");
                                    ComCafeData re2 = response2.body();
                                    cafedata2.add(new CafeData(re2.cafe_name, re2.start_time, re2.end_time, String.valueOf(re2.star),
                                            re2.id, re2.x, re2.y, re2.phone, re2.notice,re2.seat_total,re2.business,
                                            re2.tag1,re2.tag2));
                                    context = view.getContext();

                                    recyclerView = (RecyclerView) view.findViewById(R.id.VisitedList);
                                    recyclerView.setHasFixedSize(true);

                                    layoutManager = new LinearLayoutManager(context);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    recyclerView.setLayoutManager(layoutManager);

                                    CafeAdapter adapter = new CafeAdapter(context, cafedata2);
                                    recyclerView.setAdapter(adapter);

                                }
                           }
                           @Override
                            public void onFailure(Call<ComCafeData> call2, Throwable t2){
                               Log.v("카페체크알림","실패");
                           }
                        });
                    }

                }

            }

            @Override
            public void onFailure(Call<List<Check>> call, Throwable t){
                Log.v("유저체크알림","실패");
            }
        });
        //HSJ 2020-08-22
        //cafedata2.add(new CafeData("ghfhfhfh다","12:30","14:30","asdasd",1,1,1));
       // cafedata2.add(new CafeData("키사마","10:30","14:30","asdasd",2,1,1));
        //HSj 2020-08-22 주석처리
        /*
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.VisitedList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        CafeAdapter adapter = new CafeAdapter(context, cafedata2);
        recyclerView.setAdapter(adapter);
        */
        SwipeVisited.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Execute code when refresh layout swiped

                cafedata2.clear(); // 데이터 다 불러오는 데이터베이스 배열 초기화
                //HSJ 주석처리 및 추가 2020-08-22
                User user = (User)getActivity().getApplication();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


                RetroService retroservice = retrofit.create(RetroService.class);

                Call<List<Check>> call = retroservice.requestUserCheck(user.getUno());

                call.enqueue(new Callback<List<Check>>(){
                    @Override
                    public void onResponse(Call<List<Check>> call, Response<List<Check>> response){
                        if(response.isSuccessful()){
                            Log.v("유저체크알림", "성공");
                            List<Check> re1= response.body();
                            //2020-09-16 HSJ check에 기록 없을시 무한 새로고침 수정
                            if(re1.size()==0){
                                recyclerView.setHasFixedSize(true);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);

                                CafeAdapter adapter = new CafeAdapter(context, cafedata2);
                                recyclerView.setAdapter(adapter);
                                SwipeVisited.setRefreshing(false);
                            }
                            //2020-09-16 HSJ
                            for(int i=0; i<re1.size(); i++){
                                //서버에 cno로 카페 정보 요청하기(전역변수로 쓸까? 느리면 생각해보자)
                                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                                final RetroService retroservice = retrofit.create(RetroService.class);

                                Call<ComCafeData> call2 = retroservice.requestCafe(re1.get(i).cno);

                                call2.enqueue(new Callback<ComCafeData>(){
                                    @Override
                                    public void onResponse(Call<ComCafeData> call2, Response<ComCafeData> response2){
                                        if(response2.isSuccessful()){
                                            Log.v("카페체크알림","성공");

                                            ComCafeData re2 = response2.body();
                                            cafedata2.add(new CafeData(re2.cafe_name, re2.start_time, re2.end_time, String.valueOf(re2.star),
                                                    re2.id, re2.x, re2.y, re2.phone, re2.notice,re2.seat_total,re2.business,
                                                    re2.tag1,re2.tag2));


                                            recyclerView.setHasFixedSize(true);
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);

                                            CafeAdapter adapter = new CafeAdapter(context, cafedata2);
                                            recyclerView.setAdapter(adapter);

                                            SwipeVisited.setRefreshing(false);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ComCafeData> call2, Throwable t2){
                                        Log.v("카페체크알림","실패");
                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Check>> call, Throwable t){
                        Log.v("유저체크알림","실패");
                    }
                });
                /*
                cafedata2.add(new CafeData("refresh","12:30","14:30","asdasd",1,1,1));
                cafedata2.add(new CafeData("가능?","10:30","14:30","asdasd",2,1,1));
               cafedata2.add(new CafeData("쓉가능","10:40","14:30","asdasd",2,1,1));

                recyclerView.setHasFixedSize(true);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                CafeAdapter adapter = new CafeAdapter(context, cafedata2);
                recyclerView.setAdapter(adapter);

                SwipeVisited.setRefreshing(false);

                 */
            }
        });


        return view;
    }
}
