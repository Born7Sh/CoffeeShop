package com.example.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class Activity_CafeInfo extends AppCompatActivity {
    // 카페 정보 페이지 임
    private final int MY_PERMISSION_REQUEST_CALL_PHONE = 1000;
    private String CafeNumber;
    int HeartSignal;
    RecyclerView reviewView;
    RecyclerView CafeImageView;
    ArrayList<String> imageList = new ArrayList<>();
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_info_);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MODE_PRIVATE);



        //카페 정보들 변수들
        TextView CafeName = (TextView) findViewById(R.id.CafeName);
        TextView CafePhone = (TextView) findViewById(R.id.CafePhone);
        TextView CafeInfo = (TextView) findViewById(R.id.CafeInfo);
        TextView CafeEvent = (TextView) findViewById(R.id.CafeEvent);
        final TextView RemainSeat = (TextView) findViewById(R.id.RemainSeat); // 남은 좌석들 표시 이거 시작하면서 초기화 해줘야함
        // 뒤로가기 이미지
        ImageView BackArrow = (ImageView) findViewById(R.id.BackArrow);
        // 즐겨찾기 이미지
        final ImageView Heart = (ImageView) findViewById(R.id.Heart);

        Button ReviewWrite = (Button) findViewById(R.id.ReviewWrite);

        // 리뷰에 있는 얘들
        TextView Grade = (TextView) findViewById(R.id.Grade); // 평점

        ImageView SquareAdd = (ImageView) findViewById(R.id.SquareAdd);

        SquareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Activity_Reviewlist.class);
                startActivity(intent);
            }
        });

        //@@@@@@@@@@@@@@@@@@@@@@@@@이미지 부분 설명@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // 카페 이미지 리사이클 뷰 넣는 부분 (리사이클 뷰 가로로 해도되더라고)
        // 클릭했을 떄 발생하는건 Adapter가 아니라 여기 가장 밑에 구현했음 함수로 구현했음
        // html 불러오는거 때문에 여기에 구현했는데 불편하면 말하삼 다시 Adpater 클래스 안으로 넣어줄게
        // html이 어떤 형식인지는 잘모르겠지만 바꿔야하게 여기에서는 CafeImageView, imageList 변수 자료형 바꿔야하고
        // 가장 밑에있는 함수에서는 Tag 형식 어떻게 받아올지는 모르겠지만 바꿔야 할거야
        // Adapter에서는 cafeImageList 형식만 바꾸면 될듯

        CafeImageView = findViewById(R.id.CafeImageRecycleView);
        LinearLayoutManager imageManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        CafeImageView.setLayoutManager(imageManager);
        // 이미지들을 있는것들 넣어봄


        // 리뷰 리사이클 뷰 넣는 부분
        reviewView = findViewById(R.id.ReviewRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewView.setLayoutManager(linearLayoutManager);

        final ReviewAdapter adapter = new ReviewAdapter(Activity_CafeInfo.this, 3);


        //reviewView.setAdapter(adapter);
        /* 데이터베이스에서 받아와서 아이콘 바꾸는거 부분 / 어캐 받아올지 몰라서 이렇게 주석처리함
        즐겨찾기 O 이면 하트 채워진거 / 즐겨찾기 X 이면 하트 비어있는거
        if() {
        Heart.setImageResource(R.mipmap.heartfill);
        HeartSignal = 1;
        }
        else {Heart.setImageResource(R.mipmap.heartempty);
        HeartSignal = 0;
        }
        */

        // 전화하기 버튼은 CafeCall 예약하기 버튼은 CafeReservation
        Button CafeCall = (Button) findViewById(R.id.CafeCall);
        Button CafeReservation = (Button) findViewById(R.id.CafeReservation);

        Intent intent = getIntent();
        CafeNumber = intent.getExtras().getString("CafePhone");

        CafeName.setText(intent.getExtras().getString("CafeName"));
        CafePhone.setText(intent.getExtras().getString("CafePhone"));
        CafeInfo.setText(intent.getExtras().getString("CafeInfo"));
        CafeEvent.setText(intent.getExtras().getString("CafeEvent"));
        //HSJ 2020-08-26 cafeStar//cafeReview 받아오기 자리 준비//Image받아오기
        //CafeImage cafeImage = (CafeImage)intent.getSerializableExtra("CafePicture");
        //HSJ 2020-08-26

        //HSJ 2020-08-22 cafePid 받아오기 추가
        final int cafePid = intent.getExtras().getInt("CafePid");
        //HSJ 2020-08-26 cafePicture url 받아오기 추가
        //final String cafePicture = intent.getExtras().getString("CafePicture");
        final CafeData cafeData = (CafeData)intent.getSerializableExtra("CafeData");
        //Bitmap bm;
        //bm = getImFromURL(cafePicture);
        //CafeImage.setImageBitmap(bm);

        //2020-09-16 HSJ
        //리뷰 평점 넣기
        Grade.setText(cafeData.getCafeGrade());
        //2020-09-16 HSJ
        User user = (User)getApplication();
        final List<Integer> UCF = new ArrayList<>();

        //2020-08 28 HSJ 리뷰 받아오기
        // 리뷰에 있는 얘들


        SquareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Activity_Reviewlist.class);
                intent.putExtra("CafePid",cafePid);
                startActivity(intent);
            }
        });


        reviewView = findViewById(R.id.ReviewRecyclerView);

        reviewView.setLayoutManager(linearLayoutManager);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

        RetroService retroservice = retrofit.create(RetroService.class);


        Call<List<Review>> call3 = retroservice.getReview(cafePid);

        call3.enqueue(new Callback<List<Review>>(){
            @Override
            public void onResponse(Call<List<Review>> call2, Response<List<Review>> response2){
                if(response2.isSuccessful()){
                    final List<Review> re1 = response2.body();
                    if(re1.size()>0) {
                        Log.v("리뷰내용", re1.get(0).review);

                        //URLThread
                        Bitmap bm;

                        for (int i = 0; i < re1.size(); i++) {
                            bm = getImFromURL(re1.get(i).picture);
                            adapter.addItem(new ReviewData(String.valueOf(re1.get(i).uid), "10", "남", re1.get(i).create_dt, re1.get(i).review, String.valueOf(re1.get(i).star), re1.get(i).picture));
                        }
                        reviewView.setAdapter(adapter);
                    }
                }
                Log.v("리뷰받아오기",response2.message());
            }

            @Override
            public void onFailure(Call<List<Review>> call2, Throwable t2){
                Log.v("리뷰 바당오기","실패");
            }
        });


        // ReviewData data1 = new ReviewData("김지우","70","남","오늘", "나 때는 카페보다는 국밥이였어","1");
        //ReviewData data2 = new ReviewData("가로쉬","5","남","1일 전", "겉은 바삭하고 속은 촉촉하네요","5");
        //ReviewData data3 = new ReviewData("느타리","26","여","13일 전", "카피는 친철하고 사장님은 맛있네요","5");
        //ReviewData data4 = new ReviewData("스랄","70","남","13일 전", "tlqkf","5");
        //ReviewData data5 = new ReviewData("흑인","30","남","한달전", "전지전능한 아카라트 영원한빛으로 날 보호하소서 전지전능한 아카라트 영원한빛으로 날 보호하소서 전지전능한 아카라트 영원한빛으로 날 보호하소서","5");
        //ReviewData data6 = new ReviewData("릴리쓰","1","여","두달전", "포기해라","5");

