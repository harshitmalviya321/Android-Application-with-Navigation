package com.halo.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Verify extends AppCompatActivity {

    TextView text;
    Button verify;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        text = findViewById(R.id.text);
        verify = findViewById(R.id.verify);
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();

        if (!user.isEmailVerified())
        {
            text.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Verify.this, "Verification link has been sent to your Mail", Toast.LENGTH_SHORT).show();
                            Thread t = new Thread()
                            {
                                @Override
                                public void run() {
                                    super.run();
                                    try{
                                        Thread.sleep(5000);
                                        Intent gtlp=new Intent(Verify.this,HomePage.class);
                                        startActivity(gtlp);
                                        finish();
                                    }catch(InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            t.start();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }
        else
        {
            Intent hp = new Intent(Verify.this,HomePage.class);
            startActivity(hp);
            finish();
        }

    }
}
