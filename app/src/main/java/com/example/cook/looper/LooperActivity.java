package com.example.cook.looper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LooperActivity extends AppCompatActivity implements MyViewPager.OnTouchListener, ViewPager.OnPageChangeListener {

    private LooperAdapter looperAdapter;
private static List<Integer>spic=new ArrayList<>();
    private MyViewPager viewPager;
static {

    spic.add(R.drawable.ic_food_show);
    spic.add(R.drawable.ic_iconfont_voice);
    spic.add(R.drawable.ic_hear);


}

    private Handler handler;
private boolean misTouch=false;
    private ConstraintLayout constraintLayout;
    private LinearLayout linearLayout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        initview();
        dataa();
        handler = new Handler();

    }

    private void dataa() {

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(looprunnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(looprunnable);
    }
    private Runnable looprunnable =new Runnable() {
        @Override
        public void run() {
            if (!misTouch){
                int currentiteem=      viewPager.getCurrentItem();
                viewPager.setCurrentItem(++currentiteem,false);

            }

            handler.postDelayed(this,3000);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initview() {
        viewPager = this.findViewById(R.id.viewp);

        looperAdapter = new LooperAdapter();
        looperAdapter.setdata(spic);
        viewPager.setAdapter(looperAdapter);
        //xuandin
        viewPager.addOnPageChangeListener(this);

        viewPager.setListener(this);
        //加点R
        linearLayout = findViewById(R.id.looper_con);
        initpoint();
        viewPager.setCurrentItem(looperAdapter.getDataSize() * 100,false);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initpoint() {
        for (int i=0;i<spic.size();i++){
            View point=new View(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(40,40);

                point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));

//            point.setBackground();
            layoutParams.leftMargin=20;
            point.setLayoutParams(layoutParams);
            linearLayout.addView(point);
        }
    }

    @Override
    public void onPageTouch(boolean isTouch) {
        misTouch=isTouch;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//停下来选中位值
        int positionr;
        if (looperAdapter.getDataSize()!=0){
            positionr=position %looperAdapter.getDataSize();

        }else {

            positionr=0;
        }
        selectpoint(positionr);
    }

    private void selectpoint(int positionr) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
         View point=   linearLayout.getChildAt(i);
            if (i!=positionr) {
                point.setBackgroundResource(R.drawable.shape_point_normal);
            }else {
                point.setBackgroundResource(R.drawable.shape_point_select);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
