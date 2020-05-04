package com.example.cook.looper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;



public class MyViewPager extends ViewPager {
    private OnTouchListener mTouchListener=null;
    private Handler handler;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler(Looper.getMainLooper());
  setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
          int a=event.getAction();
          switch (a) {
                 case MotionEvent.ACTION_DOWN:
stopl();

                break;
                case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                      luop();
                        break;
          }
          return false;
      }
  });

    }

private void luop(){

        handler.postDelayed(mtask,3000);

}

private Runnable mtask=new Runnable() {
    @Override
    public void run() {
        int cit=getCurrentItem();
        cit++;
        setCurrentItem(cit);
        postDelayed(this,3000);
    }
};
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        luop();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopl();
    }

    private void stopl() {
        removeCallbacks(mtask);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {

//        switch(ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//
//                if (mTouchListener!=null){
//                    mTouchListener.onPageTouch(true);
//                }
//                break;
//                case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        if (mTouchListener!=null){
//                            mTouchListener.onPageTouch(false);
//                        }
//                        break;
//
//
//        }
//
//        return super.onTouchEvent(ev);
//    }


    public void setListener(OnTouchListener listener){
        this.mTouchListener=listener;

    }

//    public interface OnTouchListener{
//        void onPageTouch(boolean isTouch);
//    }
}
