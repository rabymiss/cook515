package com.example.cook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cook.R;
import com.example.cook.entity.show.ShowCarEntity;
import com.example.cook.respository.CookViewModal;

import java.util.List;

public class TextActivity extends AppCompatActivity  {
    private static final String TAG ="TextActivity" ;
    private EditText editText;
    private CookViewModal cookViewModal;
    private LiveData<List<ShowCarEntity>> alllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        cookViewModal = ViewModelProviders.of(this).get(CookViewModal.class);
        editText=findViewById(R.id.editText);



}
public void find(View view){
    Toast.makeText(this,editText.getText(),Toast.LENGTH_LONG).show();

        alllist=cookViewModal.findtypett(editText.getText().toString());
    Log.d(TAG,alllist.toString());
        alllist.observe(this, new Observer<List<ShowCarEntity>>() {
            @Override
            public void onChanged(List<ShowCarEntity> showCarEntities) {
                for (ShowCarEntity showCarEntity:showCarEntities){
                    Log.d(TAG,showCarEntity.toString());
                }
            }
        });


}
}