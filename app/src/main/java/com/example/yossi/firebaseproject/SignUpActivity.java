package com.example.yossi.firebaseproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail,etPass;
    Button btnSign;

    String mail,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnSign=findViewById(R.id.btnSign);

        btnSign.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        mail = etEmail.getText().toString();
        pass = etPass.getText().toString();

        if(!mail.equals("") && !pass.equals(""))
        {
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SignUpActivity.this, "נרשמת בהצלחה",
                                        Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(SignUpActivity.this, "ההרשמה נכשלה",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }




    }
}
