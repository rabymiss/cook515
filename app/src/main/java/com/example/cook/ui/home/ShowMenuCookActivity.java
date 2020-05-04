package com.example.cook.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cook.R;
import com.example.cook.entity.CollectCenterEntity;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookCollectViewModal;
import com.example.cook.respository.CookViewModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowMenuCookActivity extends AppCompatActivity {

    private static final String TAG = "ShowMenuCookActivity";
    private LiveData<List<ShowCarEntity>> cookList;
    private CookViewModal cookViewModal;
    private CookCollectViewModal cookCollectViewModal;
    private TextView title, username;
    private TextView st1, st2, st3, st4, st5, st6, st7, st8;
    private ImageView cookend, useicon;
    private Button Collect;
    private ImageView s1, s2, s3, s4, s5, s6, s7;
    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;
    List<String>listall=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_cook);
        cookViewModal = ViewModelProviders.of(this).get(CookViewModal.class);
        cookCollectViewModal = ViewModelProviders.of(this).get(CookCollectViewModal.class);

        initView();
        initTitle();
    }

    private void initView() {
        title = findViewById(R.id.textcook_food_title);
        username = findViewById(R.id.text_menu_usernaem);
        st1 = findViewById(R.id.text_show_content);
        Collect = findViewById(R.id.button_collect_show);
        cookend=findViewById(R.id.image_cook_show_end);
//收藏按钮
        Collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectCenterEntity collectCenterEntity = new CollectCenterEntity();
                collectCenterEntity.setTitle(title.getText().toString());
                sp=getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
              collectCenterEntity.setImageIcon(sp.getString("url",""));
                collectCenterEntity.setAuthorName(username.getText().toString());
                System.out.println(sp.getString("url",""));
                Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                cookCollectViewModal.insertCollect(collectCenterEntity);

            }
        });
    }

    private void initTitle() {

        Intent intent = getIntent();
        String tiltlr = intent.getStringExtra("Title");
        cookList = cookViewModal.findAllCooksLive(tiltlr);
        Log.d(TAG, tiltlr);
        cookList.observe(this, new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {
                for (ShowCarEntity showCarEntity : showCarEntities) {
                    title.setText(showCarEntity.getTitle());
                    username.setText(showCarEntity.getUsername());
                    st1.setText(showCarEntity.getStep());
                    listall.add(showCarEntity.getEndImage());
                    Picasso.get().load("http://172.20.10.3:8080/"+showCarEntity.getEndImage()).into(cookend);                }
                SharedPreferences.Editor editor=getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                if (listall.size()!=0){
                    editor.putString("url",listall.get(0));
                }

                editor.apply();

            }
        });
    }
}
