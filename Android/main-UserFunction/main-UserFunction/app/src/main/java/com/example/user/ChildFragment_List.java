package com.example.user;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildFragment_List#newInstance} factory method to
 * create an instance of this fragment.
 */
// Home 페이지에서 파생된 2개의 탭중에 하나인 Home-목록임
// 탭 틀었을때 현재위치 계산해서 거리별로 나열하는 getNearCafe함수랑 내위치 구하는 getMylocation 함수가 있음
public class ChildFragment_List extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CafeData> cafedata1 = new ArrayList<>(); // 데이터베이스에서 받아오는 카페 데이터
    private ArrayList<CafeData> nearCafe = new ArrayList<>(); // 3km 안에있는 카페 데이터
    RecyclerView recyclerView;
    Spinner LocationSpinner;
    Context context;
    int Position;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildFragment_List() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChildFragment_List newInstance(String param1, String param2) {
        ChildFragment_List fragment = new ChildFragment_List();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        ChildFragment_List fragment = new ChildFragment_List();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_child__list, container, false);
        cafedata1.clear();

        //새로고침
        final SwipeRefreshLayout Swipe = (SwipeRefreshLayout) view.findViewById(R.id.Swipe);

        //HSJ 2020-08-22 추가
        //서버에서 카페 데이터 전부 받아오기
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        final RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<ComCafeData>> call = retroservice.requestAllCafe();

        call.enqueue(new Callback<List<ComCafeData>>() {
            @Override
            public void onResponse(Call<List<ComCafeData>> call, Response<List<ComCafeData>> response) {
                if (response.isSuccessful()) {
                    Log.v("AllCafe알림", "성공");
                    final List<ComCafeData> re1 = response.body();
                    for (int i = 0; i < re1.size(); i++) {
                        cafedata1.add(new CafeData(re1.get(i).cafe_name, re1.get(i).start_time, re1.get(i).end_time, String.valueOf(re1.get(i).star), re1.get(i).id, re1.get(i).x, re1.get(i).y, re1.get(i).phone, re1.get(i).notice,re1.get(i).seat_total));
                    }
                                 Location mylocation = getMylocation();  // 현재 내 위치를 구하는 함수
                                 getNearCafe(mylocation,Position);

                                 Context context = view.getContext();
                                 recyclerView = (RecyclerView) view.findViewById(R.id.List);
                                 recyclerView.setHasFixedSize(true);

                                 LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                 layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                 recyclerView.setLayoutManager(layoutManager);


                                 CafeAdapter adapter = new CafeAdapter(context, nearCafe);
                                 recyclerView.setAdapter(adapter);
                             }
                         }

                         @Override
                         public void onFailure(Call<List<ComCafeData>> call, Throwable t) {
                             Log.v("즐겨찾은카페데이터알림", "실패");
                         }
                     });
       // context = view.getContext();
        //CafeAdapter adapter = new CafeAdapter(context, nearCafe);
        //recyclerView.setAdapter(adapter);


        LocationSpinner = (Spinner) view.findViewById(R.id.LocationSpinner);
        LocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View views, int position, long id) {
                Location mylocation = getMylocation();  // 현재 내 위치를 구하는 함수
                Position = position;
                getNearCafe(mylocation, Position);
                Context context = view.getContext();


                recyclerView = (RecyclerView) view.findViewById(R.id.List);
                recyclerView.setHasFixedSize(true);

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                CafeAdapter adapter = new CafeAdapter(context, nearCafe);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Execute code when refresh layout swiped

                cafedata1.clear(); // 데이터 다 불러오는 데이터베이스 배열 초기화
                nearCafe.clear(); // 불러온거 거리별로 선별해서

                //HSJ 2020-08-22 추가
                //서버에서 카페 데이터 전부 받아오기
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


                final RetroService retroservice = retrofit.create(RetroService.class);

                Call<List<ComCafeData>> call = retroservice.requestAllCafe();

                call.enqueue(new Callback<List<ComCafeData>>() {
                    @Override
                    public void onResponse(Call<List<ComCafeData>> call, Response<List<ComCafeData>> response) {
                        if (response.isSuccessful()) {
                            Log.v("AllCafe알림", "성공");
                            final List<ComCafeData> re1 = response.body();
                            for (int i = 0; i < re1.size(); i++) {
                                cafedata1.add(new CafeData(re1.get(i).cafe_name, re1.get(i).start_time, re1.get(i).end_time, String.valueOf(re1.get(i).star), re1.get(i).id, re1.get(i).x, re1.get(i).y, re1.get(i).phone, re1.get(i).notice,re1.get(i).seat_total));
                            }


                            Location mylocation = getMylocation();  // 현재 내 위치를 구하는 함수
                            getNearCafe(mylocation,Position);

                            Context context = view.getContext();
                            recyclerView = (RecyclerView) view.findViewById(R.id.List);
                            recyclerView.setHasFixedSize(true);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);

                            CafeAdapter adapter = new CafeAdapter(context, nearCafe);
                            recyclerView.setAdapter(adapter);

                            Swipe.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<ComCafeData>> call2, Throwable t2) {
                        Log.v("즐겨찾은카페데이터알림", "실패");
                    }
                });
            }
        });
        return view;

    }

    public Location getMylocation() { // 내위치 구하는 함수
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else {
            Location GPSlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location NETWORKlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Float test1;
            Float test2 = null;

            if (GPSlocation != null) {
                test1 = GPSlocation.getAccuracy();
            } else {
                test1 = Float.valueOf("100");
            }

            if (NETWORKlocation != null) {
                test2 = NETWORKlocation.getAccuracy();
            } else {
                test1 = Float.valueOf("100");
            }


            if (GPSlocation == null && NETWORKlocation == null) { // 둘다 정보가 안뜨면 큰일남
                Toast.makeText(getContext(), "큰일났다.", Toast.LENGTH_LONG).show();
            } else if ((GPSlocation == null) || (test1 > test2)) {
                return NETWORKlocation;
            } else {
                return GPSlocation;
            }

        }
        return null;
    }

    public void getNearCafe(Location myLocation, int position) { // 3km안에 위치하면 nearCafe에 추가한다.
        nearCafe.clear();

        switch (position) {
            case 0: {
                for (int i = 0; i < cafedata1.size(); i++) {
                    Location location = new Location("cafe");

                    double Latitude = cafedata1.get(i).getCafeLatitude();
                    location.setLatitude(Latitude);

                    double Longitude = cafedata1.get(i).getCafeLongitude();
                    location.setLongitude(Longitude);

                    int distance = (int) myLocation.distanceTo(location) / 1000;
                    if (distance < 3) {
                        // 3km 안에 있으면 nearCafe에 추가함
                        nearCafe.add(cafedata1.get(i));
                        String a = nearCafe.get(i).getCafeName();
                    }
                }
                break;
            }

            case 1: {
                for (int i = 0; i < cafedata1.size(); i++) {
                    Location location = new Location("cafe");

                    double Latitude = cafedata1.get(i).getCafeLatitude();
                    location.setLatitude(Latitude);

                    double Longitude = cafedata1.get(i).getCafeLongitude();
                    location.setLongitude(Longitude);

                    int distance = (int) myLocation.distanceTo(location) / 1000;
                    if (distance < 5) {
                        // 3km 안에 있으면 nearCafe에 추가함
                        nearCafe.add(cafedata1.get(i));
                    }
                }
                break;
            }

            case 2: {
                for (int i = 0; i < cafedata1.size(); i++) {
                    Location location = new Location("cafe");

                    double Latitude = cafedata1.get(i).getCafeLatitude();
                    location.setLatitude(Latitude);

                    double Longitude = cafedata1.get(i).getCafeLongitude();
                    location.setLongitude(Longitude);

                    int distance = (int) myLocation.distanceTo(location) / 1000;
                    if (distance < 10) {
                        // 3km 안에 있으면 nearCafe에 추가함
                        nearCafe.add(cafedata1.get(i));
                    }
                }
                break;
            }


        }

    }


}
