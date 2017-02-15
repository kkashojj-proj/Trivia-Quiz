package com.example.koushik.homework04;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IData {
    ProgressBar pb;
    ImageView iv;
    Button btn_exit,btn_start;
    TextView tv_ready,tv_loading;
    ArrayList<Question> questions=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb= (ProgressBar) findViewById(R.id.pb_home);
        iv= (ImageView) findViewById(R.id.iv_trivia);
        btn_exit= (Button) findViewById(R.id.btn_exit);
        btn_start= (Button) findViewById(R.id.btn_start);
        questions=new ArrayList<Question>();
        tv_loading= (TextView) findViewById(R.id.tv_loading_trivia);
        tv_ready= (TextView) findViewById(R.id.tv_ready);
        questions=new ArrayList<Question>();
        CountDownTimer timer;

        btn_start.setEnabled(false);
        iv.setVisibility(View.INVISIBLE);
        tv_ready.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);


        if(isConnectedOnline()){
            new LoadTriviaAsync(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TriviaActivity.class);
                if(questions!=null){
                    finish();
                    i.putExtra("QUESTIONS",questions);
                    startActivity(i);
                }

            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public Boolean isConnectedOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw=cm.getActiveNetworkInfo();
        if(nw!=null&&nw.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public void stopProgressBar(ArrayList<Question> questions) {
        pb.setVisibility(View.INVISIBLE);
        this.questions=questions;
        tv_ready.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.VISIBLE);
        btn_start.setEnabled(true);
    }
}
