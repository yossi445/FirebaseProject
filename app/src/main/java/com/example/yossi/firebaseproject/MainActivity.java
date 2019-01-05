package com.example.yossi.firebaseproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnSignUp, btnLogIn, btnAdd, btnAll;
    TextView tvUser;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    Dialog d;

    EditText etEmail,etPass;
    Button btnSign;
    //--------------
    ImageView iv;
    TextView tvUpdateProfilePic;

    StorageReference mStorageRef;
    DatabaseReference membersRef;
     DatabaseReference mRef;
    ProgressDialog progressDialog;

    Uri imageUri;
    String downloadUrl;

    Member m;


    static final int PICK_IMAGE_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);
        tvUpdateProfilePic = findViewById(R.id.tvUpdateProfilePic);
        tvUpdateProfilePic.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("profileImages");
        membersRef = FirebaseDatabase.getInstance().getReference("members");
        progressDialog = new ProgressDialog(this);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnAdd = findViewById(R.id.btnAdd);
        tvUser = findViewById(R.id.tvUser);
        btnAll = findViewById(R.id.btnAll);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnAll.setOnClickListener(this);

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
            else if(v == btnLogIn){



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
            else if(v == btnAll)
            {
                Intent intent = new Intent(this, AllQuestionsActivity.class);
                startActivity(intent);
            }
            else if(v == tvUpdateProfilePic)
            {
                openFileChooser();

            }

    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            uploadFile();
        }
    }

    private void uploadFile() {

        final String fileChildId = String.valueOf(System.currentTimeMillis());
        StorageReference fileRef = mStorageRef.child(fileChildId);
        progressDialog.setMessage("uploading image...");
        progressDialog.show();

        //uploading
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "upload complete", Toast.LENGTH_SHORT).show();


                //-------download


                mStorageRef.child(fileChildId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png' in uri
                        downloadUrl = uri.toString();
                        String memberId = FirebaseAuth.getInstance().getUid();
                        membersRef.child(memberId).child("profileImageUrl").setValue(downloadUrl);
                        Picasso.get().load(downloadUrl).into(iv);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

            }

        });



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
