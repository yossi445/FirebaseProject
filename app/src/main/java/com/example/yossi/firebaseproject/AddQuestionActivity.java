package com.example.yossi.firebaseproject;

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

public class AddQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etTitle, etBody;
    Button btnAdd;

    FirebaseDatabase database;
    Member m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        etTitle =findViewById(R.id.etTitle);
        etBody =findViewById(R.id.etBody);

        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();

    }

    @Override
    public void onClick(View v) {

        String uid = FirebaseAuth.getInstance().getUid().toString();
        Question q = new Question("",etTitle.getText().toString(),etBody.getText().toString(),uid);

        final DatabaseReference questionRef = database.getReference("questions").push();
        q.questionId = questionRef.getKey();
        questionRef.setValue(q);

        //----------------------------

        final DatabaseReference memberRef = database.getReference("members").child(uid);

        memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 m = dataSnapshot.getValue(Member.class);
                 m.questionsCounter++;
                 memberRef.setValue(m);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        finish();

    }
}
