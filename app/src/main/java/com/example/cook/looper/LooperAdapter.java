package com.example.cook.looper;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class LooperAdapter extends PagerAdapter {
    private List<Integer>mcolor=null;
    @Override
    public int getCount() {

        if (mcolor!=null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realpath=position % mcolor.size();
        ImageView imageView=new ImageView(container.getContext());
        imageView.setImageResource(mcolor.get(realpath));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setdata(List<Integer> scolor) {
        this.mcolor=scolor;
    }

    public int getDataSize(){

        if (mcolor!=null){
            return mcolor.size();
        }
        return 0;
    }
}
