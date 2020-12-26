package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CafeImageAdapter extends RecyclerView.Adapter<CafeImageAdapter.ViewHolder> {

    private ArrayList<String> cafeImageList;
    private Context context;
    private View.OnClickListener onClickItem;
    /*
    public CafeImageAdapter(Context context, ArrayList<Bitmap> cafeImageList, View.OnClickListener onClickItem) {
        this.context = context;
        this.cafeImageList = cafeImageList;
        this.onClickItem = onClickItem;
    }
    */
    public CafeImageAdapter(Context context, ArrayList<String> cafeImageList) {
        this.context = context;
        this.cafeImageList = cafeImageList;
        // 이미지 아이템을 클릭했을 때 나오는 부분
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cafeimage, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String a = cafeImageList.get(position);
        Bitmap item = getImFromURL(a);
        holder.imageView.setImageBitmap(item);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tag 붙여온거 받아서 전체화면으로 틀어주는 식의 함수임
                Intent intent = new Intent(context, Activity_ImageBigSize.class);
                intent.putExtra("Image",a);
                context.startActivity(intent);
            }
        });
        //holder.imageView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return cafeImageList.size();
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


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.CafeImage);
        }
    }
}
