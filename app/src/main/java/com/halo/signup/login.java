package com.halo.signup;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText eMail,pass;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eMail = findViewById(R.id.email);
        pass = findViewById(R.id.LOGOUT);
        fAuth = FirebaseAuth.getInstance();

    }

    public void homepage(View view) {

        String password=pass.getText().toString();
        String eM=eMail.getText().toString();

        if(TextUtils.isEmpty(password)) {
            pass.setError("Enter Password");
        }
        else
        if(TextUtils.isEmpty(eM)) {
            eMail.setError("Enter your E-Mail");
        }
        else {

            fAuth.signInWithEmailAndPassword(eM, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent hp = new Intent(login.this, HomePage.class);
                        startActivity(hp);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void signBtn(View view) {

        Intent sup= new Intent(login.this,Activity_SignUp.class);
        startActivity(sup);
        finish();
    }

    public void forget(View view) {
        final EditText resetMail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter your Mail Id to recieve a reset link");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(login.this, "Reset Link sent to your MailBox", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error! reset Link not sent."+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
}
