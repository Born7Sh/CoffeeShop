package com.example.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildFragment_Favorite#newInstance} factory method to
 * create an instance of this fragment.
 */

// 즐겨찾기 페이지에서 파생된 2개의 탭중에 하나인 즐겨찾기-즐겨찾기임
// recycle뷰 쓴건 빼고는 특이사항없음
public class ChildFragment_Favorite extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CafeData> cafedata1 = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    int i;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildFragment_Favorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildFragment_Favorite.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildFragment_Favorite newInstance(String param1, String param2) {
        ChildFragment_Favorite fragment = new ChildFragment_Favorite();
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

        User user = (User)getActivity().getApplication();
        if(!user.getIsLogin()){
            Intent intent = new Intent(getActivity(),Activity_Login.class);
            startActivity(intent);
        }
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_child__favorite, container, false);
        final Context con = view.getContext();
        final SwipeRefreshLayout SwipeFavorite = (SwipeRefreshLayout) view.findViewById(R.id.SwipeFavorite);

        cafedata1.clear();

        //HSJ 2020-08-21 추가
        //서버에서 받아오기(즐겨찾기목록)
        //서버에서 카페데이터 받아오기
        final ComFavoriteAdapter favoriteadapter = new ComFavoriteAdapter();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<ComFavorite>> call = retroservice.getFavorite(user.getUno());

        call.enqueue(new Callback<List<ComFavorite>>() {
                         @Override
                         public void onResponse(Call<List<ComFavorite>> call, Response<List<ComFavorite>> response) {
                             if (response.isSuccessful()) {
                                 Log.v("즐겨찾기알림", "성공");
                                 List<ComFavorite> re1 = response.body();
                                 for (int i = 0; i < re1.size(); i++) {

                                     favoriteadapter.addFavorite(new ComFavorite(re1.get(i).uid, re1.get(i).cno));
                                 }

                                 Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                                 final RetroService retroservice = retrofit.create(RetroService.class);

                                 Call<ComCafeData> call2;

                                 for (i = 0; i < favoriteadapter.getCount(); i++) {
                                     call2 = retroservice.getFavoriteCafe(favoriteadapter.getCno(i));
                                     call2.enqueue(new Callback<ComCafeData>() {
                                         @Override
                                         public void onResponse(Call<ComCafeData> call2, Response<ComCafeData> response2) {
                                                     if (response2.isSuccessful()) {
                                                         Log.v("즐겨찾은카페데이터알림", "성공");
                                                         ComCafeData re2 = response2.body();
                                                         cafedata1.add(new CafeData(re2.cafe_name, re2.start_time, re2.end_time, String.valueOf(re2.star), re2.id, re2.x, re2.y, re2.phone, re2.notice,re2.seat_total));
                                                         context = view.getContext();
                                                         recyclerView = (RecyclerView) view.findViewById(R.id.FavoriteList);
                                                         recyclerView.setHasFixedSize(true);
                                                         layoutManager = new LinearLayoutManager(context);
                                                         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                                         recyclerView.setLayoutManager(layoutManager);

                                                         CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                                                         recyclerView.setAdapter(adapter);
                                             }

                                         }

                                         @Override
                                         public void onFailure(Call<ComCafeData> call2, Throwable t2) {
                                             Log.v("즐겨찾은카페데이터알림", "실패");
                                         }
                                     });
                                 }

                             }
                         }

                         @Override
                         public void onFailure(Call<List<ComFavorite>> call, Throwable t) {
                             Log.v("즐겨찾기가져오기 실패", "실패");
                         }

                         //HSJ 2020-08-21 추가


                     });
        //HSJ 2020-08-21 추가
        //서버에서 받아오기(카페 데이터)


        //HSJ 2020-08-21 추가




        SwipeFavorite.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               /*
                cafedata1.clear();
                cafedata1.add(new CafeData("됐다", "12:30", "14:30", "asdasd", 1, 1, 1,"123","됐다카페"));
                cafedata1.add(new CafeData("됬다", "10:30", "14:30", "asdasd", 2, 1, 1,"123","됫다카페"));

                recyclerView.setHasFixedSize(true);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                recyclerView.setAdapter(adapter);
                SwipeFavorite.setRefreshing(false);

                */
                final ComFavoriteAdapter favoriteadapter = new ComFavoriteAdapter();
                User user = (User)getActivity().getApplication();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                Call<List<ComFavorite>> call = retroservice.getFavorite(user.getUno());

                call.enqueue(new Callback<List<ComFavorite>>() {
                    @Override
                    public void onResponse(Call<List<ComFavorite>> call, Response<List<ComFavorite>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().size() != 0) {
                                Log.v("즐겨찾기알림", "성공");
                                List<ComFavorite> re1 = response.body();
                                for (int i = 0; i < re1.size(); i++) {

                                    favoriteadapter.addFavorite(new ComFavorite(re1.get(i).uid, re1.get(i).cno));
                                }

                                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                                final RetroService retroservice = retrofit.create(RetroService.class);

                                Call<ComCafeData> call2;
                                cafedata1.clear();
                                for (i = 0; i < favoriteadapter.getCount(); i++) {
                                    call2 = retroservice.getFavoriteCafe(favoriteadapter.getCno(i));
                                    call2.enqueue(new Callback<ComCafeData>() {
                                        @Override
                                        public void onResponse(Call<ComCafeData> call2, Response<ComCafeData> response2) {
                                            if (response2.isSuccessful()) {

                                                Log.v("즐겨찾은카페데이터알림", "성공");
                                                final ComCafeData re2 = response2.body();
                                                cafedata1.add(new CafeData(re2.cafe_name, re2.start_time, re2.end_time, String.valueOf(re2.star), re2.id, re2.x, re2.y, re2.phone, re2.notice,re2.seat_total));

                                                recyclerView.setHasFixedSize(true);

                                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                                recyclerView.setLayoutManager(layoutManager);

                                                CafeAdapter adapter = new CafeAdapter(context, cafedata1);
                                                recyclerView.setAdapter(adapter);
                                                SwipeFavorite.setRefreshing(false);
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<ComCafeData> call2, Throwable t2) {
                                            Log.v("즐겨찾은카페데이터알림", "실패");
                                        }
                                    });
                                }



                            }else{
                                SwipeFavorite.setRefreshing(false);
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<List<ComFavorite>> call, Throwable t) {
                        Log.v("즐겨찾기가져오기 실패", "실패");
                    }

                    //HSJ 2020-08-21 추가


                });
            }
        });

        return view;
    }


}
