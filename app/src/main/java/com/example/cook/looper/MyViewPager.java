package com.example.cook.looper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private OnTouchListener mTouchListener=null;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                if (mTouchListener!=null){
                    mTouchListener.onPageTouch(true);
                }
                break;
                case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (mTouchListener!=null){
                            mTouchListener.onPageTouch(false);
                        }
                        break;


        }

        return super.onTouchEvent(ev);
    }


    public void setListener(OnTouchListener listener){
        this.mTouchListener=listener;

    }

    public interface OnTouchListener{
        void onPageTouch(boolean isTouch);
    }
}
