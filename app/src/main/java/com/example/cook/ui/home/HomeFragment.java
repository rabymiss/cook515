package com.example.cook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import com.example.cook.R;
import com.example.cook.adapter.CookAdapter;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private ImageView imageViewVBreakFast, imageViewLaunch, imageViewDinner;
    private CookViewModal cookViewModal;
    private LiveData<List<ShowCarEntity>> alllist;
    private RecyclerView recyclerView;
    private CookAdapter cookAdapter;

    public HomeFragment() {


        setHasOptionsMenu(true);
    }

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cookViewModal = ViewModelProviders.of(requireActivity()).get(CookViewModal.class);


        recyclerView = requireActivity().findViewById(R.id.recycle_food_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        cookAdapter = new CookAdapter();

        initView();
        initCilck();
//搜索

        adapter();
        //滑动删除
        deletescroll();
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

                Log.d(TAG, showCarEntities.toString());
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

//按钮


}
