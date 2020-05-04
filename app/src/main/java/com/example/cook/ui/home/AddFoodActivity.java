package com.example.cook.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
private ShowCarEntity spinner=new ShowCarEntity();
    private ImageView i1, i2, i3, i4, i5, i6;
    private Button buttonConfirm;
    private CookViewModal cookViewModal;
    private MyCookViewModel myCookViewModel;
    private EditText foodhowdo;
     private UserViewmodel userViewmodel;
     List<String>stringList=new ArrayList<>();
    LiveData<List<RegisterEntity>> userlist;
    private String uuid;
    private AlertDialog alertDialog3;
private  List<String>list=new ArrayList<>();
    //定义控件变量
    private Spinner sp;
    private TextView info,diaglong;
    private AlertDialog.Builder alertBuilder;


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
        spiner();
        //多选
        diaglong();

    }

    public void add(View view){

    }
    private void diaglong() {

        diaglong=findViewById(R.id.text_add_showtype);
         diaglong.setText(R.string.add_type);




    }





    private void spiner() {
        sp = (Spinner) findViewById(R.id.spinner);
        info=findViewById(R.id.txtInfo);
        List<String> data = new ArrayList<String>();
        data.add("早餐");
        data.add("中餐");
        data.add("晚餐");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,  //使用系统预先定义的布局
//       		R.layout.myitem, //对Spinner文本框使用自定义的布局
                data);

        //设置下拉框的布局
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(R.layout.myitem); //弹出的浮动菜单使用自定义布局

        //设置Spinner的适配器对象
        sp.setAdapter(adapter);

        //设置默认选项
        sp.setSelection(2);

        //添加点击选择的监听器
        //view--点击的行对应的View(TextView)
        //position--点击选择的行号
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TextView) view).getText().toString();
                Intent intent = new Intent();
                intent.setAction("TYPE");//用隐式意图来启动广播
                intent.putExtra("type", s);

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                info.setText(  s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void adaperphoto() {

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("TYPE");//建议把它写一个公共的变量，这里方便阅读就不写了
        BroadcastReceiver Receive =new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String account=intent.getStringExtra("type");

spinner.setCookType(account);


            }
        };
        broadcastManager.registerReceiver(Receive, intentFilter);
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

                String t1=diaglong.getText().toString();
                ShowCarEntity showCarEntity = new ShowCarEntity();
                showCarEntity.setTitle(cookTitle.getText().toString().trim());
                showCarEntity.setStep(foodhowdo.getText().toString().trim());
                showCarEntity.setCookType(spinner.getCookType());


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
              showcard.setT1(t1);
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
//        foodType = findViewById(R.id.editText_food_type);

//添加照片
        i1 = findViewById(R.id.image_step1);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFoodActivity.this, AddPhotoActivity.class);
                startActivity(intent);
            }
        });
        buttonConfirm = findViewById(R.id.button_speak);


    }

    //多选
    public void showMutilAlertDialog(View view){
        final String[] items = {"主食", "汤", "甜品","饮料","小吃"};
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("请选择分类");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                 list.add(items[i]);
//                    Toast.makeText(getApplicationContext(), "选择" + items[i], Toast.LENGTH_SHORT).show();
                }else {
                    list.remove(i);
//                    Toast.makeText(getApplicationContext(), "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                }
            }
        });


        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                    diaglong.setText(" ");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int x=0;x<list.size();x++){
                                diaglong.append(list.get(x)+" ");

                        }}
                    });
                list.clear();
                alertDialog3.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialog3.dismiss();
            }
        });


        alertDialog3 = alertBuilder.create();
        alertDialog3.show();
    }
}
