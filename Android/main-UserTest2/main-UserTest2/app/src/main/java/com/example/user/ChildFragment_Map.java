package com.example.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildFragment_Map#newInstance} factory method to
 * create an instance of this fragment.
 */

// Home 페이지에서 파생된 2개의 탭중에 하나인 Home-지도임

public class ChildFragment_Map extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // 데이터베이스에서 이런 형태로 받아온다는 가정하에 만듬
    // 초기화는 안에서 했음
    private ArrayList<CafeData> listCafeData = new ArrayList<>();
    private Context con;
    private MapView mapView = null;
    LatLng mylocation = null;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    private FusedLocationProviderClient mFusedLocationClient; // 현재위치를 얻고

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildFragment_Map() {
        // Required empty public constructor
    }

    public static ChildFragment_Map newInstance(String param1, String param2) {

        ChildFragment_Map fragment = new ChildFragment_Map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ChildFragment_Map newInstance() {
        ChildFragment_Map fragment = new ChildFragment_Map();
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
        View view = inflater.inflate(R.layout.fragment_child__map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        con = view.getContext();
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        //HSJ 2020-08-22 추가
        //서버에서 카페 데이터 전부 받아오기
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();


        RetroService retroservice = retrofit.create(RetroService.class);

        Call<List<ComCafeData>> call = retroservice.requestAllCafe();

        call.enqueue(new Callback<List<ComCafeData>>(){
           @Override
           public void onResponse(Call<List<ComCafeData>> call, Response<List<ComCafeData>> response){
               if(response.isSuccessful()){
                   final List<ComCafeData> re1= response.body();
                   for(int i=0;i<re1.size();i++){
                       listCafeData.add(new CafeData(re1.get(i).cafe_name,re1.get(i).start_time,re1.get(i).end_time,String.valueOf(re1.get(i).star),
                               re1.get(i).id,re1.get(i).x,re1.get(i).y,re1.get(i).phone,re1.get(i).notice,re1.get(i).seat_total,re1.get(i).business,
                               re1.get(i).tag1,re1.get(i).tag2));
                   }

               }

           }


           @Override
            public void onFailure(Call<List<ComCafeData>> call, Throwable t){

           }
        });


        //HSJ 2020-08-22 추가

       // CafeData data1 = new CafeData("런하는 커피", "10:30", "11:30", "5.0", 5, 37.361903, 126.730290,"123","안녕하세요");
       // CafeData data2 = new CafeData("페이퍼 타월", "5:30", "21:30", "5.0", 6, 37.357053, 126.734971,"123","안녕하세요");
       // listCafeData.add(data1);
       // listCafeData.add(data2);

        final ImageButton mylocation = (ImageButton) view.findViewById(R.id.MyLocation);
        mylocation.setOnClickListener(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 시작하자마자 자기 위치로 가는 방법 <<
        mMap = googleMap;
        final LocationManager lm = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        if (mParam1 != null && mParam2 != null) { // 인자가 날라오면 marker 추가하고 그위치로 가는것

            double mp1 = Double.parseDouble(mParam1);
            double mp2 = Double.parseDouble(mParam2);
            LatLng mylocation = new LatLng(mp1, mp2);
            mMap.addMarker(new MarkerOptions()
                    .position(mylocation)
                    .title("기본 위치")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    ));

            // 마커 클릭이벤트 추가한다는거 마커는 지도에 있는 빨강색 동그란거
            mMap.setOnMarkerClickListener(this);
            // 마커 누르면 info라고 작은 창 뜨는데 그거 클릭이벤트 추가한다는거
            mMap.setOnInfoWindowClickListener(this);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 16);
            //mMap.animateCamera(cameraUpdate);
            mMap.moveCamera(cameraUpdate);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        } else if (Build.VERSION.SDK_INT >= 23 &&

                // 구글 맵이 틀어지면서 위치 권한에 대해 물어봄 // 위치 권한 동의 안되어있으면 권한동의 띄워주는 조건문
                ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            // 권한 동의에 체크에 대해서 확인하는 변수임
            // 권한 동의를 체크 했다면 PackageManager.PERMISSION_GRANTED가 나오고
            // 권한 동의를 체크 안했으면  PackageManager.PERMISSION_DENIED 나옴
            int permssionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

            if (permssionCheck == PackageManager.PERMISSION_GRANTED) {
                // 위에서 권한동의 체크를 했다면 조건문 통해서 여기로 들어옴
                // 권한동의를 했으니깐 함수호출을 다시하면 제일 처음 조건문에 안걸리고 정상 실행될거임
                onMapReady(mMap);

            } else if (permssionCheck == PackageManager.PERMISSION_DENIED) {
                // 권한동의 거부 했으면 여기로 들어옴
                // 자기위치 안뜨고 서울 가운데로 띄어줌
                // 그거이외에는 다른건 없음
                CafeMarkerAdd();
                LatLng mylocation = new LatLng(37.5642135, 127.0016985);

                mMap.addMarker(new MarkerOptions()
                        .position(mylocation)
                        .title("기본 위치")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        ));

                // 마커 클릭이벤트 추가한다는거 마커는 지도에 있는 빨강색 동그란거
                mMap.setOnMarkerClickListener(this);
                // 마커 누르면 info라고 작은 창 뜨는데 그거 클릭이벤트 추가한다는거
                mMap.setOnInfoWindowClickListener(this);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 16);
                //mMap.animateCamera(cameraUpdate);
                mMap.moveCamera(cameraUpdate);
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }

        } else {
            // 데이터 실시간으로 받아오기

            // 자기 위치 권한 동의를 했을 때
            Location GPSlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location NETWORKlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // getLastKnownLocation은 가장 최근 위치에 있을 때의 GPS좌표를 주는거다  여기서 가장 최근 위치가 없거나 못받아 올때 NULL이 떠서 앱이 튕긴다.
            // 실내일때는 GPS_PROVIDER가 못받아와서 네트워크 정보를 가져와서 하는 방식으로 하자.

            //GPS PROVIDER 이랑 NETWORK 둘중에 정확도 더 낮은거로 쓰자 예를 들어서 41.0이 나오면 위도/경도로 부터 41m안에 있는거래
            // 그러면 숫자가 더 작을 수 록 좋으거임
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

            if (GPSlocation == null && NETWORKlocation == null) { // 둘다 정보가 안뜨면 x된거다
                Toast.makeText(getContext(), "위치 정보를 불러올 수 없습니다.", Toast.LENGTH_LONG).show();
            } else if ((GPSlocation == null) || (test1 > test2)) {
                // 정확도 비교해봐서 NETWORK가 정확하거나 GPSlocation이 없으면 NETWORK 씀
                double latitude = NETWORKlocation.getLatitude();
                double longitude = NETWORKlocation.getLongitude();
                LatLng mylocation = new LatLng(latitude, longitude); // 현재 위치 받는것

                mMap.addMarker(new MarkerOptions()
                        .position(mylocation)
                        .title("현재위치")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        ));
                // 여기까지 >>

                // << 카페 추가한거 이건 함수로 구현해야되는데 데이터베이스에서 올라오는거 보고 구현할게 기본적으로 2개 추가해줬음
                CafeMarkerAdd();

                // 마커 클릭이벤트 추가한다는거 마커는 지도에 있는 빨강색 동그란거
                mMap.setOnMarkerClickListener(this);
                // 마커 누르면 info라고 작은 창 뜨는데 그거 클릭이벤트 추가한다는거
                mMap.setOnInfoWindowClickListener(this);

                // 현재 자기위치로 화면 이동하는거
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 16);
                //mMap.animateCamera(cameraUpdate);
                mMap.moveCamera(cameraUpdate);
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

            } else { // GPS 안비어 있으면 이거 써라
                LatLng mylocation = new LatLng(GPSlocation.getLatitude(), GPSlocation.getLongitude()); // 현재 위치 받는것

                mMap.addMarker(new MarkerOptions()
                        .position(mylocation)
                        .title("현재위치")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        ));
                // 여기까지 >>

                // << 카페 추가한거 이건 함수로 구현해야되는데 데이터베이스에서 올라오는거 보고 구현할게 기본적으로 2개 추가해줬음
                CafeMarkerAdd();

                // 마커 클릭이벤트 추가한다는거 마커는 지도에 있는 빨강색 동그란거
                mMap.setOnMarkerClickListener(this);
                // 마커 누르면 info라고 작은 창 뜨는데 그거 클릭이벤트 추가한다는거
                mMap.setOnInfoWindowClickListener(this);

                // 현재 자기위치로 화면 이동하는거
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 16);
                //mMap.animateCamera(cameraUpdate);
                mMap.moveCamera(cameraUpdate);
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }

        }
    }

    final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
        }

    };

    public void CafeMarkerAdd() {
        //2020-08-26 테스팅 위해 삭제
        /*
        LatLng coffeeshop = new LatLng(listCafeData.get(0).getCafeLatitude(), listCafeData.get(0).getCafeLongitude());
        mMap.addMarker(new MarkerOptions().position(coffeeshop).title(listCafeData.get(0).getCafeName()));
        LatLng coffeeshop2 = new LatLng(listCafeData.get(1).getCafeLatitude(), listCafeData.get(1).getCafeLongitude());
        mMap.addMarker(new MarkerOptions().position(coffeeshop2).title(listCafeData.get(1).getCafeName()));
         */
        List<LatLng> coffeeshop = new ArrayList<>();
        for(int i=0; i<listCafeData.size(); i++){
            coffeeshop.add(new LatLng(listCafeData.get(i).getCafeLatitude(),listCafeData.get(i).getCafeLongitude()));
            mMap.addMarker(new MarkerOptions().position(coffeeshop.get(i)).title(listCafeData.get(i).getCafeName()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 맵에서 버튼 누르면 자기위치로 이동
            case R.id.MyLocation:
                int permssionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                // 조건문(위치제공허가)은 자기위치 권한 동의 안하면 Toast 메세지 출력
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "위치 권한 승인 필요", Toast.LENGTH_LONG).show();
                } else {
                    // 자기 위치 찾아서
                    Location GPSlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location NETWORKlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    double longitude = 0;
                    double latitude = 0;

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

                    if (GPSlocation == null && NETWORKlocation == null) { // 둘다 정보가 안뜨면 좇된거다
                        Toast.makeText(getContext(), "좇됬다.", Toast.LENGTH_LONG).show();
                    } else if ((GPSlocation == null) || (test1 > test2)) {
                        longitude = NETWORKlocation.getLongitude();
                        latitude = NETWORKlocation.getLatitude();
                    } else {
                        longitude = GPSlocation.getLongitude();
                        latitude = GPSlocation.getLatitude();
                    }
                    //좌표대로 넣어줌

                    LatLng mylocation = new LatLng(latitude, longitude); // 현재 위치 받는것

                    mMap.addMarker(new MarkerOptions()
                            .position(mylocation)
                            .title("현재위치")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                            ));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                    break;

                }
        }
    }


    // 마커 클릭 이벤트임
    @Override
    public boolean onMarkerClick(Marker marker) {
        // 마커 클릭 이벤트가 2가지로 나눔
        // 1. marker누르면 info 뜨고 info를 클릭해야지 카페 정보로 이동하는 방법
        // 2, markersnfmaus info 엑티비티 호출하고 버튼 누르면 카페 정보로 이동하는 방법 (인호가 말한 방법) (지금은 이 방법)
        for (int i = 0; i < listCafeData.size(); i++) {
            if (marker.getTitle().equals(listCafeData.get(i).getCafeName())) {
                /* 1번 방법 주석처리
                info로 들어가는 방법
                View infoWindow = getLayoutInflater().inflate(R.layout.item_mapinfo, null);
                InfoAdapter Adapter1 = new InfoAdapter(infoWindow, listCafeData.get(i));
                mMap.setInfoWindowAdapter(Adapter1);
                */
                //float latitude = listCafeData.get(i).getCafeLatitude();
                //float longitude = listCafeData.get(i).getCafeLongitude();

                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                // 2번 방법으로 한것
                Intent intent = new Intent(getContext(), Activity_MapInfo.class);
                //HSJ 2020-08-22 cno 쏘기 추가
                intent.putExtra("CafePid",listCafeData.get(i).getCafePid());
                //HSJ 2020-08-22

                intent.putExtra("CafeName", listCafeData.get(i).getCafeName());
                intent.putExtra("CafeTime", listCafeData.get(i).getCafeStart());
                intent.putExtra("CafeStart", listCafeData.get(i).getCafeStart());
                intent.putExtra("CafeEnd", listCafeData.get(i).getCafeEnd());
                intent.putExtra("CafeGrade", listCafeData.get(i).getCafeGrade());

                // 전화번호 / 설명 / 사진 같은건 데이터베이스에서 받아서 보내야됨
                intent.putExtra("CafePhone", listCafeData.get(i).getCafePhone());
                intent.putExtra("CafeInfo", listCafeData.get(i).getCafeIntro());
                intent.putExtra("CafeEvent", "바나나");
                //2020-08-26 HSJ 카페 사진 url 보내주기
                intent.putExtra("CafePicture",listCafeData.get(i).getCafePicture());
                //2020-08-26 HSJ
                startActivity(intent);
                break;
            }
        }
        // 현재 위치 마커누르면 info도 안뜨게 return false로 해줌
        if (marker.getTitle().equals("현재위치")) {
            return true;
        }
        return false;
    }

    // info 클릭 이벤트임 1번 방법 아닌 이상 안씀
    @Override
    public void onInfoWindowClick(Marker marker) {

        for (int i = 0; i < listCafeData.size(); i++) {
            if (marker.getTitle().equals(listCafeData.get(i).getCafeName())) {
                Intent intent = new Intent(getActivity(), Activity_CafeInfo.class);
                intent.putExtra("CafeName", listCafeData.get(i).getCafeName());
                intent.putExtra("CafeTime", listCafeData.get(i).getCafeStart());
                intent.putExtra("CafeGrade", listCafeData.get(i).getCafeGrade());

                // 전화번호 / 설명 / 사진 같은건 데이터베이스에서 받아서 보내야됨
                intent.putExtra("CafePhone", "010000001");
                intent.putExtra("CafeInfo", "사장은 달고 카페는 맛있어요");
                intent.putExtra("CafeEvent", "바나나");
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grandResults.length > 0
                        && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(mMap);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }


}
