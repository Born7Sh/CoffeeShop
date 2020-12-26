package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Info#newInstance} factory method to
 * create an instance of this fragment.
 */

// 개인 정보 관리하는 탭임 특이 사항 없음
public class Fragment_Info extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout frameReservation;
    LinearLayout frameNoReservation;
    int Reservation = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 데이터베이스에서 이런 형태로 받아온다는 가정하에 만듬
    // 초기화는 안에서 했음

    public Fragment_Info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Info.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Info newInstance(String param1, String param2) {
        Fragment_Info fragment = new Fragment_Info();
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

        final View view = inflater.inflate(R.layout.fragment__info, container, false);
        // Inflate the layout for this fragment


        LinearLayout UserInfo = (LinearLayout) view.findViewById(R.id.UserInfo);
        LinearLayout Chart = (LinearLayout) view.findViewById(R.id.Chart);
        LinearLayout Announce = (LinearLayout) view.findViewById(R.id.Announce);

        UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_UserInfo.class);
                startActivity(intent);

            }
        });

        Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Chart.class);
                startActivity(intent);

            }
        });

        Announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Announce.class);
                startActivity(intent);

            }
        });

        //2020-09-11 JHM (Setting 페이지 구현했음)
        ImageView Setting = (ImageView) view.findViewById(R.id.Setting);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Setting.class);
                startActivity(intent);
            }
        });

        // 두개의 프레임이 겹쳐있는 형태
        // 조건문을 통해서 아닌거 삭제하는 형태
        frameReservation = (LinearLayout) view.findViewById(R.id.frameReservation);
        frameNoReservation = (LinearLayout) view.findViewById(R.id.frameNoReservation);

        final FrameLayout frame = (FrameLayout) view.findViewById(R.id.frame);
        final TextView InfoCafeName = (TextView) view.findViewById(R.id.InfoCafeName);
        final TextView InfoCafeSeat = (TextView) view.findViewById(R.id.InfoCafeSeat);
        final TextView InfoCafeStartTime = (TextView) view.findViewById(R.id.InfoCafeStartTime);
        final TextView InfoCafeEndTime = (TextView) view.findViewById(R.id.InfoCafeEndTime);

        Button Confirmation = (Button) view.findViewById(R.id.Confirmation); // 예약 확정
        final Button Cancel = (Button) view.findViewById(R.id.Cancel); // 예약 취소
        Button Extension = (Button) view.findViewById(R.id.Extension); // 예약 연장
        Button Checkout = (Button) view.findViewById(R.id.Checkout); // 예약 퇴실


        //예약이 있다는 걸 가정하고 만든거 0은 예약 읍다 1은 예약 있다.
        //int Reservation = 0;
        int Reservation = 1;

        if (Reservation == 1) { //예약됨
            frame.removeView(frameNoReservation);

            InfoCafeName.setText("커피야 가즈아");
            InfoCafeSeat.setText("2/4");
            InfoCafeStartTime.setText("3/6");
            InfoCafeEndTime.setText("3/7");

        } else if (Reservation == 0) { // 예약 안됨
            frame.removeView(frameReservation);
        }

        //HSJ 2020-08-22 추가
        //예약 받아오기
        final User user = (User)getActivity().getApplication();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<Check>> call = retroservice.requestUserCheck(user.getUno());

        call.enqueue(new Callback<List<Check>>(){
            @Override
            public void onResponse(Call<List<Check>> call, Response<List<Check>> response){
                if(response.isSuccessful()){
                    Log.v("유저체크알림", "성공");
                    final List<Check> re1= response.body();
                    if(re1.size()>=1) {
                        //서버에 cno로 카페 정보 요청하기(전역변수로 쓸까? 느리면 생각해보자)
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                        RetroService retroservice = retrofit.create(RetroService.class);

                        Call<ComCafeData> call2 = retroservice.requestCafe(re1.get(re1.size() - 1).cno);

                        call2.enqueue(new Callback<ComCafeData>() {
                            @Override
                            public void onResponse(Call<ComCafeData> call2, Response<ComCafeData> response2) {
                                if (response2.isSuccessful()) {
                                    Log.v("카페체크알림", "성공");
                                    ComCafeData re2 = response2.body();

                                    InfoCafeName.setText(re2.cafe_name);
                                    InfoCafeSeat.setText(String.valueOf(re1.get(re1.size() - 1).sno) + "/" + String.valueOf(re2.seat_total - re2.seat_curr));
                                    InfoCafeStartTime.setText(re1.get(re1.size() - 1).start_time);
                                    InfoCafeEndTime.setText(re1.get(re1.size() - 1).end_time);
                                    user.setCheckId(re1.get(re1.size()-1).id);
                                }
                                Log.v("카페체크알림","카페가존재하지않습니다");
                            }


                            @Override
                            public void onFailure(Call<ComCafeData> call2, Throwable t2) {
                                Log.v("카페체크알림", "실패");
                            }
                        });
                    }
                    else {
                        InfoCafeName.setText("예약이 존재하지 않습니다");
                        InfoCafeSeat.setText("");
                        InfoCafeStartTime.setText("");
                        InfoCafeEndTime.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Check>> call, Throwable t){
                Log.v("유저체크알림","실패");
            }
        });
        //HSJ 2020-08-22
        Confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel.setEnabled(false);
            }
        });


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //지우기(check)
                final User user = (User)getActivity().getApplication();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


                RetroService retroservice = retrofit.create(RetroService.class);

                Call<Void> call = retroservice.DeleteCheck(user.getCheckId());

                call.enqueue(new Callback<Void>(){
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response){
                        Log.v("예약삭제알림","완료");
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t){
                        Log.v("예약삭제알림","실패");
                    }
                });
                frame.removeView(frameReservation);

                frame.addView(frameNoReservation);


            }
        });


        Extension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.removeView(frameReservation);

                frame.addView(frameNoReservation);
            }
        });


        return view;
    }


}
