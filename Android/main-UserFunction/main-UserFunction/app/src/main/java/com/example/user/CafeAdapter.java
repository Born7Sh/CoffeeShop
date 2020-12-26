package com.example.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 카페 어뎁터임 recycle 뷰 쓰려고 만든거
// 카페 항목 클릭 이벤트 구현할거면 onBindViewHolder에서 구현하면됨
public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ItemViewHolder> {
    private ArrayList<CafeData> listCafeData = new ArrayList<>();
    private LayoutInflater mInflate;
    private Context mContext;
    private MainActivity mainActivity;

    public CafeAdapter(Context context, ArrayList<CafeData> listCafeData) {
        this.mContext = context;
        this.mainActivity = (MainActivity) context;
        this.mInflate = LayoutInflater.from(context);
        this.listCafeData = listCafeData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_cafe, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        // EventAdaper와 다른것은 holder final로 바꿨다.
        //listData : 현재 recyclerView로 가지고 있는 데이터 그릇을 의미
        //position : 현재 recyclerView의 position
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listCafeData.get(position));

        holder.CafeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Activity_CafeInfo.class);
                //HSJ 2020-08-22 추가
                intent.putExtra("CafePid",listCafeData.get(position).getCafePid());
                //HSJ 2020-08-22
                intent.putExtra("CafeName", String.valueOf(holder.textView1.getText()));
                intent.putExtra("CafeTime", String.valueOf(holder.textView2.getText()));
                intent.putExtra("CafeGrade", String.valueOf(holder.textView3.getText()));

                // 전화번호 / 설명 / 사진 같은건 데이터베이스에서 받아서 보내야됨
                intent.putExtra("CafePhone", listCafeData.get(position).getCafePhone());
                intent.putExtra("CafeInfo", listCafeData.get(position).getCafeIntro());
                intent.putExtra("CafeEvent", "쵸콜렛 바나나");
                intent.putExtra("CafeData",listCafeData.get(position));
                //2020-08-26 HSJ 카페사진 url 추가
                //intent.putExtra("CafePicture",listCafeData.get(position).getCafePicture());


                mContext.startActivity(intent);
            }
        });

        holder.Seemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment(Double.toString(listCafeData.get(position).getCafeLatitude()), Double.toString(listCafeData.get(position).getCafeLongitude()));
            }
        });
    }

    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listCafeData.size();
    }


    void addItem(CafeData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listCafeData.add(data);
    }
    public void clearItem(){
        this.listCafeData.clear();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout CafeItem;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView Seemap;
        private ImageView imageView4;


        ItemViewHolder(View itemView) {
            super(itemView);
            CafeItem = (LinearLayout) itemView.findViewById(R.id.CafeItem);
            Seemap = (TextView) itemView.findViewById(R.id.SeeMap);
            textView1 = (TextView) itemView.findViewById(R.id.CafeName);
            textView2 = (TextView) itemView.findViewById(R.id.CafeTime);
            textView3 = (TextView) itemView.findViewById(R.id.CafeGrade);
            imageView4 = (ImageView) itemView.findViewById(R.id.CafePicture);

        }

        void onBind(CafeData data) {
            textView1.setText(data.getCafeName());
            textView2.setText(data.getCafeStart() + " - " + data.getCafeEnd());
            textView3.setText(data.getCafeGrade());
            //imageView4.setImageResource(data.getCafePid());


            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.125.237.247:8000").addConverterFactory(GsonConverterFactory.create()).build();

            RetroService retroservice = retrofit.create(RetroService.class);

            Call<List<CafeImage>> call4;
            call4 = retroservice.getCafeImages(data.getCafePid());

            call4.enqueue(new Callback<List<CafeImage>>(){
                @Override
                public void onResponse(Call<List<CafeImage>> call4, Response<List<CafeImage>> response4){
                    if(response4.isSuccessful()){
                        List<CafeImage> res = response4.body();
                        imageView4.setImageBitmap(getImFromURL(res.get(0).img1));
                    }
                }
                @Override
                public void onFailure(Call<List<CafeImage>> call4, Throwable t3){
                    Log.v("카페이미지불러오기","오류");
                }

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
            });
        }
    }
    public void clear(){
        this.listCafeData.clear();
    }
}
