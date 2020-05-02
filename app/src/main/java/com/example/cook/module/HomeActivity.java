package com.example.cook.module;

import android.app.Activity;
import android.os.Bundle;

import com.example.cook.R;

public class HomeActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*ImageView imageView=(ImageView) findViewById(R.id.imageView12);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,MineActivity.class);
                startActivity(intent);
            }
        });
*/

    }
}
