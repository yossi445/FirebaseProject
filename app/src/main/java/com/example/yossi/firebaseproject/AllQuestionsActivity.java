package com.example.yossi.firebaseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllQuestionsActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Question> questionsList;
    AllQuestionsAdapter allQuestionsAdapter;

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);


        lv = findViewById(R.id.lv);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("questions");

        retrieveData();


    }

    private void retrieveData() {

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                questionsList = new ArrayList<>();

                for(DataSnapshot data: dataSnapshot.getChildren())
                {

                    Question q = data.getValue(Question.class);
                    questionsList.add(q);

                }

                allQuestionsAdapter = new AllQuestionsAdapter(AllQuestionsActivity.this,0,0,questionsList);
                lv.setAdapter(allQuestionsAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


    }
}
