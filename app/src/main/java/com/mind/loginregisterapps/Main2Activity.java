package com.mind.loginregisterapps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {
    private int waktu_loading = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                setelah loading maka akan langsung berpindah ke main activity utama
                Intent home= new Intent(Main2Activity.this, MainActivity.class);
                startActivity(home);
                finish();
            }
        },waktu_loading);
    }
}
