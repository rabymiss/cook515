package com.example.cook.ui.notifications.ui;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cook.ButtomActivity;
import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.module.LoginActivity;
import com.example.cook.respository.UserViewmodel;
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

public class PasswordActivity extends AppCompatActivity {
    private static final String TAG ="PasswordActivity" ;
    private EditText newpsw,oldpsw,confirmpsw;
private TextView textViewusername;
private Button confim;



    private LiveData<List<RegisterEntity>> findmsg;
    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;
    private UserViewmodel userViewmodel;
    List<String>listall=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        userViewmodel= ViewModelProviders.of(this).get(UserViewmodel.class);

        oldpsw=findViewById(R.id.edpsw_oldpsw);
        newpsw=findViewById(R.id.text_new_psw);
        confirmpsw=findViewById(R.id.text_psw_confirm_psw);
        confim=findViewById(R.id.button7);
        textViewusername=findViewById(R.id.text_psw_username);

        initview();
        confirmpsw();
        findusername();
    }

    private void findusername() {
        findmsg=userViewmodel.list();
        findmsg.observe(this, new Observer<List<RegisterEntity>>() {
            @Override
            public void onChanged(List<RegisterEntity> registerEntities) {


                for (RegisterEntity registerEntity:registerEntities){
                    listall.add(registerEntity.getUsername());
                    SharedPreferences.Editor editor=getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                    editor.putString("username",listall.get(0));
                    editor.apply();
                    textViewusername.setText(listall.get(0));
                }


            }
        });


    }

    private void initview() {

    }

    private void confirmpsw(){

confim.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


final String old=oldpsw.getText().toString().trim();
final String newpw=newpsw.getText().toString().trim();
final String conf=confirmpsw.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.3:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        final RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setUsername(listall.get(0));


        registerEntity.setPassword(old);

        Gson gson = new Gson();
        String jsonstr = gson.toJson(registerEntity);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);

        Call<RegisterEntity> task = api.LoginResult(requestBody);
        task.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {


                if (response.body().getUsername().equals("1")) {

if (newpw.equals(conf)){
    final RegisterEntity registerEntity1= new RegisterEntity();
    registerEntity1.setUsername(listall.get(0));
    registerEntity1.setPassword(conf);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.20.10.3:8080")
            .addConverterFactory(GsonConverterFactory.create()).build();
    Api api = retrofit.create(Api.class);


    Gson gson = new Gson();
    String jsonstr1 = gson.toJson(registerEntity1);
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr1);

    Call<RegisterEntity> task = api.updateuserr(requestBody);
    task.enqueue(new Callback<RegisterEntity>() {
        @Override
        public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {

        }

        @Override
        public void onFailure(Call<RegisterEntity> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(PasswordActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    });



}else {

    Toast.makeText(getApplicationContext(), "新密码不一致", Toast.LENGTH_LONG).show();


}




















                } else if (response.body().getUsername().equals("0")) {
                    Toast.makeText(getApplicationContext(), "旧密码不正确", Toast.LENGTH_SHORT).show();

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








    }
});
    }
}
