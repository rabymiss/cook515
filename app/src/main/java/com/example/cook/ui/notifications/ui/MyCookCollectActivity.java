package com.example.cook.ui.notifications.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cook.R;
import com.example.cook.adapter.MyCookAdapter;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;
import com.example.cook.respository.MyCookViewModel;
import com.example.cook.respository.UserViewmodel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyCookCollectActivity extends AppCompatActivity {
    private MyCookViewModel myCookViewModel;
    private RecyclerView recyclerView;
    private MyCookAdapter myCookAdapter;
    LiveData<List<MyCookEntity>> allMyCook;
    List<MyCookEntity> myCookEntityList;
    private CookViewModal cookViewModal;
    LiveData<List<ShowCarEntity>>listLiveDataMy;

    private UserViewmodel userViewmodel;
    List<String>stringList=new ArrayList<>();
    LiveData<List<RegisterEntity>> userlist;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cook_collect);
        myCookAdapter = new MyCookAdapter();
        recyclerView = findViewById(R.id.recycler_menu_my);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userViewmodel = ViewModelProviders.of(this).get(UserViewmodel.class);
        //寻找username
myCookViewModel=ViewModelProviders.of(this).get(MyCookViewModel.class);
   Intent intent=getIntent();
//   String username=intent.getStringExtra("username");


        //....................................
//        myCookViewModel = ViewModelProviders.of(this).get(MyCookViewModel.class);
//        cookViewModal=ViewModelProviders.of(this).get(CookViewModal.class);
//
//        listLiveDataMy=cookViewModal.getUsercook(username);
//        listLiveDataMy.observe(this, new Observer<List<ShowCarEntity>>() {
//            @Override
//            public void onChanged(List<ShowCarEntity> showCarEntities) {
//                myCookViewModel.deleteAllCooks();
//                for (ShowCarEntity showCarEntity:showCarEntities){
//                    MyCookEntity myCookEntity=new MyCookEntity();
//                    myCookEntity.setTitle(showCarEntity.getTitle());
//                    myCookEntity.setAuthorName(showCarEntity.getUsername());
//                    myCookEntity.setImageIcon(showCarEntity.getUserIcon());
//                  myCookViewModel.insertCooks(myCookEntity);
//
//                }
//            }
//        });



        addCookMy();
        deleteCook();
    }

    //删除作品
    private void deleteCook() {

        //滑动删除
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final MyCookEntity delete = myCookEntityList.get(viewHolder.getAdapterPosition());
                myCookViewModel.deleteCooks(delete);
                myCookAdapter.notifyDataSetChanged();

                Snackbar.make(findViewById(R.id.recycler_menu_my), "删除了", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                myCookViewModel.insertCooks(delete);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);


    }

    private void addCookMy() {



        allMyCook = myCookViewModel.getAllCooksLive();
        allMyCook.observe(this, new Observer<List<MyCookEntity>>() {
           @Override
           public void onChanged(List<MyCookEntity> myCookEntities) {
               myCookEntityList = myCookEntities;
               myCookAdapter.setAllCollect(myCookEntities);
                myCookAdapter.notifyDataSetChanged();
           }
        });
        recyclerView.setAdapter(myCookAdapter);

    }
}
