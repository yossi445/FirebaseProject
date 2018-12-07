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

import java.util.List;

public class AllAnswerAdapter extends ArrayAdapter<Answer> {

    Context context;
    List<Answer> answersList;


    public AllAnswerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Answer> answersList) {
        super(context, resource, textViewResourceId, answersList);

        this.context = context;
        this.answersList = answersList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.answer_custom,parent,false);

        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvLikes = view.findViewById(R.id.tvLikes);



        Answer a = answersList.get(position);

        tvContent.setText(a.content);
        tvLikes.setText(a.likes + " אהבו את התשובה");


        return view;

    }
}

