package com.example.yossi.firebaseproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnSignUp, btnLogIn, btnAdd;
    TextView tvUser;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    Dialog d;

    EditText etEmail,etPass;
    Button btnSign;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnAdd = findViewById(R.id.btnAdd);
        tvUser = findViewById(R.id.tvUser);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

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
            else if(v == btnLogIn)
                {

                    d = new Dialog(this);
                    d.setContentView(R.layout.signin_dialog);
                    d.setCancelable(true);

                    etEmail = d.findViewById(R.id.etEmail);
                    etPass = d.findViewById(R.id.etPass);
                    btnSign= d.findViewById(R.id.btnSign);
                    btnSign.setOnClickListener(this);
                    d.show();
                }
                else if(v == btnSign){

                    String mail,pass;
                    mail = etEmail.getText().toString();
                    pass = etPass.getText().toString();

                        if(!mail.equals("") && !pass.equals(""))
                        {
                            signIn(mail,pass);
                        }

                 }
                 else if(v == btnAdd) {
                    Intent intent = new Intent(this,AddQuestionActivity.class);
                    startActivity(intent);

        }

    }

    private void signIn(String mail, String pass) {

        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "התחברת בהצלחה!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            d.dismiss();
                            onStart();

                        } else {

                            Toast.makeText(MainActivity.this, "שם משתמש או סיסמא אינם מזוהים",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }


}