//        adapter.addItem(data1);
  //      adapter.addItem(data2);
    //    adapter.addItem(data3);
      //  adapter.addItem(data4);
        //adapter.addItem(data5);
        //adapter.addItem(data6);

        //2020-08-30 HSJ 현재 시간 기준 좌석 수 받아오기
        final BookList bookList = new BookList();
        Call<List<Booking1>> call7 = retroservice.getBook1(cafePid);
        call7.enqueue(new Callback<List<Booking1>>() {
            @Override
            public void onResponse(Call<List<Booking1>> call, Response<List<Booking1>> response) {
                if (response.isSuccessful()) {
                    List<Booking1> re1 = response.body();
                    for(int i=0;i<re1.size();i++){
                        bookList.setBook1(re1.get(i));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Booking1>> call, Throwable t) {
                Log.v("알림", "실패");
            }
        });

        Call<List<Booking2>> call8 = retroservice.getBook2(cafePid);
        call8.enqueue(new Callback<List<Booking2>>() {
            @Override
            public void onResponse(Call<List<Booking2>> call, Response<List<Booking2>> response) {
                if (response.isSuccessful()) {
                    List<Booking2> re1 = response.body();
                    for(int i=0;i<re1.size();i++){
                        bookList.setBook2(re1.get(i));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Booking2>> call, Throwable t) {
                Log.v("알림", "실패");
            }
        });

        Call<List<Booking3>> call9 = retroservice.getBook3(cafePid);
        call9.enqueue(new Callback<List<Booking3>>() {
            @Override
            public void onResponse(Call<List<Booking3>> call, Response<List<Booking3>> response) {
                if (response.isSuccessful()) {
                    List<Booking3> re1 = response.body();
                    for(int i=0;i<re1.size();i++){
                        bookList.setBook3(re1.get(i));
                        //현재 시간 띄워주기

                    }
                    long now = System.currentTimeMillis();
                    SimpleDateFormat timef = new SimpleDateFormat("hh:mm:ss");
                    String timestr = timef.format(new Date(now));
                    Log.v("시간알림",timestr);
                    String timeHs = timestr.substring(0,2);
                    String timeMs = timestr.substring(3,5);
                    int timeH = Integer.parseInt(timeHs);
                    int timeM = Integer.parseInt(timeMs);
                    if(timeM<30){
                        timeM = 0;
                    }
                    else if(timeM>=30){
                        timeM = 1;
                    }
                    RemainSeat.setText(String.valueOf(cafeData.getCafeSeat() - bookList.BookList.get((2*timeH) + timeM)));
                }
            }
            @Override
            public void onFailure(Call<List<Booking3>> call, Throwable t) {
                Log.v("알림", "실패");
            }
        });
        //2020-08-30 HSJ

        //2020-08-28 HSJ 리뷰
        //즐겨찾기 하트 아이콘 설정


        Map map2 = new HashMap();

        map2.put("uid",user.getUno());
        map2.put("cno",cafePid);
        Call<List<ComFavorite>> call2 = retroservice.getUserCafeFavorite(map2);

        call2.enqueue(new Callback<List<ComFavorite>>() {
            @Override
            public void onResponse(Call<List<ComFavorite>> call, Response<List<ComFavorite>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()!=0){
                        HeartSignal = 1;
                        Heart.setImageResource(R.mipmap.heartfill);
                        Log.v("알림","성공");
                        List<ComFavorite> lcf1= response.body();
                        UCF.add(lcf1.get(0).id);
                    }


                }
                else {
                    Log.v("알림", "성공");
                    HeartSignal = 0;
                    Heart.setImageResource(R.mipmap.heartempty);
                }
            }
            @Override
            public void onFailure(Call<List<ComFavorite>> call, Throwable t) {
                Log.v("알림", "실패");
            }
        });

        //HSJ 2020-08-26 카페 사진 받아오기(테스트완료)


        Call<List<CafeImage>> call4;
        call4 = retroservice.getCafeImages(cafePid);

        final CafeImageAdapter cafeImageAdapter = new CafeImageAdapter(this, imageList);
        final List<CafeImage> cafePictureList = new ArrayList<>();
        call4.enqueue(new Callback<List<CafeImage>>(){
            @Override
            public void onResponse(Call<List<CafeImage>> call4, Response<List<CafeImage>> response4){
                if(response4.isSuccessful()){
                    List<CafeImage> res = response4.body();
                    cafePictureList.add(res.get(0));
                    /*
                    imageList.add(getImFromURL(cafePictureList.get(0).img1));
                    imageList.add(getImFromURL(cafePictureList.get(0).img2));
                    imageList.add(getImFromURL(cafePictureList.get(0).img3));
                    imageList.add(getImFromURL(cafePictureList.get(0).img4));
                    imageList.add(getImFromURL(cafePictureList.get(0).img5));
                     */
                    imageList.add(cafePictureList.get(0).img1);
                    imageList.add(cafePictureList.get(0).img2);
                    imageList.add(cafePictureList.get(0).img3);
                    imageList.add(cafePictureList.get(0).img4);
                    imageList.add(cafePictureList.get(0).img5);


                    CafeImageView.setAdapter(cafeImageAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<CafeImage>> call4, Throwable t3){
                Log.v("카페이미지불러오기","오류");
            }
        });



        /*

        Call<List<Review>> call3 = retroservice.getReview(cafePid);

        call3.enqueue(new Callback<List<Review>>(){
            @Override
            public void onResponse(Call<List<Review>> call2, Response<List<Review>> response2){
                if(response2.isSuccessful()){
                    final List<Review> re1 = response2.body();
                    Log.v("리뷰내용",re1.get(0).review);
                    Log.v("사진",re1.get(0).picture);
                    //URLThread
                    Bitmap bm;
                    bm = getImFromURL(re1.get(0).picture);
                    CafeImage.setImageBitmap(bm);

                }
                Log.v("리뷰받아오기",response2.message());
            }

            @Override
            public void onFailure(Call<List<Review>> call2, Throwable t2){
                Log.v("리뷰 바당오기","실패");
            }
        });
        */


        //CafeImage.setImageBitmap(bm);

        //HSJ 2020-08-26


        //HSJ 2020-08-23 추가
        Heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) getApplication();

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                if (HeartSignal == 0) {

                    Heart.setImageResource(R.mipmap.heartfill);
                    HeartSignal = HeartSignal + 1;


                    Call<ComFavorite> call = retroservice.provideFavorite("json", cafePid, user.getUno());


                    call.enqueue(new Callback<ComFavorite>() {
                        @Override
                        public void onResponse(Call<ComFavorite> call, Response<ComFavorite> response) {
                            if (response.isSuccessful()) {
                                Log.v("알림", "성공");
                            }
                        }

                        @Override
                        public void onFailure(Call<ComFavorite> call, Throwable t) {
                            Log.v("알림", "실패");
                        }
                    });


                } else if(HeartSignal == 1) {
                    Heart.setImageResource(R.mipmap.heartempty);
                    HeartSignal = HeartSignal - 1;

                    Map map = new HashMap();
                    map.put("uid",user.getUno());
                    map.put("cno",cafePid);


                    Call<Void> call = retroservice.deleteFavorite(UCF.get(0));


                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.v("즐겨찾기삭제알림", "성공");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.v("즐겨찾기삭제알림", "실패");
                        }
                    });

                }
            }
        });


        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CafeCall.setOnClickListener(new View.OnClickListener() {
            /*
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                int permissionCheck = ContextCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(c, "권한 승인 필요", Toast.LENGTH_LONG).show();

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) v.getContext(), Manifest.permission.CALL_PHONE)) {
                        Toast.makeText(c, "전화 권환 필요", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST_CALL_PHONE);
                        Toast.makeText(c, "전화 권환 필요", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + CafeNumber)); // 얘는 다이얼 한번 띄어주고 전화걸리는거
                    //Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)); // 얘는 바로 전화걸리는거
                    startActivity(tt);

                }
            }*/
            @Override
            public void onClick(View v){

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                //2020-08-25 HSJ 카페이미지 받아오기
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.v("1차경로", path);
                    path = path + "/Pictures/Blue.png";
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Log.v("그림알림", String.valueOf(bitmap));
                    Log.v("경로알림", path);

                    //CafeImage.setImageBitmap(bitmap);


                //2020-08-25 HSJ 카페이미지 임시 생성
                File file = new File(path);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                MultipartBody.Part picture = MultipartBody.Part.createFormData("picture",file.getName(),requestFile);
                User user = (User)getApplication();

                //RequestBody picture = RequestBody.create(MediaType.parse("image/*"),file);
                int uid = user.getUno();
                /*
                Call<ResponseBody> call = retroservice.provideReviewP("json",user.getUno(),"맛있어요",picture,cafePid);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.v("알림", "성공");
                        }
                        Log.v("실패알림",response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.v("알림", "실패");
                    }
                });
                */

                /*
                Call<ResponseBody> call2 = retroservice.getReview(cafePid);

                call2.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response2){
                        if(response2.isSuccessful()){
                            //Response= response2.body();
                            //Log.v("리뷰내용",re1.get(0).review);
                            //CafeImage.setImageDrawable(re1.picture);
                            ResponseBody re1= response2.body();
                        }
                        Log.v("리뷰받아오기",response2.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call2, Throwable t2){
                        Log.v("리뷰 바당오기","실패");
                    }
                });
                */
            }
        });

        CafeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카페 예약 다이얼로그 띄우는거
                Intent intent = new Intent(v.getContext(), Activity_CafeReservation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //HSJ 2020-08-22 putExtra 추가
                intent.putExtra("CafePid",cafePid);
                intent.putExtra("BookList",bookList);
                intent.putExtra("CafeData",cafeData);
                startActivity(intent);
            }
        });

        int Visited; // 방문했는지 안했는지에 관한 변수
        ReviewWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User)getApplication();
                if(user.isVisited(cafePid)){ // 방문했을 때
                    Intent intent = new Intent(v.getContext(), Activity_ReviewWrite.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("CafePid",cafePid);
                    startActivity(intent);
                }else{
                    Toast.makeText(v.getContext(), "방문 안했는댑숑?", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResult) {

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CALL_PHONE: {
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "승인 허가", Toast.LENGTH_LONG).show();

                    // 승인 허가가 뜬 다음에 바로 전화가 걸려야 되서 여기도 있어야함

                    Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + CafeNumber)); // 얘는 다이얼 한번 띄어주고 전화걸리는거
                    //Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)); // 얘는 바로 전화걸리는거
                    startActivity(tt);
                } else {
                    Toast.makeText(this, "아직 승인 받지 않음", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }


    }
    // 이미지 아이템을 클릭했을 때 나오는 부분
    /*
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Tag 붙여온거 받아서 전체화면으로 틀어주는 식의 함수임
            int a = (int) v.getTag();
            Intent intent = new Intent(context, Activity_ImageBigSize.class);
            intent.putExtra("Image",a);
            startActivity(intent);
        }
    };
    */
    //2020-08-25 HSJ url로 서버에서 이미지 받아오기
    public Bitmap getImFromURL(final String ImUrl){
        if(ImUrl==null){
            return null;
        }
        final Bitmap[] bms = new Bitmap[1];
        Thread mThread = new Thread() {
            @Override
            public void run()
            {
                try {
                    Bitmap bm;
                    URL url = new URL(ImUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bm = BitmapFactory.decodeStream(is);

                    is.close();
                    bms[0] =bm;

                } catch (Exception e) {
                    Log.v("로딩오류", e.getMessage());
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
        }catch(Exception e2){

        }
        return bms[0];
    }


}