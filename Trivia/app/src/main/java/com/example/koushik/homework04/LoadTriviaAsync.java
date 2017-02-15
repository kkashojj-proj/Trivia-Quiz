package com.example.koushik.homework04;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by KOUSHIK on 10-02-2017.
 */

public class LoadTriviaAsync extends AsyncTask<String,Void,ArrayList<Question>> {
    IData activity;

    public LoadTriviaAsync(IData activity) {
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        activity.stopProgressBar(questions);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        HttpURLConnection con=null;
        BufferedReader reader=null;
        try {
            URL url=new URL(strings[0]);
            try {
                con=(HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = "";
                StringBuilder sb=new StringBuilder();
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    Log.d("Line: " , ""+line);

                }
                ArrayList<Question> questions=new ArrayList<Question>();
                questions=new JSONParserTrivia().parserJSON(sb.toString());
                return questions;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
