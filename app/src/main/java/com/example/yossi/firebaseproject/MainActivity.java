package com.example.yossi.firebaseproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnSignUp, btnLogIn;
    TextView tvUser;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        tvUser = findViewById(R.id.tvUser);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }



    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null)
       {
           btnSignUp.setText("התנתקות");
           tvUser.setText(currentUser.getEmail().toString());
           btnLogIn.setVisibility(View.INVISIBLE);
       }

    }



    @Override
    public void onClick(View v) {

        if(v == btnSignUp) {
            if (btnSignUp.getText().equals("הרשמה")) {
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);


            }
            else if (btnSignUp.getText().equals("התנתקות")) {

                mAuth.signOut();
                btnSignUp.setText("הרשמה");
                tvUser.setText("שלום אורח!");
                btnLogIn.setVisibility(View.VISIBLE);
            }

            }

    }
}
