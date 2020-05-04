//package com.example.cook.save;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import com.example.cook.R;
//import com.example.cook.adapter.CookAdapter;
//import com.example.cook.entity.show.ShowCarEntity;
//import com.example.cook.looper.LooperAdapter;
//import com.example.cook.looper.MyViewPager;
//import com.example.cook.respository.CookViewModal;
//import com.example.cook.ui.home.BreakfastActivity;
//import com.example.cook.ui.home.DinnerActivity;
//import com.example.cook.ui.home.HomeViewModel;
//import com.example.cook.ui.home.LunchActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeFragment extends Fragment   {
//    private static final String TAG = "HomeFragment";
//    private ImageView imageViewVBreakFast, imageViewLaunch, imageViewDinner;
//    private CookViewModal cookViewModal;
//    private LiveData<List<ShowCarEntity>> alllist;
//    private RecyclerView recyclerView;
//    private CookAdapter cookAdapter;
//
//    //广告..........................
//
//    private LooperAdapter looperAdapter;
//    private static List<Integer>spic=new ArrayList<>();
//    private MyViewPager viewPager;
//    static {
//
//        spic.add(R.drawable.ic_food_show);
//        spic.add(R.drawable.ic_iconfont_voice);
//        spic.add(R.drawable.ic_hear);
//
//
//    }
//
//    private Handler handler;
//    private boolean misTouch=false;
//    private ConstraintLayout constraintLayout;
//    private LinearLayout linearLayout;
//
//
//
//
//
//    public HomeFragment() {
//
//
//        setHasOptionsMenu(true);
//    }
//
//    private HomeViewModel homeViewModel;
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        cookViewModal = ViewModelProviders.of(requireActivity()).get(CookViewModal.class);
//
//
//        recyclerView = requireActivity().findViewById(R.id.recycle_food_home);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setReverseLayout(true);
//        recyclerView.setLayoutManager(layoutManager);
////        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//
//        cookAdapter = new CookAdapter();
//
//        //广告
//        looper();
//        initview();
//
//        handler = new Handler();
//
//        initView();
//        initCilck();
////搜索
//        //瀑布流
//        purecyccler();
//
//        adapter();
//        //滑动删除
//        deletescroll();
//    }
//
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void initview() {
//        viewPager = requireActivity().findViewById(R.id.viewp);
//
//        looperAdapter = new LooperAdapter();
//        looperAdapter.setdata(spic);
//        viewPager.setAdapter(looperAdapter);
//        //xuandin
////        viewPager.addOnPageChangeListener(this);
////
////        viewPager.setListener(this);
//        //加点R
//        linearLayout =requireActivity().findViewById(R.id.looper_con);
//        initpoint();
//        viewPager.setCurrentItem(looperAdapter.getDataSize() * 100,false);
//
//
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void initpoint() {
//
//        for (int i=0;i<spic.size();i++){
//            View point=new View(requireActivity());
//            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(40,40);
//
//            point.setBackground(getResources().getDrawable(R.drawable.shape_point_normal));
//
////            point.setBackground();
//            layoutParams.leftMargin=20;
//            point.setLayoutParams(layoutParams);
//            linearLayout.addView(point);
//        }
//
//
//    }
//
//
//    private void looper() {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        other();
//
//    }
//
//
//    private void other() {
//
//
//    }
//
//
//
//
//    //...........looper..............................
//
//    private void purecyccler() {
//
//        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//layoutManager.setReverseLayout(true);
//
//    }
//
//
//    //...............................................搜索..............
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search_menu, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.home_app_bar_search).getActionView();
//        searchView.setMaxWidth(500);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String pattern = newText.trim();
//                alllist.removeObservers(requireActivity());
//                alllist = cookViewModal.findAllCooksLive(pattern);
//                alllist.observe(requireActivity(), new Observer<List<ShowCarEntity>>() {
//                    @Override
//                    public void onChanged(List<ShowCarEntity> msg) {
//                        int temp = cookAdapter.getItemCount();
//                        cookAdapter.setAllCooks(msg);
//                        if (temp != msg.size()) {
//
//                            cookAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//                return true;
//            }
//        });
//    }
//
//
//    private void deletescroll() {
//
//
//
//
//    }
//
//    private void adapter() {
//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
//        alllist = cookViewModal.getAllCooksLive();
//
//        alllist.observe(requireActivity(), new Observer<List<ShowCarEntity>>() {
//            @Override
//            public void onChanged(List<ShowCarEntity> showCarEntities) {
//
//
//                cookAdapter.setAllCooks(showCarEntities);
//                cookAdapter.notifyDataSetChanged();
//            }
//        });
//        recyclerView.setAdapter(cookAdapter);
//    }
//
//    private void initCilck() {
//        imageViewVBreakFast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireContext(), BreakfastActivity.class);
//                startActivity(intent);
//            }
//        });
//        imageViewLaunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireContext(), LunchActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        imageViewDinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(requireContext(), DinnerActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }
//
//    private void initView() {
//        imageViewVBreakFast = requireActivity().findViewById(R.id.imageView_breakfst);
//        imageViewLaunch = requireActivity().findViewById(R.id.imageView_food_menu_lunch);
//        imageViewDinner = requireActivity().findViewById(R.id.imageView_hone_dinner);
//
//    }
////广告
////    @Override
////    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////
////    }
////
////    @Override
////    public void onPageSelected(int position) {
//////停下来选中位值
////        int positionr;
////        if (looperAdapter.getDataSize()!=0){
////            positionr=position %looperAdapter.getDataSize();
////
////        }else {
////
////            positionr=0;
////        }
////        selectpoint(positionr);
////    }
////
////    private void selectpoint(int positionr) {
////
////        for (int i = 0; i < linearLayout.getChildCount(); i++) {
////            View point=   linearLayout.getChildAt(i);
////            if (i!=positionr) {
////                point.setBackgroundResource(R.drawable.shape_point_normal);
////            }else {
////                point.setBackgroundResource(R.drawable.shape_point_select);
////            }
////        }
////    }
////
////    @Override
////    public void onPageScrollStateChanged(int state) {
////
////    }
////
////    @Override
////    public void onPageTouch(boolean isTouch) {
////misTouch=isTouch;
////    }
//
////按钮
//
//
//}
