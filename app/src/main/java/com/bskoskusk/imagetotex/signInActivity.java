package com.bskoskusk.imagetotex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bskoskusk.imagetotex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signInActivity extends AppCompatActivity {

    TextView tVtoast;
    TextView tVCreate;
    ImageView iVNext;
    //fireBaseDetails
    EditText eMail;
    EditText passWord;
    //EditText userName;
    //EditText phoneNo;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        //fireBaseDetails
        //fireBase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        eMail = findViewById(R.id.userNameStylish);
        passWord = findViewById(R.id.passwordStylish);
        //userName = findViewById(R.id.editTextUserName);
        //phoneNo = findViewById(R.id.editTextPhone);
        btnSignIn = findViewById(R.id.buttonSignIn);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(signInActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                    Intent N = new Intent(signInActivity.this,documentSelectionActivity.class);
                    startActivity(N);
                }
                else{
                    Toast.makeText(signInActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get in string
                String email = eMail.getText().toString().trim();
                String pass = passWord.getText().toString().trim();
                //String user = userName.getText().toString().trim();
                //String phone = phoneNo.getText().toString().trim();

                if (email.isEmpty()){
                    eMail.setError("Please enter your email");
                    eMail.requestFocus();
                }
                else if (pass.isEmpty()){
                    passWord.setError("Please enter your password");
                    passWord.requestFocus();
                }
                /*
                else if (user.isEmpty()){
                    userName.setError("Please enter your password");
                    userName.requestFocus();
                }
                else if (phone.isEmpty()){
                    phoneNo.setError("Please enter your password");
                    phoneNo.requestFocus();
                }

                 */
                else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(signInActivity.this,"Please fill out user & password fields!",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty() )){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(signInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(signInActivity.this,"Login error, Please login again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent H = new Intent(signInActivity.this,documentSelectionActivity.class);
                                startActivity(H);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(signInActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //tVtoast = findViewById(R.id.textViewHelp);

        //iVNext = findViewById(R.id.imageViewNext);
        /*
        iVNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent test = new Intent(signInActivity.this,documentSelectionActivity.class);
                startActivity(test);
            }
        });
        */
        tVCreate = findViewById(R.id.textViewCreateAccount);
        tVCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(signInActivity.this, com.bskoskusk.imagetotex.signUpActivity.class);
                startActivity(signUpActivity);
            }
        });
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

 */
    

}