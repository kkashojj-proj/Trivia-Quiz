package com.example.koushik.homework04;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int total,correct=0;

        int[] answers=getIntent().getIntArrayExtra("ANSWERS");

        ArrayList<Question> questions= (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS_TRIVIA");
        String[] answerText =getIntent().getStringArrayExtra("ANSWERTEXT");
        total=questions.size();


        for(int i=0;i<answerText.length;i++){
            if(answerText[i]!=(questions.get(i).choices[questions.get(i).answer-1])){
                LinearLayout ll= (LinearLayout) findViewById(R.id.linearLayout);
                TextView tv_q=new TextView(this);
                TextView tv_ur=new TextView(this);
                TextView tv_crct=new TextView(this);
                tv_q.setText(questions.get(i).text);
                if(answerText[i]!=null){
                    if(!answerText[i].equals(questions.get(i).choices[questions.get(i).answer-1])){
                        tv_ur.setText(answerText[i]);
                        tv_ur.setBackgroundColor(Color.RED);
                        tv_crct.setText((questions.get(i).choices[questions.get(i).answer-1]));
                        tv_q.setTextColor(Color.BLACK);
                        tv_q.setTypeface(null, Typeface.BOLD);
                        tv_ur.setTextColor(Color.BLACK);
                        tv_crct.setTextColor(Color.BLACK);
                        tv_crct.setTypeface(null,Typeface.ITALIC);
                        tv_crct.setPadding(0,0,0,10);
                        ll.addView(tv_q);
                        ll.addView(tv_ur);
                        ll.addView(tv_crct);
                    }
                }else{
                    tv_ur.setText("Not attempted");
                    tv_ur.setBackgroundColor(Color.RED);
                    tv_crct.setText((questions.get(i).choices[questions.get(i).answer-1]));
                    tv_q.setTextColor(Color.BLACK);
                    tv_q.setTypeface(null, Typeface.BOLD);
                    tv_ur.setTextColor(Color.BLACK);
                    tv_crct.setTextColor(Color.BLACK);
                    tv_crct.setTypeface(null,Typeface.ITALIC);
                    tv_crct.setPadding(0,0,0,10);
                    ll.addView(tv_q);
                    ll.addView(tv_ur);
                    ll.addView(tv_crct);
                }
        }}
        for(int i=0;i<answerText.length;i++){
            if(answerText[i]!=null){
                if(answerText[i].equals(questions.get(i).choices[questions.get(i).answer-1])){
                    LinearLayout ll= (LinearLayout) findViewById(R.id.linearLayout);
                    TextView tv_q=new TextView(this);
                    TextView tv_ur=new TextView(this);
                    TextView tv_crct=new TextView(this);
                    tv_q.setText(questions.get(i).text);
                    if(answerText[i]!=null){
                        tv_ur.setText(answerText[i]);
                    }else{
                        tv_ur.setText("Not attempted");
                    }
                    tv_ur.setBackgroundColor(Color.GREEN);
                    correct++;
                    tv_crct.setText((questions.get(i).choices[questions.get(i).answer-1]));
                    tv_q.setTextColor(Color.BLACK);
                    tv_q.setTypeface(null, Typeface.BOLD);
                    tv_ur.setTextColor(Color.BLACK);
                    tv_crct.setTextColor(Color.BLACK);
                    tv_crct.setTypeface(null,Typeface.ITALIC);
                    tv_crct.setPadding(0,0,0,10);
                    ll.addView(tv_q);
                    ll.addView(tv_ur);
                    ll.addView(tv_crct);
                }
            }
        }
        ProgressBar pb= (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(total);
        pb.setProgress(correct);
        TextView tv_percent= (TextView) findViewById(R.id.tv_percent);
        tv_percent.setText((correct*6.25)+"%");
        Button btn_finish= (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i=new Intent(ResultActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
