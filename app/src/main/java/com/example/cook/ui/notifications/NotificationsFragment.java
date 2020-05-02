package com.example.cook.ui.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.fastjson.JSON;
import com.example.cook.R;
import com.example.cook.api.Api;
import com.example.cook.apientity.FindCook;
import com.example.cook.apientity.PersonMessage;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.module.LoginActivity;
import com.example.cook.respository.MyCookViewModel;
import com.example.cook.respository.UserViewmodel;
import com.example.cook.ui.home.AddFoodActivity;
import com.example.cook.ui.notifications.ui.CookMenuActivity;
import com.example.cook.ui.notifications.ui.MessageActivity;
import com.example.cook.ui.notifications.ui.MyCookCollectActivity;
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

import static android.view.View.resolveSize;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class NotificationsFragment extends Fragment {
    private TextView textbcook, textedit, textmymessage,showyou,textusernme,nickname1;
    private LinearLayout linearLayoutCook, linearMyCOok;
    private TextView cookMynumer;
    private MyCookViewModel myCookViewModel;
    private ImageView icon;
    private LiveData<List<MyCookEntity>> listLiveData;
    private   LiveData<List<RegisterEntity>> findmsg;
    private UserViewmodel userViewmodel;
    List<String>listall=new ArrayList<>();

    private static final String TEMP_INFO = "temp_info";
    private SharedPreferences sp;
    private String username;

    public NotificationsFragment() {


        setHasOptionsMenu(true);
    }

    private NotificationsViewModel notificationsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        return inflater.inflate(R.layout.fragment_person, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myCookViewModel = ViewModelProviders.of(requireActivity()).get(MyCookViewModel.class);
        userViewmodel=ViewModelProviders.of(requireActivity()).get(UserViewmodel.class);

        initView();
        doinit();
        personmessg();
//寻找username
        //加载pmsg
addpmsg();
        findmsg=userViewmodel.list();
        findmsg.observe(requireActivity(), new Observer<List<RegisterEntity>>() {
            @Override
            public void onChanged(List<RegisterEntity> registerEntities) {
                for (RegisterEntity registerEntity:registerEntities){

                    listall.add(registerEntity.getUsername());
                    listall.add(registerEntity.getPassword());
                    SharedPreferences.Editor editor =requireActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                    editor.putString("username",listall.get(0));
editor.apply();
                }
            }
        });
    }

    private void addpmsg() {


//接收广播

//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("ACCOUNT");//建议把它写一个公共的变量，这里方便阅读就不写了
//        BroadcastReceiver Receive =new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String account=intent.getStringExtra("account");
//                Toast.makeText(getContext(),account,Toast.LENGTH_LONG).show();
//
//
//
//
//
//
//            }
//        };
//        broadcastManager.registerReceiver(Receive, intentFilter);







        sp = requireActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);

PersonMessage personMessage=new PersonMessage();
personMessage.setUsername(sp.getString("account",""));
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


        Intent intent = new Intent();
        intent.setAction("ACCOUNT");//用隐式意图来启动广播
        intent.putExtra("you", gt.getShowyou());
        intent.putExtra("nickname", gt.getNickname());
        intent.putExtra("icon",gt.getIcon());
        LocalBroadcastManager.getInstance(requireActivity()).sendBroadcast(intent);

    }

    @Override
    public void onFailure(Call<PersonMessage> call, Throwable t) {
System.out.println(t.toString());
    }
});
    }

    private void personmessg() {

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACCOUNT");//建议把它写一个公共的变量，这里方便阅读就不写了
        BroadcastReceiver Receive =new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String you=intent.getStringExtra("you");
                String nickname=intent.getStringExtra("nickname");
                String icon1=intent.getStringExtra("icon");
                Picasso.get().load("http:/172.20.10.3:8080/"+icon1).into(icon);
                showyou.setText(you);
                nickname1.setText(nickname);

//                SharedPreferences.Editor editor =requireActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
//        editor.putString("you",you);
//        editor.putString("nickname",nickname);
//        editor.commit();
//                showyou.setText(msg);

            }
        };
        broadcastManager.registerReceiver(Receive, intentFilter);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http:/172.20.10.3:8080")
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        Api api = retrofit.create(Api.class);
//        Gson gson = new Gson();
//        String jsonstr = gson.toJson(personMessage);
//        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
//        Call<PersonMessage> task=api.finmsg(requestBody);
//        task.enqueue(new Callback<PersonMessage>() {
//            @Override
//            public void onResponse(Call<PersonMessage> call, Response<PersonMessage> response) {
//                System.out.println(response.toString());
//                PersonMessage gt=response.body();
//
//              showyou.setText(gt.getShowyou());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<PersonMessage> call, Throwable t) {
//                System.out.println(t.toString());
//            }
//        });



    }


    void TextView(View v) {


    }

    private void doinit() {
        listLiveData = myCookViewModel.getAllCooksLive();
        listLiveData.observe(requireActivity(), new Observer<List<MyCookEntity>>() {
            @Override
            public void onChanged(List<MyCookEntity> myCookEntities) {

                int a = myCookEntities.size();
                cookMynumer.setText(String.valueOf(a));

            }
        });
    }

    private void initView() {

icon=requireActivity().findViewById(R.id.image_person_icon);

//g个人简介
      showyou=requireActivity().findViewById(R.id.text_person_speak);
        sp = requireActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);

//       showyou.setText(sp.getString("you",""));

      textusernme=requireActivity().findViewById(R.id.text_person_username);



          nickname1=requireActivity().findViewById(R.id.text_person_username);
//          nickname.setText(sp.getString("nickname",""));

        cookMynumer = requireActivity().findViewById(R.id.text_cook_other_number);
       linearLayoutCook = requireActivity().findViewById(R.id.linear_perosn_cook);
        linearMyCOok = requireActivity().findViewById(R.id.linear_my_cook);
//
//  //.....................................................................
      textbcook = requireActivity().findViewById(R.id.text123);
      textbcook.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(requireActivity(), AddFoodActivity.class);
                      startActivity(intent);
          }
      });




//        //.......................................................
       textedit = requireActivity().findViewById(R.id.text28);
       textedit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



                        Intent intent = new Intent(requireActivity(), MessageActivity.class);

//                        intent.putExtra("username",listall.get(0));
//                        intent.putExtra("password",listall.get(1));
                        startActivity(intent);



//               Intent intent = new Intent(requireActivity(), MessageActivity.class);
//                startActivity(intent);
           }
       });
//
//        //.............................................................
//
               textmymessage = requireActivity().findViewById(R.id.text_person_username);
               textmymessage.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                   }
               });
//
//                //................................................................
//
//
       //z作品
        linearMyCOok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MyCookCollectActivity.class);
//                intent.putExtra("username",listall.get(0));
//                intent.putExtra("password",listall.get(1));
             startActivity(intent);
            }
        });
//
linearLayoutCook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(requireActivity(), CookMenuActivity.class);
             startActivity(intent);
    }
});




}
}
