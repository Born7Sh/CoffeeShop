package com.example.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CafeImageAdapter extends RecyclerView.Adapter<CafeImageAdapter.ViewHolder> {

    private ArrayList<Bitmap> cafeImageList;
    private Context context;
    private View.OnClickListener onClickItem;

    public CafeImageAdapter(Context context, ArrayList<Bitmap> cafeImageList, View.OnClickListener onClickItem) {
        this.context = context;
        this.cafeImageList = cafeImageList;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cafeimage, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap item = cafeImageList.get(position);

        holder.imageView.setImageBitmap(item);
        //holder.imageView.setTag(item);
        holder.imageView.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return cafeImageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.CafeImage);
        }
    }
}
