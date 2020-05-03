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
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.ui.home.ShowMenuCookActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CookAdapter extends RecyclerView.Adapter<CookAdapter.MyViewHolder> {
    private static final String TAG = "CookAdapter";
    List<ShowCarEntity> allCooks = new ArrayList<>();

    public void setAllCooks(List<ShowCarEntity> allCooks) {


this.allCooks.clear();
        this.allCooks = allCooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.show_food_card2, parent, false);
//卡片点击事件
        final MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String allCook = holder.textViewTitle.getText().toString();
                Intent intent = new Intent(view.getContext(), ShowMenuCookActivity.class);
                intent.putExtra("Title", allCook);

                holder.itemView.getContext().startActivity(intent);
            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShowCarEntity showCarEntity = allCooks.get(position);
        holder.textViewTitle.setText(showCarEntity.getTitle());
        holder.textViewusername.setText(showCarEntity.getUsername());
        Picasso.get().load("http://172.20.10.3:8080/"+showCarEntity.getEndImage()).into(holder.imageViewCookEnd);

    }

    @Override
    public int getItemCount() {
        return allCooks.size();
    }

    public void setData(ShowCarEntity showCarEntity) {
        allCooks.clear();
        allCooks.add(showCarEntity);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle, textViewusername, textViewNumber;
        private ImageView imageViewCookEnd, imageViewuserIcon, imageViewHeat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_show_title);
            textViewusername = itemView.findViewById(R.id.text_show_username);
            textViewNumber = itemView.findViewById(R.id.text_heart_number);
            imageViewCookEnd = itemView.findViewById(R.id.imageView2);
            imageViewuserIcon = itemView.findViewById(R.id.image_user_icon);
            imageViewHeat = itemView.findViewById(R.id.image_car_heart);


        }
    }
}
