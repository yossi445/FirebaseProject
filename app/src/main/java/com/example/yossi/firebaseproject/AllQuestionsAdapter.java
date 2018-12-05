package com.example.yossi.firebaseproject;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllQuestionsAdapter extends ArrayAdapter<Question> {

    Context context;
    List<Question> questionsList;


    public AllQuestionsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Question> questionsList) {
        super(context, resource, textViewResourceId, questionsList);

        this.context = context;
        this.questionsList = questionsList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_question,parent,false);

        TextView tvTitle = view.findViewById(R.id.tvTitle);


        Question q = questionsList.get(position);

        tvTitle.setText(q.title);


        return view;

    }
}
