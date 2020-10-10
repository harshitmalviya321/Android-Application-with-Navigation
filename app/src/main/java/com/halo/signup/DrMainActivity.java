package com.halo.signup;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DrMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_activity_main);
        Thread t = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try{
                    Thread.sleep(2500);
                    Intent gtlp=new Intent(DrMainActivity.this, DrActivity_SignUp.class);
                    startActivity(gtlp);
                    finishAffinity();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
