package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);

        final EditText LoginId = (EditText) findViewById(R.id.LoginId); // ID 입력
        final EditText LoginPw = (EditText) findViewById(R.id.LoginPw); // PW 입력

        Button btnLogin = (Button) findViewById(R.id.btnLogin); // 로그인 버튼
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp); // 회원가입 버튼

        TextView Find = (TextView) findViewById(R.id.Find); // 찾기 눌렀을 때

        Find.setPaintFlags(Find.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 찾기 텍스트 밑줄 긋기

        // 밑에는 눌렀을때의 리스너
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);

                Call<Login> call = retroservice.requestLogin(String.valueOf(LoginId.getText()),String.valueOf(LoginPw.getText()));

                call.enqueue(new Callback<Login>(){
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response){
                        if(response.isSuccessful()){
                            Login login = response.body();
                            if(login.code.equals("0000")){
                                Log.v("로그인 알림","성공");
                                User user = (User)getApplication();
                                int uno = Integer.parseInt(login.msg.substring(0,login.msg.indexOf(",")));
                                user.setUno(uno);
                                String uid = login.msg.substring(login.msg.indexOf(",")+1,login.msg.length());
                                user.setUid(uid);

                                Log.v("uid",uid);
                                Log.v("uno",String.valueOf(uno));
                                user.setIsLogin();
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t){

                    }
                });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User)getApplication();
                user.setUno(1);
                user.setNotLogin();
                user.setUid("게스트");
                finish();
            }
        });

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
