package com.example.cook.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.cook.R;
import com.example.cook.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowMainActivity extends AppCompatActivity {

    private static final int LOAD_ID = 1;
    private List<ImagEntity> imagEntityList=new ArrayList<>();
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_main);
        fianimag();
        INITV();
        iitview();
    }

    private void iitview() {
        RecyclerView recyclerView=this.findViewById(R.id.recycler_celect);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        imageAdapter= new ImageAdapter();
        recyclerView.setAdapter(imageAdapter);
    }

    private void INITV() {


        imagEntityList.clear();
        LoaderManager loaderManager=LoaderManager.getInstance(this);

        loaderManager.initLoader(LOAD_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

                if (id==LOAD_ID){
                    return new CursorLoader(ShowMainActivity.this,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{"_data",
                    "_display_name",
                    "date_added"},null,null,"date_added DESC");
                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
if (data!=null){
    String[] columnNames=data.getColumnNames();
        while (data.moveToNext()){
String t=data.getString(1);
Long date=data.getLong(2);
String pa=data.getString(0);
ImagEntity imagEntity=new ImagEntity(pa,t,date);
imagEntityList.add(imagEntity);

//            System.out.println("------------------------------------------");
//
//                for (String colum:columnNames){
//                    Log.d("AAAAAAAAA",colum+"---------------"+data.getString(data.getColumnIndex(colum)));
//                    System.out.println();
//                }
//                System.out.println("------------------------------------------");




        }          data.close();
        imageAdapter.setdata(imagEntityList);
//        for (ImagEntity imagEntity1:imagEntityList){
//            Log.d("aaaaaa",imagEntity1.toString());
//        }
}
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });

    }

    private void fianimag() {

//        ContentResolver contentResolver=getContentResolver();
//        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        Cursor query=contentResolver.query(uri,null,null,null,null);
//            String[] columnNames=query.getColumnNames();
//            while (query.moveToNext()){
//                System.out.println("------------------------------------------");
//
//                for (String colum:columnNames){
//                    Log.d("AAAAAAAAA",colum+"---------------"+query.getString(query.getColumnIndex(colum)));
//                    System.out.println();
//                }
//                System.out.println("------------------------------------------");
//
//            }
//            query.close();

        }
    }

