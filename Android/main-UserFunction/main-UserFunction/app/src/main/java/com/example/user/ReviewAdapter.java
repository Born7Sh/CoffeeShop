package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder>{
    private ArrayList<ReviewData> listReviewData = new ArrayList<>();
    Context context;
    private int limit;


    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public ReviewAdapter(Context context, int limit) {
        this.context = context;
        this.limit = limit;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        // EventAdaper와 다른것은 holder final로 바꿨다.
        //listData : 현재 recyclerView로 가지고 있는 데이터 그릇을 의미
        //position : 현재 recyclerView의 position
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listReviewData.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_ImageBigSize.class);
                Bitmap a = listReviewData.get(position).getReviewPicture();
                intent.putExtra("image",listReviewData.get(position).getReviewPicture());
                context.startActivity(intent);
            }
        });
    }
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.

        if(limit == 3){
            return limit;
        }
        return listReviewData.size();
    }


    void addItem(ReviewData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listReviewData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private TextView textView6;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.ReviewId);
            textView2 = (TextView) itemView.findViewById(R.id.ReviewAge);
            textView3 = (TextView) itemView.findViewById(R.id.ReviewGender);
            textView4 = (TextView) itemView.findViewById(R.id.ReviewDate);
            textView5 = (TextView) itemView.findViewById(R.id.Review);
            textView6 = (TextView) itemView.findViewById(R.id.ReviewGrade);
            imageView = (ImageView) itemView.findViewById(R.id.ReviewPicture);
        }

        void onBind(ReviewData data) {
            textView1.setText(data.getReviewId() + "님");
            textView2.setText("(" + data.getReviewAge() + "대");
            textView3.setText(" " + data.getReviewGender() + ")");
            textView4.setText(data.getReviewDate());
            //날짜 받아오는거는 데이터베이스에 어떻게 저장되는지 생각해서 나중에 설정할게.

            textView5.setText(data.getReview());
            textView6.setText(data.getReviewGrade());
            imageView.setImageBitmap(data.getReviewPicture());
        }
    }
}
