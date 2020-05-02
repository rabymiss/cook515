package com.example.cook.api;


import com.example.cook.apientity.FindCook;
import com.example.cook.apientity.PersonMessage;
import com.example.cook.apientity.Showcard;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.entity.show.ShowCarEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api {

    @POST("user/register")
    Call<RegisterEntity> posterRegister(@Body RequestBody requestBody);

    @POST("user/login")
    Call<RegisterEntity> LoginResult(@Body RequestBody requestBody);
    @POST("add/cook")
    Call<Showcard>addDook(@Body RequestBody requestBody);
    @POST("find/cook")
    Call<FindCook>findCook();
    @POST("add/messsage")
    Call<PersonMessage>addMsg(@Body RequestBody requestBody);
    @POST("find/message")
    Call<PersonMessage>finmsg(@Body RequestBody requestBody);

    @Multipart
    @POST("updown12")
    Call<RegisterEntity>load(@Part MultipartBody.Part part, @PartMap Map<String,String> params);

//    添加食谱
    @Multipart
    @POST("updown/cook")
    Call<RegisterEntity>loadcook(@Part MultipartBody.Part part, @PartMap Map<String,String> params);
    @POST("user/update")
    Call<RegisterEntity>updateuserr(@Body RequestBody requestBody);
}
