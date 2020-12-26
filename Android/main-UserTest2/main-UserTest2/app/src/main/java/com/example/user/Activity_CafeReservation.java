package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_CafeReservation extends AppCompatActivity {
    // 카페 예약 다이얼로그임
    // 동작할게 없어서 아직 구현 안함
    String currentTime;
    String AmPm;
    int currentHour;
    int currentMinute;
    TimePickerFragment mTimePickerFragment;
    Spinner TimeSpinner;
    final List<Integer> times = new ArrayList<Integer>();

    String selectData = "120";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cafe_reservation);
        EditText ReverseId = (EditText) findViewById(R.id.ReverseId); // 예약자 아이디
        EditText ReversePhone1 = (EditText) findViewById(R.id.ReversePhone1); // 예약자 폰번호
        EditText ReversePhone2 = (EditText) findViewById(R.id.ReversePhone2); // 예약자 폰번호
        EditText ReversePhone3 = (EditText) findViewById(R.id.ReversePhone3); // 예약자 폰번호
        final EditText ReverseNum = (EditText) findViewById(R.id.ReverseNum); // 예약자 몇명
        final TextView ReserveTime = findViewById(R.id.ReserveTime);
        final Button click1 = (Button) findViewById(R.id.click1);
        //HSJ 2020-08-22 cno추가
        Intent intent = getIntent();
        final int cno = intent.getExtras().getInt("CafePid");
        final BookList bookList = (BookList)intent.getSerializableExtra("BookList");
        final CafeData cafeData = (CafeData)intent.getSerializableExtra("CafeData");
        //남은 좌석 텍스트뷰
        final TextView remainNum = (TextView) findViewById(R.id.RemainNum);
        final Context context = this;

        final TextView ExpectTime = (TextView) findViewById(R.id.ExpectTime);

        Button CafeRefresh = (Button) findViewById(R.id.CafeRefresh); // 새로고침 버튼
        final Button Reservation = (Button) findViewById(R.id.CafeReservationReserve); // 예약버튼

        click1.setOnClickListener(new View.OnClickListener() {
            // 원래 여기서 다 값을 받아와서 초기화를 해줘야하는데 mTimePickerFragment가 실행되기전에 값들을 초기화하는게 먼저 실행되서 조금 복잡하게 됐음
            // 그래서 내가 한 방법은 mTimePickerFragment에서 setText 할 수 있어가지고 mTimePickerFragment에서 setText를 하고
            // Text값이 바뀔때 호출되는 addTextChangedListener -> afterTextChanged 에 함수를 달아서 값들을 초기화해줬음
            @Override
            public void onClick(View v) {
                Calendar mCalendar = Calendar.getInstance();
                int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                int min = mCalendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerFragment(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        AmPm = "오전";
                        String ExpectAmPm = "오전";

                        int ExpectHour = selectedHour + 2; // 기본값인 상태에서 + 2시간 상태

                        int afterHour;  // +2 상태에서 12시가 지났을 때 판별하기 위해서 만든 변수

                        if (selectedHour > 11) {
                            AmPm = "오후";
                        }

                        if (ExpectHour > 11) {
                            if (ExpectHour >= 24) {
                                ExpectAmPm = "오전";
                            } else {
                                ExpectAmPm = "오후";
                            }
                        }

                        if (selectedHour > 12) {
                            currentHour = selectedHour - 12;
                            currentMinute = selectedMinute;
                        } else {
                            currentHour = selectedHour;
                            currentMinute = selectedMinute;
                        }

                        if (ExpectHour > 12) {
                            if (ExpectHour >= 24) {
                                afterHour = ExpectHour - 24;
                            } else {
                                afterHour = ExpectHour - 12;
                            }
                        } else {
                            afterHour = ExpectHour;
                        }

                        ReserveTime.setText(AmPm + " " + String.valueOf(currentHour) + "시" + " " + String.valueOf(selectedMinute) + "분");

                        ExpectTime.setText(ExpectAmPm + " " + String.valueOf(afterHour) + "시" + " " + String.valueOf(selectedMinute) + "분");


                        if(AmPm.equals("오후")){
                            currentHour = currentHour + 12;
                        }

                        if(String.valueOf(cafeData.getCafeSeat() - bookList.BookList.get((2*currentHour) + (currentMinute/30))).equals("0")){
                            Reservation.setEnabled(false);
                        }
                        else{
                            Reservation.setEnabled(true);
                        }
                        remainNum.setText(String.valueOf(cafeData.getCafeSeat() - bookList.BookList.get((2*currentHour) + (currentMinute/30))));



                        if(AmPm.equals("오후")){
                            currentHour = currentHour - 12;
                        }
                        TimeSpinner.setSelection(0);
                        TimeSpinner.setEnabled(true);
                        TimeSpinner.setClickable(true);
                    }
                }, hour, min, false);//Yes 24 hour time
                timePicker.setTitle("Select Time");
                timePicker.show();
            }
        });

        TimeSpinner = (Spinner) findViewById(R.id.TimeSpinner);
        TimeSpinner.setEnabled(false);
        TimeSpinner.setClickable(false);
        TimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String a = ReserveTime.getText().toString();

                if (a.equals("") || a == null || a.equals(null)) {

                } else {
                    //2020-08-30 HSJ 추가
                    if(AmPm.equals("오후")){
                        currentHour = currentHour + 12;
                    }

                    remainNum.setText(String.valueOf(cafeData.getCafeSeat() - bookList.BookList.get((2*currentHour) + (currentMinute/30))));

                    if(AmPm.equals("오후")){
                        currentHour = currentHour - 12;
                    }
                    //HSJ

                    String time = parent.getItemAtPosition(position).toString();
                    String date[] = time.split("분");
                    selectData = date[0];

                    String expectAmPm = AmPm;
                    int expectHour; // 계산이 적용된 시간
                    int expectMinute;  // 계산이 적용된 분
                    int afterHour; // 12시 지났을 때 나오는 변수
                    times.clear();//예약시간 초기화
                    switch (selectData) {
                        case "30": {
                            if (currentMinute == 30) {
                                expectHour = currentHour + 1;
                                expectMinute = currentMinute - 30;
                            } else {
                                expectHour = currentHour;
                                expectMinute = currentMinute + 30;
                            }
                            if (expectAmPm.equals("오후")) {
                                expectHour = expectHour + 12;
                            }
                            if (expectHour > 11) {
                                if (expectHour >= 24) {
                                    expectAmPm = "오전";
                                } else {
                                    expectAmPm = "오후";
                                }
                            }

                            if (expectHour > 12) {
                                if (expectHour >= 24) {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 24) + "시" + " " + String.valueOf(expectMinute) + "분");
                                } else {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 12) + "시" + " " + String.valueOf(expectMinute) + "분");
                                }
                            } else {
                                ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour) + "시" + " " + String.valueOf(expectMinute) + "분");
                            }
                            times.add(0);
                        }
                        break;
                        case "60": {
                            expectHour = currentHour + 1;
                            expectMinute = currentMinute;

                            if (expectAmPm.equals("오후")) {
                                expectHour = expectHour + 12;
                            }

                            if (expectHour > 11) {
                                if (expectHour >= 24) {
                                    expectAmPm = "오전";
                                } else {
                                    expectAmPm = "오후";
                                }
                            }

                            if (expectHour > 12) {
                                if (expectHour >= 24) {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 24) + "시" + " " + String.valueOf(expectMinute) + "분");
                                } else {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 12) + "시" + " " + String.valueOf(expectMinute) + "분");
                                }
                            } else {
                                ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour) + "시" + " " + String.valueOf(expectMinute) + "분");
                            }
                            times.add(1);
                        }
                        break;
                        case "90": {
                            if (currentMinute == 30) {
                                expectHour = currentHour + 2;
                                expectMinute = currentMinute - 30;
                            } else {
                                expectHour = currentHour + 1;
                                expectMinute = currentMinute + 30;

                            }

                            if (expectAmPm.equals("오후")) {
                                expectHour = expectHour + 12;
                            }

                            if (expectHour > 11) {
                                if (expectHour >= 24) {
                                    expectAmPm = "오전";
                                } else {
                                    expectAmPm = "오후";
                                }
                            }

                            if (expectHour > 12) {
                                if (expectHour >= 24) {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 24) + "시" + " " + String.valueOf(expectMinute) + "분");
                                } else {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 12) + "시" + " " + String.valueOf(expectMinute) + "분");
                                }
                            } else {
                                ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour) + "시" + " " + String.valueOf(expectMinute) + "분");
                            }
                            times.add(2);
                        }
                        break;
                        case "120": {
                            expectHour = currentHour + 2;
                            expectMinute = currentMinute;

                            if (expectAmPm.equals("오후")) {
                                expectHour = expectHour + 12;
                            }

                            if (expectHour > 11) {
                                if (expectHour >= 24) {
                                    expectAmPm = "오전";
                                } else {
                                    expectAmPm = "오후";
                                }
                            }

                            if (expectHour > 12) {
                                if (expectHour >= 24) {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 24) + "시" + " " + String.valueOf(expectMinute) + "분");
                                } else {
                                    ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour - 12) + "시" + " " + String.valueOf(expectMinute) + "분");
                                }
                            } else {
                                ExpectTime.setText(expectAmPm + " " + String.valueOf(expectHour) + "시" + " " + String.valueOf(expectMinute) + "분");
                            }
                            times.add(3);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         /* if () { if문을 통해서 예약을 판별하고 예약이 되었다면 setText를 한다.

                데이터베이스에서 가저오는걸로 대입입
               ReverseId.setText();
                ReversePhone.setText();
                ReverseNum.setText();

         }
     */



        Reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User)getApplication();
                int testMinute;
                String testTime = "00:00:00";
                Log.v("uid알림",user.getUid());
                Log.v("uno알림",String.valueOf(user.getUno()));
                if(AmPm.equals("오후")){
                    currentHour = currentHour +12;
                }
                if(currentMinute==0) {
                    if (currentHour < 10) {
                        testTime = "0" + String.valueOf(currentHour) + ":00" + ":" + "00";
                    } else {

                        testTime = String.valueOf(currentHour) + ":00" + ":" + "00";
                    }
                }
                else if(currentMinute==30) {
                    if (currentHour < 10) {
                        testTime = "0" + String.valueOf(currentHour) + ":00" + ":" + "00";
                    } else {
                        testTime = String.valueOf(currentHour) + ":30:" + "00";
                    }
                }
                //예약시간이 가능한지 테스트
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                Call<Time> call5 = retroservice.requestTime(testTime,String.valueOf(user.getUno()));

                call5.enqueue(new Callback<Time>(){
                    @Override
                            public void onResponse(Call<Time> call, Response<Time> response){
                                if(response.isSuccessful()){
                                    Time time1 = response.body();
                                    if(time1.code.toString().equals("0000")){
                                        if(time1.msg.equals("True")){
                                            Log.v("예약시간확인알림",time1.msg);
                                            MakeBook(currentHour,currentMinute,ReverseNum,cno,times.get(0));
                                        }
                                        else if(time1.msg.equals("False")){
                                            Log.v("예약시간확인알림",time1.msg);
                                        }
                                        else{
                                            Log.v("예약시간확인알림",time1.msg);
                                        }

                                    }
                                    else if(time1.code.toString().equals("1001")){

                                    }
                        }
                    }

                    @Override
                    public void onFailure(Call<Time> call, Throwable t){
                        Log.v("알림",t.getMessage());
                    }
                });


            }
        });
        ImageView BackArrow = (ImageView) findViewById(R.id.BackArrow);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }
    public void MakeBook(int startH,int startM,EditText ReverseNum,int cno,int time){
        User user = (User)getApplication();
        Book book = new Book();
        //HSJ 2020-08-22 추가
        //서버에 check 쏘기
        int seatNum = Integer.parseInt(String.valueOf(ReverseNum.getText()));
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        RetroService retroservice = retrofit.create(RetroService.class);

        //서버에 Booking 쏘기
        final int startindex = startH*2 + startM/30;
        int endindex = startindex + time;

        if(endindex>=48){
            endindex = endindex-48;
        }


        for(int i=0; i<time; i++){
            book.setTime(startindex + i, true);
        }
        if(startindex<17 && endindex<17){
            //Booking1
            /*
            String start = "t0" + String.valueOf(startH);
            if(startM == 0){
                start = start + "00";
            }
            else{
                start = start + "30";
            }
            String end = "t0" + String.valueOf(endH);
            if(endM ==0){
                end = end + "00";
            }
            else{
                end = end + "30";
            }
            Log.v("예약시작시간",start);
            Log.v("예약 종료시간",end);
            */

            Call<Booking1> call2 = retroservice.provideBooking1("json",cno,seatNum,user.getUno(),book.TimeList.get(0),book.TimeList.get(1),book.TimeList.get(2),book.TimeList.get(3),book.TimeList.get(4),book.TimeList.get(5),book.TimeList.get(6),
                    book.TimeList.get(7),book.TimeList.get(8),book.TimeList.get(9),book.TimeList.get(10),book.TimeList.get(11),book.TimeList.get(12),book.TimeList.get(13),book.TimeList.get(14),book.TimeList.get(15),book.TimeList.get(16),book.TimeList.get(17));

            call2.enqueue(new Callback<Booking1>(){
                @Override
                public void onResponse(Call<Booking1> call, Response<Booking1> response){
                    if(response.isSuccessful()){
                        Log.v("Booking1알림","성공");
                    }
                    Log.v("Booking1알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking1> call, Throwable t){
                    Log.v("Booking1알림","실패");
                }
            });


        }
        else if(startindex<17 && endindex >=17){
            //Booking1,2
            //서버에 Booking 쏘기
            Call<Booking1> call2 = retroservice.provideBooking1("json",cno,seatNum,user.getUno(),book.TimeList.get(0),book.TimeList.get(1),book.TimeList.get(2),book.TimeList.get(3),book.TimeList.get(4),book.TimeList.get(5),book.TimeList.get(6),
                    book.TimeList.get(7),book.TimeList.get(8),book.TimeList.get(9),book.TimeList.get(10),book.TimeList.get(11),book.TimeList.get(12),book.TimeList.get(13),book.TimeList.get(14),book.TimeList.get(15),book.TimeList.get(16),book.TimeList.get(17));

            call2.enqueue(new Callback<Booking1>(){
                @Override
                public void onResponse(Call<Booking1> call, Response<Booking1> response){
                    if(response.isSuccessful()){
                        Log.v("Booking1알림","성공");
                    }
                    Log.v("Booking1알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking1> call, Throwable t){
                    Log.v("Booking1알림","실패");
                }
            });


            Call<Booking2> call3 = retroservice.provideBooking2("json",cno,seatNum,user.getUno(),book.TimeList.get(18),book.TimeList.get(19),book.TimeList.get(20),book.TimeList.get(21),book.TimeList.get(22),book.TimeList.get(23),book.TimeList.get(24),
                    book.TimeList.get(25),book.TimeList.get(26),book.TimeList.get(27),book.TimeList.get(28),book.TimeList.get(29),book.TimeList.get(30),book.TimeList.get(31),book.TimeList.get(32),book.TimeList.get(33));

            call3.enqueue(new Callback<Booking2>(){
                @Override
                public void onResponse(Call<Booking2> call, Response<Booking2> response){
                    if(response.isSuccessful()){
                        Log.v("Booking2알림","성공");
                    }
                    Log.v("Booking2알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking2> call, Throwable t){
                    Log.v("Booking2알림","실패");
                }
            });


        }
        else if(startindex>=17 && startindex<34 && endindex>=17 && endindex<34){
            //Booking2
            Call<Booking2> call3 = retroservice.provideBooking2("json",cno,seatNum,user.getUno(),book.TimeList.get(18),book.TimeList.get(19),book.TimeList.get(20),book.TimeList.get(21),book.TimeList.get(22),book.TimeList.get(23),book.TimeList.get(24),
                    book.TimeList.get(25),book.TimeList.get(26),book.TimeList.get(27),book.TimeList.get(28),book.TimeList.get(29),book.TimeList.get(30),book.TimeList.get(31),book.TimeList.get(32),book.TimeList.get(33));

            call3.enqueue(new Callback<Booking2>(){
                @Override
                public void onResponse(Call<Booking2> call, Response<Booking2> response){
                    if(response.isSuccessful()){
                        Log.v("Booking2알림","성공");
                    }
                    Log.v("Booking2알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking2> call, Throwable t){
                    Log.v("Booking2알림","실패");
                }
            });
        }
        else if(startindex>=17 && startindex<34 && endindex>=34){
            //Booking2,3
            Call<Booking2> call3 = retroservice.provideBooking2("json",cno,seatNum,user.getUno(),book.TimeList.get(18),book.TimeList.get(19),book.TimeList.get(20),book.TimeList.get(21),book.TimeList.get(22),book.TimeList.get(23),book.TimeList.get(24),
                    book.TimeList.get(25),book.TimeList.get(26),book.TimeList.get(27),book.TimeList.get(28),book.TimeList.get(29),book.TimeList.get(30),book.TimeList.get(31),book.TimeList.get(32),book.TimeList.get(33));

            call3.enqueue(new Callback<Booking2>(){
                @Override
                public void onResponse(Call<Booking2> call, Response<Booking2> response){
                    if(response.isSuccessful()){
                        Log.v("Booking2알림","성공");
                    }
                    Log.v("Booking2알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking2> call, Throwable t){
                    Log.v("Booking2알림","실패");
                }
            });

            Call<Booking3> call4 = retroservice.provideBooking3("json",cno,seatNum,user.getUno(),book.TimeList.get(34),book.TimeList.get(35),book.TimeList.get(36),book.TimeList.get(37),book.TimeList.get(38),book.TimeList.get(39),book.TimeList.get(40),
                    book.TimeList.get(41),book.TimeList.get(42),book.TimeList.get(43),book.TimeList.get(44),book.TimeList.get(45),book.TimeList.get(46),book.TimeList.get(47));

            call4.enqueue(new Callback<Booking3>(){
                @Override
                public void onResponse(Call<Booking3> call, Response<Booking3> response){
                    if(response.isSuccessful()){
                        Log.v("Booking3알림","성공");
                    }
                    Log.v("Booking3알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking3> call, Throwable t){
                    Log.v("Booking3알림","실패");
                }
            });
        }
        else if(startindex>=34 && endindex>=34){
            //Booking3
            Call<Booking3> call4 = retroservice.provideBooking3("json",cno,seatNum,user.getUno(),book.TimeList.get(34),book.TimeList.get(35),book.TimeList.get(36),book.TimeList.get(37),book.TimeList.get(38),book.TimeList.get(39),book.TimeList.get(40),
                    book.TimeList.get(41),book.TimeList.get(42),book.TimeList.get(43),book.TimeList.get(44),book.TimeList.get(45),book.TimeList.get(46),book.TimeList.get(47));

            call4.enqueue(new Callback<Booking3>(){
                @Override
                public void onResponse(Call<Booking3> call, Response<Booking3> response){
                    if(response.isSuccessful()){
                        Log.v("Booking3알림","성공");
                    }
                    Log.v("Booking3알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking3> call, Throwable t){
                    Log.v("Booking3알림","실패");
                }
            });
        }
        else if(startH>=34 && endindex<17){
            //Booking3,1
            Call<Booking3> call4 = retroservice.provideBooking3("json",cno,seatNum,user.getUno(),book.TimeList.get(34),book.TimeList.get(35),book.TimeList.get(36),book.TimeList.get(37),book.TimeList.get(38),book.TimeList.get(39),book.TimeList.get(40),
                    book.TimeList.get(41),book.TimeList.get(42),book.TimeList.get(43),book.TimeList.get(44),book.TimeList.get(45),book.TimeList.get(46),book.TimeList.get(47));

            call4.enqueue(new Callback<Booking3>(){
                @Override
                public void onResponse(Call<Booking3> call, Response<Booking3> response){
                    if(response.isSuccessful()){
                        Log.v("Booking3알림","성공");
                    }
                    Log.v("Booking3알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking3> call, Throwable t){
                    Log.v("Booking3알림","실패");
                }
            });

            Call<Booking1> call2 = retroservice.provideBooking1("json",cno,seatNum,user.getUno(),book.TimeList.get(0),book.TimeList.get(1),book.TimeList.get(2),book.TimeList.get(3),book.TimeList.get(4),book.TimeList.get(5),book.TimeList.get(6),
                    book.TimeList.get(7),book.TimeList.get(8),book.TimeList.get(9),book.TimeList.get(10),book.TimeList.get(11),book.TimeList.get(12),book.TimeList.get(13),book.TimeList.get(14),book.TimeList.get(15),book.TimeList.get(16),book.TimeList.get(17));

            call2.enqueue(new Callback<Booking1>(){
                @Override
                public void onResponse(Call<Booking1> call, Response<Booking1> response){
                    if(response.isSuccessful()){
                        Log.v("Booking1알림","성공");
                    }
                    Log.v("Booking1알림",response.message());
                }

                @Override
                public void onFailure(Call<Booking1> call, Throwable t){
                    Log.v("Booking1알림","실패");
                }
            });
        }
        //서버에 check 쏘기
        String start_time = String.valueOf(startH) + ":" + String.valueOf(startM) + ":00";
        long now = System.currentTimeMillis();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-mm-dd");
        String datestr = datef.format(new Date(now));
        Log.v("날짜알림",datestr);
        String timeHs=String.valueOf(startH);
        String timeMs=String.valueOf(startM);
        String endHs;
        String endMs;
        if(startH<10){
            timeHs = "0" + String.valueOf(startH);
        }

        if(startM==0){
            timeMs = "0" + String.valueOf(startM);
        }
        endindex = endindex +1;

        int endH = endindex/2;
        int endM = (endindex%2) * 30;
        endHs = String.valueOf(endH);
        endMs = String.valueOf(endM);
        if(endH<10){
            endHs = "0" + String.valueOf(endH);
        }
        if(endM==0){
            endMs = "0" + String.valueOf(endM);
        }
        String start = timeHs + ":" + timeMs + ":00";
        String end = endHs + ":" + endMs + ":00";
        user.setRsvTime(start,end);
        Call<Check> call = retroservice.provideCheck("json",user.getUno(),user.getUid(),datestr,start,datestr,end,Integer.parseInt(String.valueOf(ReverseNum.getText())),cno);


        call.enqueue(new Callback<Check>(){
            @Override
            public void onResponse(Call<Check> call, Response<Check> response){
                if(response.isSuccessful()){
                    Log.v("알림","성공");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Check> call, Throwable t){
                Log.v("알림","실패");
            }
        });

        //알림 추가하기
        user.createNotificationChannel();
        user.sendNoti();
    }


}
