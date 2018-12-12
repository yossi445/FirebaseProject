package com.example.yossi.firebaseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etContent;
    Button btnSave;

    FirebaseDatabase database;
    DatabaseReference ansRef;

    Answer a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_answer);

        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


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
                etContent.setText(a.content);


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {


        a.content = etContent.getText().toString();
        ansRef.setValue(a);
        finish();



    }
}
