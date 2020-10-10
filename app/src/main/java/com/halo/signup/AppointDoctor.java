/**
 * Copyright (c) 2016, Diego Bezerra <diego.bezerra@gmail.com>
 * Permission to use, copy, modify, and/or distribute this software for any purpose
 * with or without fee is hereby granted, provided that the above copyright notice
 * and this permission notice appear in all copies.
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
 * REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT,
 * OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS
 * ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 **/
package com.halo.signup;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppointDoctor extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ExpandingList mExpandingList;
    FirebaseAuth fAuth;
    String date,t;
    FirebaseFirestore fStore;
    CollectionReference collectionReference;
    String drName, drPhone,exp,specs,quali,userId,name,phone,bloodgrp,age;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appoint_doctor);
        mExpandingList = findViewById(R.id.expanding_list_main);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
        DocumentReference documentReference = fStore.collection("details").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    name = task.getResult().getString("name");
                    phone = task.getResult().getString("phone");
                    bloodgrp = task.getResult().getString("bloodGrp");
                    age = task.getResult().getString("age");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AppointDoctor.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: document not exist"+e.getMessage());
            }
        });

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

                    addItem(drName,new String[]{drPhone,quali,exp,specs},R.color.colorPrimary,R.drawable.ic_ghost);
                }

            }
        });

       // createItems();
    }

//    private void createItems() {
//        addItem("John", new String[]{"House", "Boat", "Candy", "Collection", "Sport", "Ball", "Head"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Mary", new String[]{"Dog", "Horse", "Boat"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Ana", new String[]{"Cat"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Peter", new String[]{"Parrot", "Elephant", "Coffee"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Joseph", new String[]{}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Paul", new String[]{"Golf", "Football"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Larry", new String[]{"Ferrari", "Mazda", "Honda", "Toyota", "Fiat"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Moe", new String[]{"Beans", "Rice", "Meat"}, R.color.colorPrimary, R.drawable.ic_ghost);
//        addItem("Bart", new String[]{"Hamburger", "Ice cream", "Candy"}, R.color.colorPrimary, R.drawable.ic_ghost);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.title)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }
            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
//                    showInsertDialog(new OnItemCreated() {
//                        @Override
//                        public void itemCreated(String title) {
//                            View newSubItem = item.createSubItem();
//                            configureSubItem(item, newSubItem, title);
//                        }
//                    });
                    t = (String) ((TextView) item.findViewById(R.id.title)).getText();
                    showDatePickerDialog();

                }
            });


        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configureSubItem(final ExpandingItem item, final View view, final String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.sub_title).setTooltipText(subTitle);
        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //item.removeSubItem(view);
//                Intent i1=new Intent();
//                i1.setAction(Intent.ACTION_CALL);
//                i1.setData(Uri.parse("tel:7987505896"));
//                startActivity(i1);
            }
        });
    }


    private void showInsertDialog(final OnItemCreated positive) {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(text);
        builder.setTitle("Enter Title");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = dayOfMonth + "/" + ++month + "/" + year;
        fStore.collection("drDetails")
                .whereEqualTo("name", t)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String number = document.getString("phone");
                                Log.i("Send SMS", "");
                                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                                smsIntent.setData(Uri.parse("smsto:"));
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.putExtra("address"  , number);
                                smsIntent.putExtra("sms_body"  , " Dear "+ t +",\n\nA Patient:"+ name +" has appointed you for date : "
                                        + date+". Contact him/her to asure the appointment.\n\nIgnore if already done.");

                                try {
                                    startActivity(smsIntent);
                                    Log.i("Finished sending SMS...", "");

                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(AppointDoctor.this,
                                            "SMS faild, please try again later."+ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                try {
                                    DocumentReference documentReference = fStore.collection("appointments").document(userId);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("patient",name);
                                    user.put("doctor",drName);
                                    user.put("date",date);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess:Appointed "+userId);
                                        }
                                    });
                                } catch (Exception e){
                                    Toast.makeText(AppointDoctor.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onFailure: document not exist"+e.getMessage());
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    interface OnItemCreated {
        void itemCreated(String title);
    }
}
