package com.example.cook.ui.notifications;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.apientity.PersonMessage;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.img.image.PhotoActivity;
import com.example.cook.respository.UserViewmodel;
import com.example.cook.ui.notifications.ui.MessageActivity;
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

public class PersonMessageActivity extends AppCompatActivity {
    private static final String TAG ="PersonMessageActivity" ;
    private EditText edName, edBurth, edpolitics, edemail, edphone, edaddress, edwork;
    private EditText edqwer, deteached, edworkming, edshowyouself;
     private ImageView imageViewIcon;
    private Button confirm;

    private UserViewmodel userViewmodel;
    List<String>listall=new ArrayList<>();
    LiveData<List<RegisterEntity>>liveData;
    PersonMessage personMessage=new PersonMessage();
    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        userViewmodel= ViewModelProviders.of(this).get(UserViewmodel.class);
        inoitView();
        initDo();
        livedata();
        getintent();
    }

    private void getintent() {

        Intent intent=getIntent();
        url = intent.getStringExtra("url");
        Picasso.get().load(url).into(imageViewIcon);
    }

    //编辑头像
    public void onclickedimg(View view){
        Intent intent=new Intent(PersonMessageActivity.this, PhotoActivity.class);

        startActivity(intent);


    }
    private void livedata() {

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACCOUNT");//建议把它写一个公共的变量，这里方便阅读就不写了
        BroadcastReceiver Receive =new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
              String  username = intent.getStringExtra("account");
                listall.add(username);
                System.out.println("usena--------"+username);

            }
        };
        broadcastManager.registerReceiver(Receive, intentFilter);







 liveData=userViewmodel.list();
 liveData.observe(this, new Observer<List<RegisterEntity>>() {
     @Override
     public void onChanged(List<RegisterEntity> registerEntities) {
    for (RegisterEntity registerEntity2:registerEntities){

        SharedPreferences.Editor editor=getSharedPreferences(TEMP_INFO,Context.MODE_PRIVATE).edit();
        editor.putString("username",registerEntity2.getUsername());
        editor.apply();

    }
     }
 });
    }

    private void inoitView() {
        edName = findViewById(R.id.edit_res_name);
        edBurth = findViewById(R.id.edit_res_burth);
//        edpolitics = findViewById(R.id.edit_res_politics);
        edemail = findViewById(R.id.edit_res_e_mail);
        edphone = findViewById(R.id.edit_res_telphone);

        edaddress = findViewById(R.id.edit_res_address);
        edwork = findViewById(R.id.edit_res_work);
        edshowyouself = findViewById(R.id.edit_show_you);


        confirm = findViewById(R.id.button_res_confirm);
imageViewIcon=findViewById(R.id.imageView_pco);

        sp = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        String content1 = sp.getString("edName", "");
        String content2 = sp.getString("edBurth", "");

        String content4 = sp.getString("edemail", "");
        String content5 = sp.getString("edphone", "");
        String content6 = sp.getString("edaddress", "");
        String content7 = sp.getString("edwork", "");
        String content8 = sp.getString("edshowyou", "");

        edName.setText(content1);
        edBurth.setText(content2);

        edemail.setText(content4);
        edphone.setText(content5);

        edaddress.setText(content6);
        edwork.setText(content7);
        edshowyouself.setText(content8);


    }

    private void initDo() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                editor.putString("edName", edName.getText().toString());
                editor.putString("edBurth", edBurth.getText().toString());

                editor.putString("edemail", edemail.getText().toString());
                editor.putString("edphone", edphone.getText().toString());
                editor.putString("edaddress", edaddress.getText().toString());

                editor.putString("edwork", edwork.getText().toString());
                editor.putString("edshowyou", edshowyouself.getText().toString());
//                editor.putString("deteached", deteached.getText().toString());
//                editor.putString("edworkming", edworkming.getText().toString());
//                editor.putString("edshowyouself", edshowyouself.getText().toString());



                personMessage.setAddressp(edaddress.getText().toString());
                personMessage.setBurthday(edBurth.getText().toString());
                personMessage.setEmail( edemail.getText().toString());
                personMessage.setNickname(edName.getText().toString());
                personMessage.setPhonenumber(edphone.getText().toString());
                personMessage.setShowyou(edshowyouself.getText().toString());
                personMessage.setWorkin(edwork.getText().toString());
                sp=getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
             personMessage.setUsername(sp.getString("username",""));



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.3:8080")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                Api api = retrofit.create(Api.class);
                Gson gson = new Gson();
                String jsonstr = gson.toJson(personMessage);
                final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
                Call<PersonMessage>task=api.addMsg(requestBody);
                task.enqueue(new Callback<PersonMessage>() {
                    @Override
                    public void onResponse(Call<PersonMessage> call, Response<PersonMessage> response) {

                            System.out.println("-------------------"+response.code());
                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<PersonMessage> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });





                editor.commit();


                Intent intent=new Intent(PersonMessageActivity.this,NotificationsFragment.class);




//                startActivity(intent);




            }
        });
    }
}
