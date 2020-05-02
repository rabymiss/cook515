package com.example.cook.ui.notifications.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.apientity.PersonMessage;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.module.LoginActivity;
import com.example.cook.respository.UserViewmodel;
import com.example.cook.ui.notifications.PersonMessageActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    List<String>listall=new ArrayList<>();
  private  RegisterEntity live=new RegisterEntity();
  private   LiveData<List<RegisterEntity>> findmsg;
    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;
  private Button buttonQuite,Bedit;
    private TextView username,nickname,password,burthday,email,phonenumber,addressp,workin,showyou;



    private UserViewmodel userViewmodel;
    List<String>stringList=new ArrayList<>();
    LiveData<List<RegisterEntity>> userlist;
    private PersonMessage gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

       userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);
finduser();

        initView();
        doview();
       lidata();


    }

    public void editpassword(View view){
Intent intent=new Intent(MessageActivity.this,PasswordActivity.class);
startActivity(intent);

    }

    private void finduser() {
        findmsg=userViewmodel.list();
        findmsg.observe(this, new Observer<List<RegisterEntity>>() {
            @Override
            public void onChanged(List<RegisterEntity> registerEntities) {
                for (RegisterEntity registerEntity:registerEntities){
                    listall.add(registerEntity.getUsername());
                    SharedPreferences.Editor editor=getSharedPreferences(TEMP_INFO,Context.MODE_PRIVATE).edit();
                    editor.putString("username",listall.get(0));
                    editor.apply();

                }
            }
        });




    }

    private void lidata() {










    }
    //获取editmessage

    private void doview() {
//
//
//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("ACCOUNT");//建议把它写一个公共的变量，这里方便阅读就不写了
//        BroadcastReceiver Receive =new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String account=intent.getStringExtra("account");
//
//       System.out.println("-----------json------"+ account);
//
//
//            }
//        };
//        broadcastManager.registerReceiver(Receive, intentFilter);





        sp=getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);

PersonMessage personMessage=new PersonMessage();
personMessage.setUsername(sp.getString("username",""));
System.out.println(personMessage);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.3:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Gson gson = new Gson();
        String jsonstr = gson.toJson(personMessage);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
        Call<PersonMessage> task=api.finmsg(requestBody);
task.enqueue(new Callback<PersonMessage>() {
    @Override
    public void onResponse(Call<PersonMessage> call, Response<PersonMessage> response) {
        System.out.println(response.toString());
PersonMessage gt=response.body();
        nickname.setText(gt.getNickname());
        addressp .setText(gt.getAddressp());
        burthday.setText(gt.getBurthday());
        email.setText(gt.getEmail());
        phonenumber.setText(gt.getPhonenumber());
        workin.setText(gt.getWorkin());


        Intent intent = new Intent();
        intent.setAction("android.intent.action.ACTION_FASONGA");//用隐式意图来启动广播
        intent.putExtra("you", gt.getShowyou());
        intent.putExtra("nickname", gt.getNickname());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

    @Override
    public void onFailure(Call<PersonMessage> call, Throwable t) {
System.out.println(t.toString());
    }
});






    }

    private void initView() {

//showyou=findViewById(R.id.text_person_speak);
Bedit=findViewById(R.id.button_message_edite);
Bedit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MessageActivity.this, PersonMessageActivity.class);
        startActivity(intent);
    }
});
        buttonQuite = findViewById(R.id.button_message_quite);
        buttonQuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                startActivity(intent);
                MessageActivity.this.finish();
            }
        });



        nickname=findViewById(R.id.t_res_name);
        addressp =findViewById(R.id.t_res_address);
        burthday=findViewById(R.id.t_res_burth);
         email=findViewById(R.id.t_res_e_mail);
        phonenumber=findViewById(R.id.t_res_telphone);
        workin=findViewById(R.id.t_res_work);




    }












}
