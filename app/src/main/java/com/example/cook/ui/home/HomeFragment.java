package com.example.cook.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.PrimaryKey;
import androidx.viewpager.widget.ViewPager;

import com.example.cook.R;
import com.example.cook.adapter.CookAdapter;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.looper.LooperAdapter;
import com.example.cook.looper.MyViewPager;
import com.example.cook.respository.CookViewModal;
import com.example.cook.views.PagerItem;
import com.example.cook.views.SobLooperPager;
import com.example.cook.views.SuperMainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment   {
    private static final String TAG = "HomeFragment";
    private ImageView imageViewVBreakFast, imageViewLaunch, imageViewDinner;
    private CookViewModal cookViewModal;
    private LiveData<List<ShowCarEntity>> alllist;
    private RecyclerView recyclerView;
    private CookAdapter cookAdapter;

    //广告..........................

    private SobLooperPager mLooperPager;

    private List<PagerItem> mData = new ArrayList<>();





    public HomeFragment() {


        setHasOptionsMenu(true);
    }

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cookViewModal = ViewModelProviders.of(requireActivity()).get(CookViewModal.class);


        recyclerView = requireActivity().findViewById(R.id.recycle_food_home);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        cookAdapter=new CookAdapter();

        //广告
        looper();

        initData();
        initViewlop();
        initEvent();
        

        initView();
        initCilck();
//搜索
        //瀑布流
        purecyccler();

        adapter();
        //滑动删除
        deletescroll();
    }

    private void initEvent() {if(mLooperPager != null) {
        mLooperPager.setOnItemClickListener(new SobLooperPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),"点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                //todo:根据交互业务去实现具体逻辑
            }
        });
    }

    }

    private void initViewlop() {
    }

    private void initData() {
        mData.add(new PagerItem("第一个图片",R.mipmap.pic0));
        mData.add(new PagerItem("第2个图片",R.mipmap.pic1));
        mData.add(new PagerItem("第三个图片",R.mipmap.pic2));
        mData.add(new PagerItem("第4个图片",R.mipmap.pic3));
    }


    private void looper() {

        mLooperPager = requireActivity().findViewById(R.id.sob_looper_pager);
        mLooperPager.setData(mInnerAdapter,new SobLooperPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });


















        other();

    }


    private void other() {


    }

    private SobLooperPager.InnerAdapter mInnerAdapter = new SobLooperPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getSubView(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getPicResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }
    };


    //...........looper..............................

    private void purecyccler() {

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
layoutManager.setReverseLayout(true);

    }


    //...............................................搜索..............
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.home_app_bar_search).getActionView();
        searchView.setMaxWidth(500);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                alllist.removeObservers(requireActivity());
                alllist = cookViewModal.findAllCooksLive(pattern);
                alllist.observe(requireActivity(), new Observer<List<ShowCarEntity>>() {
                    @Override
                    public void onChanged(List<ShowCarEntity> msg) {
                        int temp = cookAdapter.getItemCount();
                        cookAdapter.setAllCooks(msg);
                        if (temp != msg.size()) {

                            cookAdapter.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
    }


    private void deletescroll() {




    }

    private void adapter() {
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        alllist = cookViewModal.getAllCooksLive();

        alllist.observe(requireActivity(), new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {


                cookAdapter.setAllCooks(showCarEntities);
                cookAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(cookAdapter);
    }

    private void initCilck() {
        imageViewVBreakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), BreakfastActivity.class);
                startActivity(intent);
            }
        });
        imageViewLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LunchActivity.class);
                startActivity(intent);
            }
        });

        imageViewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), DinnerActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView() {
        imageViewVBreakFast = requireActivity().findViewById(R.id.imageView_breakfst);
        imageViewLaunch = requireActivity().findViewById(R.id.imageView_food_menu_lunch);
        imageViewDinner = requireActivity().findViewById(R.id.imageView_hone_dinner);

    }



}
