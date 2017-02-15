package com.example.koushik.homework04;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KOUSHIK on 10-02-2017.
 */

public class JSONParserTrivia {

    public ArrayList<Question> parserJSON(String s) throws JSONException {

        ArrayList<Question> questions=new ArrayList<Question>();
        JSONObject root=new JSONObject(s);
        JSONArray ques_array=root.getJSONArray("questions");

        for(int i=0;i<ques_array.length();i++){
            Question q=new Question();
            JSONObject quesObject=ques_array.getJSONObject(i);

            if(quesObject.has("id")){
            q.setId(Integer.parseInt(quesObject.getString("id")));
            }
            if(quesObject.has("image")){
                q.setImage(quesObject.getString("image"));
            }
            if(quesObject.has("text")){
                q.setText(quesObject.getString("text"));
            }

            if(quesObject.has("choices")){
                JSONObject temp1=quesObject.getJSONObject("choices");
                JSONArray temp2=temp1.getJSONArray("choice");
                String[] choices=new String[temp2.length()];
                for(int j=0;j<temp2.length();j++){
                    choices[j]=temp2.getString(j);
                }
                q.setChoices(choices);
                if(temp1.has("answer")){
                    q.setAnswer(Integer.parseInt(temp1.getString("answer")));
                    Log.d("demo",i+"");
                }
            }
            questions.add(q);
        }
        return questions;
    }

}
