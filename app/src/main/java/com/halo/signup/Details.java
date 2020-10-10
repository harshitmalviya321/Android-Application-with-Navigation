package com.halo.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText lname,lphone,lage,lbloodGrp;
    //RadioGroup radioGroup;
    Button submit;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    //RadioButton lmale,lfemale,lother;
    //DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verify");
        builder.setMessage("Go to Mail Box to verify yourself");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent emailIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                startActivity(emailIntent);
            }
        });
        builder.show();



        lname = findViewById(R.id.name);
        lphone = findViewById(R.id.phone);
        lage = findViewById(R.id.age);
        lbloodGrp = findViewById(R.id.bloodGrp);
        submit = findViewById(R.id.submit);
        //lmale = findViewById(R.id.male);
        //lfemale = findViewById(R.id.female);
        //lother = findViewById(R.id.other);

        //dref = FirebaseDatabase.getInstance().getReference("details");

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = lname.getText().toString();
                String phone = lphone.getText().toString();
                String age = lage.getText().toString();
                String bloodGrp = lbloodGrp.getText().toString();
//                if (lmale.isSelected())
//                    gender = "male";
//                else
//                if (lfemale.isSelected())
//                    gender = "female";
//                else
//                if (lother.isSelected())
//                    gender = "other";

                userId = fAuth.getCurrentUser().getUid();

                DocumentReference documentReference = fstore.collection("details").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("name",name);
                user.put("phone",phone);
                user.put("age",age);
                user.put("bloodGrp",bloodGrp);
                //user.put("gender",gender);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Details added for "+userId);
                        Intent hp = new Intent(Details.this, HomePage.class);
                        startActivity(hp);
                        finish();
                    }
                });
/*
                Toast.makeText(Details.this, "Details Added", Toast.LENGTH_SHORT).show();
                Intent hp = new Intent(Details.this,Verify.class);
                startActivity(hp);
                finish();
*/
            }
        });

    }


}
