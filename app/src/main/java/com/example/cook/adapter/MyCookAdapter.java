package com.example.cook.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook.R;
import com.example.cook.entity.CollectCenterEntity;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.ui.home.ShowMenuCookActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class MyCookAdapter extends RecyclerView.Adapter<MyCookAdapter.MyViewHolder> {
    List<MyCookEntity> allCollect = new ArrayList<>();

    public void setAllCollect(List<MyCookEntity> allCollect) {
        this.allCollect.clear();
        this.allCollect = allCollect;
notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.ceel_collect_car, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allCook = holder.textViewTitle.getText().toString();
                Intent intent = new Intent(v.getContext(), ShowMenuCookActivity.class);
                intent.putExtra("Title", allCook);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCookAdapter.MyViewHolder holder, int position) {

        MyCookEntity myCookEntity = allCollect.get(position);
        holder.author.setText(myCookEntity.getAuthorName());
        holder.textViewTitle.setText(myCookEntity.getTitle());
        Picasso.get().load("http://172.20.10.3:8080/"+myCookEntity.getImageIcon()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return allCollect.size();
    }

    public void SetData(MyCookEntity myCookEntity) {
        allCollect.clear();
        allCollect.add(myCookEntity);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewTitle, author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_collect_icon);
            textViewTitle = itemView.findViewById(R.id.text_collect_title1);
            author = itemView.findViewById(R.id.text_collect_author);
        }
    }
}
