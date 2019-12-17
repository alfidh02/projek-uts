package com.mind.loginregisterapps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Multiple_Choice extends AppCompatActivity implements View.OnClickListener{
    private RequestQueue requestQueue;
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();
    private TextView question,tvSignUp;
    private Button ansA, ansB, ansC;
    private int i = 0;
    int progressStatus = 0;
    ProgressBar progressBar;
    Handler handler = new Handler();
    private RelativeLayout rlayout;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice);
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
        question = findViewById(R.id.question);
        progressBar = findViewById(R.id.progressBar);
        tvSignUp = findViewById(R.id.tvSignUp);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);

        tvSignUp.setText("Multiple Choice");
        requestQueue = Volley.newRequestQueue(this);
        getQuestionFromJsonRandomly();
        new Thread(new Runnable() {
            public void run() {
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
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setQuestionToLayout(MultipleChoiceQuestion mcq) {
        question.setText(mcq.getQuestion());
        List<String> option = new ArrayList<>();
        option.add(mcq.getAnswer());
        option.add(mcq.getOption_1());
        option.add(mcq.getOption_2());
        Collections.shuffle(option);
        ansA.setText(option.get(0));
        ansB.setText(option.get(1));
        ansC.setText(option.get(2));
    }

    private void getQuestionFromJsonRandomly() {
        String url = "https://api.myjson.com/bins/10ckz6";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("mc");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
                                mcq.setId(jsonObject.getInt("id"));
                                mcq.setGroup_id(jsonObject.getString("group_id"));
                                mcq.setQuestion(jsonObject.getString("question"));
                                mcq.setAnswer(jsonObject.getString("answer"));
                                mcq.setOption_1(jsonObject.getString("option_1"));
                                mcq.setOption_2(jsonObject.getString("option_2"));
                                mcq.setDescription(jsonObject.getString("description"));
                                multipleChoiceQuestions.add(mcq);
                            }
                            Collections.shuffle(multipleChoiceQuestions);
                            setQuestionToLayout(multipleChoiceQuestions.get(0));
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
    public void onClick(View v) {
        Button beenClicked = (Button) v;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mc_description, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        TextView value = dialogView.findViewById(R.id.value);
        if (beenClicked.getText().toString().equals(multipleChoiceQuestions.get(i).getAnswer())) {
            value.setText("Benar");
        } else {
            value.setText("Salah");
        }
        TextView description = dialogView.findViewById(R.id.description);
        description.setText(multipleChoiceQuestions.get(i).getDescription());
        dialog.setPositiveButton("Selanjutnya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i++;
                setQuestionToLayout(multipleChoiceQuestions.get(i));
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
