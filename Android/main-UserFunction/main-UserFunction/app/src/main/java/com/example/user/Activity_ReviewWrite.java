package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ReviewWrite extends AppCompatActivity {
    TextView ratingNum;

    private Boolean isPermission = true;

    private static final int REQUEST_CODE = 0;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;
    private File tempFile;

    ImageView imageView;
    ImageView cancel;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        TextView reviewCafeName = (TextView) findViewById(R.id.ReviewCafeName);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.Rating);
        ratingNum = (TextView) findViewById(R.id.RatingNum);

        final EditText review = (EditText) findViewById(R.id.Review);
        Button Add = (Button) findViewById(R.id.pickAdd);

        Button ReviewCancel = (Button) findViewById(R.id.ReviewCancel);
        Button ReviewAdd = (Button) findViewById(R.id.ReviewAdd);

        ratingBar.setOnRatingBarChangeListener(new Listener());

        imageView = findViewById(R.id.ReviewImage);
        cancel = findViewById(R.id.ImageRefresh);

        tedPermission();
        Intent intent = getIntent();
        final int cafePid = intent.getExtras().getInt("CafePid");
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermission) goToAlbum();
                else {
                    Toast.makeText(getApplicationContext(), "사진  및 파일을 저장하기 위하여 접근 권한이 필요합니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.mipmap.squareadd);
                img = null;
                cancel.setVisibility(View.GONE);
            }
        });
        //2020-09-11 JHM (현재 글자수 파악해서 setText 하는것 추가)
        final TextView currnetNumber = (TextView) findViewById(R.id.currentNum);
        review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = review.getText().toString();
                currnetNumber.setText(input.length()+" / 100 글자 수");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 99) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ReviewWrite.this);
                    builder.setMessage("입력은 100자리까지 입니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

        ReviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

                RetroService retroservice = retrofit.create(RetroService.class);
                if (tempFile != null) {
                    File file = new File(tempFile.getAbsolutePath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part picture = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
                    User user = (User) getApplication();

                    //RequestBody picture = RequestBody.create(MediaType.parse("image/*"),file);
                    int uid = user.getUno();

                    Call<ResponseBody> call = retroservice.provideReviewP("json", user.getUno(), String.valueOf(review.getText()), Integer.parseInt(String.valueOf(ratingNum.getText())),picture, cafePid);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.v("알림", "성공");
                            }
                            Log.v("실패알림", response.message());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Log.v("알림", "실패");
                        }
                    });

                }
                else{
                    User user = (User)getApplication();
                    Call<ResponseBody> call = retroservice.provideReviewNP("json", user.getUno(), String.valueOf(review.getText()),Integer.parseInt(String.valueOf(ratingNum.getText())),cafePid);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.v("알림", "성공");
                            }
                            Log.v("실패알림", response.message());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Log.v("알림", "실패");
                        }
                    });
                }
            }
        });
    }


    class Listener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            ratingNum.setText(String.valueOf((int) rating));
        }
    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setDeniedMessage("사진 및 파일을 첨부하기 위하여 접근 권한이 필요합니다")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void goToAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                    cancel.setVisibility(View.VISIBLE);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);
        tempFile = null;
    }

}
