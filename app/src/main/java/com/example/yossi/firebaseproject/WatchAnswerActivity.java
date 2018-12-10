package com.example.yossi.firebaseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WatchAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvAns,tvLikes;
    Button btnLike;

    FirebaseDatabase database;
    DatabaseReference ansRef;

    Answer a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_answer);

        tvAns = findViewById(R.id.tvAns);
        tvLikes = findViewById(R.id.tvLikes);

        btnLike = findViewById(R.id.btnLike);
        btnLike.setOnClickListener(this);

        Intent intent = getIntent();
        String answerId = intent.getExtras().getString("answerId");
        String questionId = intent.getExtras().getString("questionId");


        database = FirebaseDatabase.getInstance();
        ansRef = database.getReference("answers").child(questionId).child(answerId);

        retriveData();


    }

    private void retriveData() {

        ansRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                a = dataSnapshot.getValue(Answer.class);
                tvAns.setText(a.content);
                tvLikes.setText(a.likes + " אהבו את התשובה");

                if(!a.userId.equals(FirebaseAuth.getInstance().getUid().toString()))
                    btnLike.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        a.likes++;
        ansRef.setValue(a);

    }


}
