package com.example.cook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cook.R;
import com.example.cook.ui.ImagEntity;
import com.example.cook.ui.ImgaActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Myviewholder> {
    private List<ImagEntity>list=new ArrayList<>();
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cellimg,parent,false);

        return new Myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        ImagEntity imagEntity=list.get(position);
        ImageView imageView=holder.itemView.findViewById(R.id.imageView100);
        TextView textView=holder.itemView.findViewById(R.id.textView101);
        textView.setText(imagEntity.getTitle());

      Glide.with(imageView.getContext()).load(imagEntity.getPath()).into(imageView);
//  Picasso.get().load(imagEntity.getPath()).into(imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setdata(List<ImagEntity> imagEntityList) {
        list.clear();
        list.addAll(imagEntityList);
        notifyDataSetChanged();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
