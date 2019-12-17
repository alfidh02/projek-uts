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
import android.widget.Button;
import android.widget.ProgressBar;
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

public class TrueFalse extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private List<TrueFalseQuestion> TrueFalseQuestion = new ArrayList<>();
    private Button ansA, ansB;
    private TextView tvSignUp;
    private int i = 0;
    int progressStatus = 0; //timer
    ProgressBar progressBar;
    Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //sesuaikan xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truefalse);
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);

        tvSignUp = findViewById(R.id.tvSignUp);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);

        tvSignUp.setText("True/False");
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
                                ansA.setEnabled(false);
                                ansB.setEnabled(false);
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

    private void setQuestionToLayout(TrueFalseQuestion tf) { //set list soal
        List<String> option = new ArrayList<>();
        option.add(tf.getcorrect_statement());
        option.add(tf.getincorrect_statement());
        option.add(tf.get_remark());
        Collections.shuffle(option); //acak
        ansA.setText(option.get(0)); //masukkan nilai option jawaban
        ansB.setText(option.get(1));
    }

    private void getQuestionFromJsonRandomly() { //untuk random
        String url = "https://api.myjson.com/bins/12dkdk"; //ambil url json
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("tf");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TrueFalseQuestion tf = new TrueFalseQuestion();
                                tf.setId(jsonObject.getInt("id"));
                                tf.setGroup_id(jsonObject.getString("group_id"));
                                tf.setcorrect_statement(jsonObject.getString("correct_statement"));
                                tf.setincorrect_statement(jsonObject.getString("incorrect_statement"));
                                tf.set_remark(jsonObject.getString("remark"));
                                tf.setDescription(jsonObject.getString("description"));
                                TrueFalseQuestion.add(tf);
                            }
                            Collections.shuffle(TrueFalseQuestion);
                            setQuestionToLayout(TrueFalseQuestion.get(0));
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
        TextView description = dialogView.findViewById(R.id.description);
        if (beenClicked.getText().toString().equals(TrueFalseQuestion.get(i).getcorrect_statement())) {
            value.setText("Benar");
            description.setText(null);
        } else if (beenClicked.getText().toString().equals(TrueFalseQuestion.get(i).get_remark())){
            value.setText("Salah");
            description.setText(TrueFalseQuestion.get(i).getDescription());
        }
        dialog.setPositiveButton("Selanjutnya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i++;
                setQuestionToLayout(TrueFalseQuestion.get(i));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
