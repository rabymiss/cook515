package com.example.cook.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.apientity.Showcard;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;
import com.example.cook.respository.MyCookViewModel;
import com.example.cook.respository.UserViewmodel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFoodActivity extends AppCompatActivity {
    private static final String TAG = "AddFoodActivity";
    private EditText cookTitle, foodType, foodMA2, foodMA3, foodMA4, foodMA5, foodMA6, foodMA7, foodMA8;
    private EditText kg1, kg2, kg3, kg4, kg5, kg6, kg7, kg8;
    private EditText step1, step2, step3, step4, step5, step6, step7, step8;
    private ImageView i1, i2, i3, i4, i5, i6;
    private Button buttonConfirm;
    private CookViewModal cookViewModal;
    private MyCookViewModel myCookViewModel;
    private EditText foodhowdo;
     private UserViewmodel userViewmodel;
     List<String>stringList=new ArrayList<>();
    LiveData<List<RegisterEntity>> userlist;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        cookViewModal = ViewModelProviders.of(this).get(CookViewModal.class);
        myCookViewModel = ViewModelProviders.of(this).get(MyCookViewModel.class);
        userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);
        initView();
        doing();
        adaperphoto();
        finduser();
    }

    private void adaperphoto() {
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        uuid = intent.getStringExtra("uuid");
        System.out.println("uuid------------"+ uuid);
        if (url!=null){
            Picasso.get().load(url).into(i1);
        }


    }

    private void finduser() {
        userlist=userViewmodel.list();
        userlist.observe(this, new Observer<List<RegisterEntity>>() {
            @Override
            public void onChanged(List<RegisterEntity> registerEntities) {
                for (RegisterEntity registerEntity:registerEntities){
                    stringList.add(registerEntity.getUsername());

                }
            }
        });
    }

    private void doing() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShowCarEntity showCarEntity = new ShowCarEntity();
                showCarEntity.setTitle(cookTitle.getText().toString().trim());
                showCarEntity.setStep(foodhowdo.getText().toString().trim());
                showCarEntity.setCookType(foodType.getText().toString().trim());


                final MyCookEntity myCookEntity = new MyCookEntity();
                myCookEntity.setTitle(showCarEntity.getTitle());
                myCookEntity.setAuthorName(showCarEntity.getUsername());
         myCookViewModel.insertCooks(myCookEntity);

                //.................................
                Showcard showcard=new Showcard();
                showcard.setTitle(showCarEntity.getTitle());
                showcard.setStepImage1(showCarEntity.getStep());
                showcard.setCookName(uuid);
                showcard.setCookType(showCarEntity.getCookType());
                showcard.setUsername(showCarEntity.getUsername());
                showcard.setUserIcon(showCarEntity.getUserIcon());
                showcard.setImage(showCarEntity.getImage());
                showcard.setUsername(stringList.get(0));

                Gson gson =new Gson();
                String jsontr=gson.toJson(showcard);


                Log.d(TAG, showCarEntity.toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.3:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api = retrofit.create(Api.class);
                final RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsontr);
                Call<Showcard> task=api.addDook(requestBody);

task.enqueue(new Callback<Showcard>() {
    @Override
    public void onResponse(Call<Showcard> call, Response<Showcard> response) {
        System.out.println(response.body());

    }

    @Override
    public void onFailure(Call<Showcard> call, Throwable t) {
System.out.println( "错误"+ t.toString());
    }
});

cookViewModal.insertCooks(showCarEntity);
//                myCookViewModel.insertCooks(myCookEntity);
                AddFoodActivity.this.finish();


            }
        });
    }

    private void initView() {
        cookTitle = findViewById(R.id.Edite_cook_food_title);
        foodhowdo = findViewById(R.id.edit_food_how_do);
        foodType = findViewById(R.id.editText_food_type);

//添加照片
        i1 = findViewById(R.id.image_step1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFoodActivity.this,AddPhotoActivity.class);
                startActivity(intent);
            }
        });
        buttonConfirm = findViewById(R.id.button_speak);


    }
}
