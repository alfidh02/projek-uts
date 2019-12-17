package com.mind.loginregisterapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Asynonym extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private List<AsynonymQuestion> AsynonymQuestion = new ArrayList<>();
    private TextView question,tvSignUp;
    private Button ansA, ansB, ansC;
    private int i = 0;
    int progressStatus = 0; //timer
    ProgressBar progressBar;
    Handler handler = new Handler();
    private RelativeLayout rlayout;
    private Animation animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //sesuaikan xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynonym);
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
        question = findViewById(R.id.question);
        tvSignUp = findViewById(R.id.tvSignUp);
        progressBar = findViewById(R.id.progressBar);

        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);

        tvSignUp.setText("Asynonym");
        requestQueue = Volley.newRequestQueue(this); //connect json
        getQuestionFromJsonRandomly();
        new Thread(new Runnable() {
            public void run() { //progresbar
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if(progressStatus == 99) {
                                ansA.setBackgroundResource(R.drawable.bg_false);
                                ansB.setBackgroundResource(R.drawable.bg_false);
                                ansC.setBackgroundResource(R.drawable.bg_false);
                                ansA.setEnabled(false);
                                ansB.setEnabled(false);
                                ansC.setEnabled(false);
                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200); //jeda
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setQuestionToLayout(AsynonymQuestion asq) { //set list soal
        question.setText(asq.getQuestion());
        List<String> option = new ArrayList<>();
        option.add(asq.getSynonym_1());
        option.add(asq.getsynonym_2());
        option.add(asq.getAnswer());
        Collections.shuffle(option); //acak
        ansA.setText(option.get(0)); //masukkan nilai option jawaban
        ansB.setText(option.get(1));
        ansC.setText(option.get(2));
    }

    private void getQuestionFromJsonRandomly() { //untuk random
        String url = "https://api.myjson.com/bins/cxjg4"; //ambil url json
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("asynonym");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                AsynonymQuestion asq = new AsynonymQuestion();
                                asq.setId(jsonObject.getInt("id"));
                                asq.setGroup_id(jsonObject.getString("group_id"));
                                asq.setQuestion(jsonObject.getString("question"));
                                asq.setAnswer(jsonObject.getString("answer"));
                                asq.setSynonym_1(jsonObject.getString("synonym_1"));
                                asq.setsynonym_2(jsonObject.getString("synonym_2"));
                                asq.setDescription(jsonObject.getString("description"));
                                AsynonymQuestion.add(asq);
                            }
                            Collections.shuffle(AsynonymQuestion);
                            setQuestionToLayout(AsynonymQuestion.get(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
    }


    @Override
    public void onClick(View v) { //deskripsi jawaban
        Button beenClicked = (Button) v;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mc_description, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        TextView value = dialogView.findViewById(R.id.value);
        if (beenClicked.getText().toString().equals(AsynonymQuestion.get(i).getAnswer())) {
            value.setText("Benar");
        } else {
            value.setText("Salah");
        }
        TextView description = dialogView.findViewById(R.id.description);
        description.setText(AsynonymQuestion.get(i).getDescription());
        dialog.setPositiveButton("Selanjutnya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i++;
                setQuestionToLayout(AsynonymQuestion.get(i));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
