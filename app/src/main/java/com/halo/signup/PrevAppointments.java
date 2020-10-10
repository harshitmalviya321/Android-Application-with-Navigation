package com.halo.signup;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PrevAppointments extends AppCompatActivity {

    FirebaseFirestore fStore;
    CollectionReference collectionReference;
    ListView listView;
    String pName,drName,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_appointments);
        fStore = FirebaseFirestore.getInstance();
        collectionReference = fStore.collection("appointments");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    pName = documentSnapshot.getString("patient");
                    drName = documentSnapshot.getString("doctor");
                    date = documentSnapshot.getString("date");

                    addName(new String[]{drName},new String[] {date},new Integer[]{R.drawable.calendar});
                }
            }
        });


    }

    public void addName(String[] nameArray,String[] dateArray,Integer[] imageCalArray){
        CustomListAdaptor whatever = new CustomListAdaptor(this, nameArray, dateArray, imageCalArray);
        listView = findViewById(R.id.list);
        listView.setAdapter(whatever);

    }
}
