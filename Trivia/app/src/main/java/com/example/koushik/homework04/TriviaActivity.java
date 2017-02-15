package com.example.koushik.homework04;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.koushik.homework04.R.id.imageView;

public class TriviaActivity extends AppCompatActivity{
    Button btn_prev,btn_next;
    ImageView iv;
    ScrollView sv;
    LinearLayout ll;
    ProgressBar pb;
    RadioGroup rg;
    TextView tv_q_num,tv_question,tv_timeleft,tv_loadimg;
    ArrayList<Question> questions=null;
    int question_counter,x=0;
    int[] answers={-1};
    String[] answerText={""};
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        tv_loadimg= (TextView) findViewById(R.id.tv_loadimg);
        btn_prev= (Button) findViewById(R.id.btn_prev);
        btn_next= (Button) findViewById(R.id.btn_next);
        sv= (ScrollView) findViewById(R.id.scrollview);
        ll= (LinearLayout) findViewById(R.id.linearLayout);
        iv= (ImageView) findViewById(imageView);
        tv_q_num= (TextView) findViewById(R.id.tv_q_num);
        tv_question= (TextView) findViewById(R.id.tv_question);
        tv_timeleft= (TextView) findViewById(R.id.tv_timeleft);
        questions=new ArrayList<Question>();
        pb= (ProgressBar) findViewById(R.id.progressBar);


        questions= (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS");
        Log.d("demo",questions.get(0).id+"");
        answers=new int[questions.size()];
        answerText=new String[questions.size()];
        LayoutGenerator(questions.get(question_counter));
        timer= new CountDownTimer(120000,1000){
            @Override
            public void onTick(long l) {
                tv_timeleft.setText(TimeUnit.MILLISECONDS.toSeconds(l)+" seconds");
            }

            @Override
            public void onFinish() {
                finishTrivia();
            }
        }.start();

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageBitmap(null);
                if(question_counter>0){
                    question_counter--;
                    LayoutGenerator(questions.get(question_counter));
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageBitmap(null);
                if(question_counter==questions.size()-1){
                    Toast.makeText(TriviaActivity.this,"Thank you for taking Trivia Quiz", Toast.LENGTH_SHORT).show();
                    finishTrivia();
                }else{
                    question_counter++;
                    LayoutGenerator(questions.get(question_counter));
                }
            }
        });

    }

    private void finishTrivia() {
        timer.cancel();
        finish();
        Intent i =new Intent(TriviaActivity.this,ResultActivity.class);
        i.putExtra("ANSWERS",answers);
        i.putExtra("QUESTIONS_TRIVIA",questions);
        i.putExtra("ANSWERTEXT",answerText);
        startActivity(i);

    }

    private void LayoutGenerator(final Question question) {
        pb.setVisibility(View.VISIBLE);
        tv_loadimg.setVisibility(View.VISIBLE);
        if(x>0){
            rg.removeAllViews();
        }

        if(question.id==0){
            btn_prev.setEnabled(false);
        }else{
            btn_prev.setEnabled(true);
        }
        if(question.id==questions.size()-1){
            btn_next.setText("Finish");
        }else{
            btn_next.setText("Next");
        }

        tv_q_num.setText("Q"+(1+question.id));
        tv_question.setText(question.text);
        rg=new RadioGroup(this);
        RadioButton[] rb=new RadioButton[question.getChoices().length];
        for(int i=0;i<question.getChoices().length;i++){
            rb[i]=new RadioButton(this);
            rb[i].setId((100*question_counter)+i+1);
            rb[i].setText(question.getChoices()[i]);

            rg.removeView(rb[i]);
            rg.addView(rb[i]);
            rg.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout)ll).removeView(rg);
            ((LinearLayout)ll).addView(rg);
        }
        rg.check(answers[question_counter]);
        Log.d("demo",answers[question_counter]+"            Question 1"+question_counter);
        x++;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup radioGroup, int i) {
                  answers[question_counter]=i;
                  answerText[question_counter]= ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                  Log.d("demo",i+"");
              }
        });

        if(question.image!=null){
            Picasso.with(TriviaActivity.this)
                    .load(questions.get(question_counter).image)
                    .into(iv);
            pb.setVisibility(View.INVISIBLE);
            tv_loadimg.setVisibility(View.INVISIBLE);
        }
    }
}
