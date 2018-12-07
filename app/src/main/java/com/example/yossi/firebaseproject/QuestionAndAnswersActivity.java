package com.example.yossi.firebaseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionAndAnswersActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle, tvBody;
    EditText etAns;
    Button btnAdd;
    ListView lvAnswers;

    FirebaseDatabase database;
    DatabaseReference ansRef;
    String qid;

    ArrayList<Answer> answersList;
    AllAnswerAdapter allAnswerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_and_answers);

        tvTitle = findViewById(R.id.tvTitle);
        tvBody = findViewById(R.id.tvBody);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        etAns = findViewById(R.id.etAns);

        lvAnswers = findViewById(R.id.lvAnswers);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String body = intent.getExtras().getString("body");
        qid = intent.getExtras().getString("qid");


        tvTitle.setText(title);
        tvBody.setText(body);

        database = FirebaseDatabase.getInstance();
        ansRef = database.getReference("answers").child(qid);


    }

    @Override
    protected void onStart() {
        super.onStart();


        // Read from the database
        ansRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                answersList = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Answer a = data.getValue(Answer.class);

                    answersList.add(a);

                }

                allAnswerAdapter = new AllAnswerAdapter(QuestionAndAnswersActivity.this, 0, 0, answersList);
                lvAnswers.setAdapter(allAnswerAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }



    @Override
    public void onClick(View v) {

        saveAnswer();

    }

    private void saveAnswer() {

        String content = etAns.getText().toString();

        if(!content.equals(""))
        {

            String aid = ansRef.push().getKey();
            String uid = FirebaseAuth.getInstance().getUid().toString();

            Answer a = new Answer(aid,content,qid,uid);
            ansRef.child(aid).setValue(a);

        }
        else
            Toast.makeText(this, "הזן תשובה", Toast.LENGTH_SHORT).show();
    }


}
