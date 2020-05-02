package com.example.cook.ui.dashboard;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cook.R;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;

import java.util.List;

public class MyCollectActivity extends AppCompatActivity {

    private static final String TAG = "ShowMenuCookActivity";
    private LiveData<List<ShowCarEntity>> cookList;
    private CookViewModal cookViewModal;

    private TextView title, username;
    private TextView mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8;
    private TextView kg1, kg2, kg3, kg4, kg5, kg6, kg7, kg8;
    private TextView st1, st2, st3, st4, st5, st6, st7, st8;
    private ImageView cookend, useicon;
    private ImageView s1, s2, s3, s4, s5, s6, s7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        cookViewModal = ViewModelProviders.of(this).get(CookViewModal.class);


        initView();
        initTitle();
    }

    private void initView() {
        title = findViewById(R.id.textcook_food_title);
        username = findViewById(R.id.text_menu_usernaem);

        mat1 = findViewById(R.id.text_foodMaterial1);
        mat2 = findViewById(R.id.text_foodMaterial2);
        mat3 = findViewById(R.id.text_foodMaterial3);
        mat4 = findViewById(R.id.text_foodMateril4);
        mat5 = findViewById(R.id.text_foodMaterial5);
        mat6 = findViewById(R.id.text_foodMaterial6);


        st1 = findViewById(R.id.text_sp1);
        st2 = findViewById(R.id.text_sp2);
        st3 = findViewById(R.id.text_sp3);
        st4 = findViewById(R.id.text_sp4);
        st5 = findViewById(R.id.text_sp5);
        st6 = findViewById(R.id.text_sp6);
        st7 = findViewById(R.id.text_sp7);


    }

    private void initTitle() {
        Intent intent = getIntent();
        String tiltlr = intent.getStringExtra("cookName");
        cookList = cookViewModal.findAllCooksLive(tiltlr);
        Log.d(TAG, tiltlr);
        cookList.observe(this, new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {
                Log.d(TAG, "搜索结果" + showCarEntities);
                for (ShowCarEntity showCarEntity : showCarEntities) {
                    title.setText(showCarEntity.getTitle());
                    username.setText(showCarEntity.getUsername());


                }


            }
        });
    }
}

