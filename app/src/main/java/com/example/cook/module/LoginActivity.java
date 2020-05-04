package com.example.cook.module;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.cook.ButtomActivity;
import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.apientity.FindCook;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;
import com.example.cook.respository.MyCookViewModel;
import com.example.cook.respository.UserViewmodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    final String REMEMBER_PWD_PREF = "rememberPwd";
    final String ACCOUNT_PREF = "account";
    final String PASSWORD_PREF = "password";
    private UserViewmodel userViewmodel;

//   private     FindCook findCook;
    private CookViewModal cookViewModal;
    List<FindCook.DataBean>dataBeanList=new ArrayList<>();
    LiveData<List<ShowCarEntity>>listLiveDatashow;
    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;

    // private JobViewModel jobViewModel;
    // private UserViewModel userViewModel;
    private TextView textViewAccount, textViewPassword, buttonRegister;
    private Button buttonConfirm;
    private MyCookViewModel myCookViewModel;
    LiveData<List<ShowCarEntity>>listLiveDataMy;
    // private List<JobResultEntity.DataBean> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);
        buttonConfirm = findViewById(R.id.button);
        buttonRegister = findViewById(R.id.textView12);
        textViewAccount = findViewById(R.id.textView9);
        textViewPassword = findViewById(R.id.textView11);
        cookViewModal=ViewModelProviders.of(this).get(CookViewModal.class);

        myCookViewModel = ViewModelProviders.of(this).get(MyCookViewModel.class);

        addmyself();
        //加载信息
        addcook();
        //  jobViewModel=ViewModelProviders.of(this).get(JobViewModel.class);
        //记住密码

        boolean isRemember = sp.getBoolean(REMEMBER_PWD_PREF, false);
        final CheckBox rememberPwd = (CheckBox) findViewById(R.id.checkBox_login);


        if (isRemember) {//设置【账号】与【密码】到文本框，并勾选【记住密码】
            textViewAccount.setText(sp.getString(ACCOUNT_PREF, ""));
            textViewPassword.setText(sp.getString(PASSWORD_PREF, ""));
            rememberPwd.setChecked(true);
        }


        //   userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String account = textViewAccount.getText().toString().trim();
                final String password = textViewPassword.getText().toString().trim();










//登陆操作

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.3:8080")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                Api api = retrofit.create(Api.class);
                final RegisterEntity registerEntity = new RegisterEntity();
                registerEntity.setUsername(account);
                registerEntity.setPassword(password);

                Gson gson = new Gson();
                String jsonstr = gson.toJson(registerEntity);
                final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);

                Call<RegisterEntity> task = api.LoginResult(requestBody);
                task.enqueue(new Callback<RegisterEntity>() {
                    @Override
                    public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {

                        int a = response.code();
                        if (response.body().getUsername().equals("1")) {
//                            Log.d(TAG, "成功登录-----------" + response.body().getUsername());

                            userViewmodel.deleteall();
                            userViewmodel.insert(registerEntity);
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                            if (rememberPwd.isChecked()) {//记住账号与密码
                                editor.putBoolean(REMEMBER_PWD_PREF, true);
                                editor.putString(ACCOUNT_PREF, account);
                                editor.putString(PASSWORD_PREF, password);
                            } else {//清空数据
                                editor.clear();
                            }
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, ButtomActivity.class);
                            startActivity(intent);

                        } else if (response.body().getUsername().equals("0")) {
                            Toast.makeText(getApplicationContext(), "账号或密码不正确", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "后台异常请联系管理员", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterEntity> call, Throwable t) {
                        Log.d(TAG, "失败" + t.toString());
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

//..................................................................................................




            }
        });
    }

    private void addmyself() {
        sp = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
      String username= sp.getString(ACCOUNT_PREF,"");

        listLiveDataMy=cookViewModal.getUsercook(username);
        listLiveDataMy.observe(this, new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {
                myCookViewModel.deleteAllCooks();
                for (ShowCarEntity showCarEntity:showCarEntities){
                    MyCookEntity myCookEntity=new MyCookEntity();
                    myCookEntity.setTitle(showCarEntity.getTitle());
                    myCookEntity.setAuthorName(showCarEntity.getUsername());
                    myCookEntity.setImageIcon(showCarEntity.getUserIcon());
                    myCookViewModel.insertCooks(myCookEntity);

                }
            }
        });


    }

    private void addcook() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.3:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        final Call<FindCook> task = api.findCook();
task.enqueue(new Callback<FindCook>() {
    @Override
    public void onResponse(Call<FindCook> call, Response<FindCook> response) {


    FindCook   findCook=response.body();
    dataBeanList.addAll(findCook.getData());
    for (FindCook.DataBean dataBean:dataBeanList){

        ShowCarEntity showCarEntity=new ShowCarEntity();
        showCarEntity.setCookType(dataBean.getCookType());
        showCarEntity.setEndImage(dataBean.getImage());
        showCarEntity.setStep(dataBean.getStepImage1());
        showCarEntity.setCookName(dataBean.getCookName());
        showCarEntity.setCookName(dataBean.getTitle());
        showCarEntity.setTitle(dataBean.getTitle());
        showCarEntity.setUsername(dataBean.getUsername());
       showCarEntity.setT1(dataBean.getT1());
        cookViewModal.deleteAllCooks();
        cookViewModal.insertCooks(showCarEntity);











          //  cookViewModal.insertCooks( );



        }





    }

    @Override
    public void onFailure(Call<FindCook> call, Throwable t) {
        System.out.println(t.toString());
        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
    }
});


    }


}

