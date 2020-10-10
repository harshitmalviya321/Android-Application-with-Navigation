package com.halo.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_SignUp extends AppCompatActivity {

    EditText pass,cPass,eMail;
    Button signUpbtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pass = findViewById(R.id.LOGOUT);
        cPass = findViewById(R.id.cpass);
        eMail =  findViewById(R.id.email);
        signUpbtn= findViewById(R.id.btn_signUp);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!=null)
        {
            Intent hp = new Intent(Activity_SignUp.this, HomePage.class);
            startActivity(hp);
            finishAffinity();
        }

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = pass.getText().toString();
                String cpassword = cPass.getText().toString();
                String eM = eMail.getText().toString();
                if (TextUtils.isEmpty(eM)) {
                    eMail.setError("Enter your E-Mail");
                } else if (TextUtils.isEmpty(password)) {
                    pass.setError("Enter Password");
                } else if (TextUtils.isEmpty(cpassword)) {
                    cPass.setError("Enter Same Password");
                } else
                if (cPass.getText().toString().equals(pass.getText().toString()))
                {
                    fAuth.createUserWithEmailAndPassword(eM, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fUser = fAuth.getCurrentUser();
                                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity_SignUp.this, "Verification link has been sent to your Mail", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                                    }
                                });


                                Toast.makeText(Activity_SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                Intent details = new Intent(Activity_SignUp.this, Details.class);
                                startActivity(details);
                                finishAffinity();
                            }
                            else
                            {
                                Toast.makeText(Activity_SignUp.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Activity_SignUp.this, "Enter your Credentials again. Something went wrong!", Toast.LENGTH_SHORT).show();
                    eMail.setText("");
                    pass.setText("");
                    cPass.setText("");
                }

            }
        });

    }

    public void loginPage(View view) {
        Intent lp=new Intent(Activity_SignUp.this,login.class);
        startActivity(lp);
        finishAffinity();
    }
}
