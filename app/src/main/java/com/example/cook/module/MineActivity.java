package com.example.cook.module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cook.R;

public class MineActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        TextView textView1 = (TextView) findViewById(R.id.textView17);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        TextView textView2 = (TextView) findViewById(R.id.textView18);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, ColloctActivity.class);
                startActivity(intent);
            }
        });
    }
}
