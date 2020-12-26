package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.ItemViewHolder> {
    private ArrayList<AnnounceData> listAnnounceData = new ArrayList<>();
    private LayoutInflater mInflate;
    private Context mContext;

    public AnnounceAdapter(Context context, ArrayList<AnnounceData> listAnnounceData) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.listAnnounceData = listAnnounceData;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_announce, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        // EventAdaper와 다른것은 holder final로 바꿨다.
        //listData : 현재 recyclerView로 가지고 있는 데이터 그릇을 의미
        //position : 현재 recyclerView의 position
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listAnnounceData.get(position));

        holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Activity_Announce_Confirmatiom.class);

                intent.putExtra("AnnounceTitle", String.valueOf(holder.textView1.getText()));
                intent.putExtra("AnnounceDate", String.valueOf(holder.textView2.getText()));
                intent.putExtra("Announce", String.valueOf(listAnnounceData.get(position).getAnnounce()));

                mContext.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listAnnounceData.size();
    }


    void addItem(AnnounceData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listAnnounceData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout1;
        private TextView textView1;
        private TextView textView2;

        ItemViewHolder(View itemView) {
            super(itemView);
            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.AnnounceLayout);
            textView1 = (TextView) itemView.findViewById(R.id.AnnounceTitle);
            textView2 = (TextView) itemView.findViewById(R.id.AnnounceDate);

        }

        void onBind(AnnounceData data) {
            textView1.setText(data.getAnnounceTitle());
            textView2.setText(data.getAnnounceDate());
            // imageView4.setImageResource(data.getCafePid());
        }
    }
}

