package com.example.cook.module;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.entity.RegisterEntity;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.P)

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private TextView textViewAccount, textViewPassword, textViewPasswordConfirm;
    private Button buttonConfirmRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonConfirmRegister = findViewById(R.id.button2);
        textViewAccount = findViewById(R.id.textView3);
        textViewPassword = findViewById(R.id.textView5);
        textViewPasswordConfirm = findViewById(R.id.textView7);

        buttonConfirmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = textViewAccount.getText().toString().trim();
                String password1 = textViewPassword.getText().toString().trim();
                String password2 = textViewPasswordConfirm.getText().toString().trim();


                //加载工作信息
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://172.20.10.3:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                if (password1.equals(password2)) {
//                  Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    final RegisterEntity registerEntity = new RegisterEntity();
                    registerEntity.setUsername(account);
                    registerEntity.setPassword(password1);

                    Api api = retrofit.create(Api.class);

                    Gson gson = new Gson();
                    String jsonstr = gson.toJson(registerEntity);
                    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
                    Call<RegisterEntity> task = api.posterRegister(requestBody);
                    task.enqueue(new Callback<RegisterEntity>() {

                        public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                            int a = response.code();
                            if (a == HttpURLConnection.HTTP_OK) {
                                Log.d(TAG, "Json-->" + response.body());
                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                              RegisterActivity.this.finish();
                              
                            }

                        }

                        public void onFailure(Call<RegisterEntity> call, Throwable t) {
                            Log.d(TAG, "失败" + t.toString());
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "两次密码不一致，请重新输入", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



