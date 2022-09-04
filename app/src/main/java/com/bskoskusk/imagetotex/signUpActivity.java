package com.bskoskusk.imagetotex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bskoskusk.imagetotex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {

    EditText eMail;
    EditText passWord;
    EditText userName;
    EditText phoneNo;
    Button btnSignUp;
    Button btnSignInNew;
    ImageView iVback;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        btnSignInNew = findViewById(R.id.buttonSignInFromSignUp);
        btnSignInNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //fireBase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        eMail = findViewById(R.id.editTextMail);
        passWord = findViewById(R.id.editTextPassword);
        userName = findViewById(R.id.editTextUserName);
        phoneNo = findViewById(R.id.editTextPhone);
        btnSignUp = findViewById(R.id.buttonSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get in string
                String email = eMail.getText().toString().trim();
                String pass = passWord.getText().toString().trim();
                String user = userName.getText().toString().trim();
                String phone = phoneNo.getText().toString().trim();

                if (email.isEmpty()){
                    eMail.setError("Email is Required!");
                    eMail.requestFocus();
                }
                else if (pass.isEmpty()){
                    passWord.setError("Password is required!");
                    passWord.requestFocus();
                }
                else if (user.isEmpty()){
                    userName.setError("Username required!");
                    userName.requestFocus();
                }
                else if (phone.isEmpty()){
                    phoneNo.setError("Please enter your password");
                    phoneNo.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty() && user.isEmpty() && phone.isEmpty()){
                    Toast.makeText(signUpActivity.this,"Registration can not be done this way!",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty() && user.isEmpty() && phone.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(signUpActivity.this,"Sign up Unsuccessful, Try again!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(signUpActivity.this,documentSelectionActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(signUpActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}