package com.example.cook.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.cook.R;
import com.example.cook.adapter.CookAdapter;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;

import java.util.List;

public class DinnerActivity extends AppCompatActivity {
    private CookViewModal cookViewModal;
    private LiveData<List<ShowCarEntity>> alllist;
    private RecyclerView recyclerView;
    private CookAdapter cookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        cookViewModal = ViewModelProviders.of(this).get(CookViewModal.class);


        recyclerView = findViewById(R.id.recycler_dinner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cookAdapter = new CookAdapter();
        adpter();
    }

    private void adpter() {
        alllist = cookViewModal.type("午餐");
        alllist.observe(this, new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {


                cookAdapter.setAllCooks(showCarEntities);
                cookAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(cookAdapter);
    }
    }

