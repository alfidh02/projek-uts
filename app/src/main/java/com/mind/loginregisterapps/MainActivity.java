package com.mind.loginregisterapps;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Button btn_mc,btn_asy,btn_tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_mc = findViewById(R.id.Multiple_Choice);
        btn_asy = findViewById(R.id.Asynonym);
        btn_tf = findViewById(R.id.True_False);

        btn_mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Multiple_Choice.class);
                startActivity(intent);
            }
        });
        btn_asy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Asynonym.class);
                startActivity(intent);
            }
        });
        btn_tf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrueFalse.class);
                startActivity(intent);
            }
        });
    }

}
