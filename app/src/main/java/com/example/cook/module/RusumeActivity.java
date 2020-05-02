package com.example.cook.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cook.R;

public class RusumeActivity extends AppCompatActivity {
    private EditText edName;
    private Button confirm;

    private SharedPreferences sp;
    private static final String TEMP_INFO = "temp_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        initView();
        initDo();
    }

    private void initDo() {

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
                editor.putString("edName", edName.getText().toString());
                Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
                editor.commit();


            }
        });

    }

    private void initView() {
        edName = findViewById(R.id.textView21);

        confirm = findViewById(R.id.button3);

        sp = getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        String content1 = sp.getString("edName", "");

        edName.setText(content1);
    }
}
