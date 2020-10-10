package com.halo.signup;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorList extends AppCompatActivity {
    Dialog myDialog;
    FirebaseFirestore fStore;
    CollectionReference collectionReference;
    String drName,drPhone,quali,exp,specs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        myDialog = new Dialog(this);
        fStore = FirebaseFirestore.getInstance();
        collectionReference = fStore.collection("drDetails");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    //DataItems dataItems = documentSnapshot.toObject(DataItems.class);

                    drName = documentSnapshot.getString("name");
                    drPhone = documentSnapshot.getString("phone");
                    quali = documentSnapshot.getString("qualification");
                    exp = documentSnapshot.getString("experience");
                    specs = documentSnapshot.getString("speciality");

                }

            }
        });

    }

    public void showPopup(View view) {
        myDialog.setContentView(R.layout.custom_popup);
    }
}