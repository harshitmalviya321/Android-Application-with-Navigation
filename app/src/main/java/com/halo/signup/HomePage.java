package com.halo.signup;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.synnapps.carouselview.CarouselView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.halo.signup.Details.TAG;

//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;

public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    CarouselView customCarouselView;
    Dialog myDialog;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId,name,phone,age,bloodgrp;

    int[] sampleImages = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.am};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(HomePage.this,DoctorList.class);
                startActivity(chat);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout, R.id.nav_change_pass)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        myDialog = new Dialog(this);

        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
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
                Toast.makeText(HomePage.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: document not exist"+e.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case Menu.FIRST:
                Intent intent = getPackageManager().getLaunchIntentForPackage("codingwithmitch.com.recyclerview");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "codingwithmitch.com.recyclerview"));
                    startActivity(intent);
                }


                break;

            case Menu.FIRST+1:
                Intent sar=new Intent(HomePage.this,MapsActivity.class);
                startActivity(sar);

                break;

            case Menu.FIRST+2:
                Intent ad = new Intent(this,AppointDoctor.class);
                startActivity(ad);
                break;

            case Menu.FIRST+3:

                Toast.makeText(this,"This is Starred message",Toast.LENGTH_SHORT);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0,Menu.FIRST,Menu.NONE,"Previous Appointments");
        menu.add(0,Menu.FIRST+1,Menu.NONE,"Locate Doctors");
        menu.add(0,Menu.FIRST+2,Menu.NONE,"Appoint Doctor");

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void showPopup(View view){
        final TextView textClose,pName,pBlood,pContact,pAge;
        Button changePass;
        myDialog.setContentView(R.layout.custom_popup);
        textClose = myDialog.findViewById(R.id.cancel);
        changePass = myDialog.findViewById(R.id.LOGOUT);
        pName = myDialog.findViewById(R.id.name);
        pBlood = myDialog.findViewById(R.id.blood);
        pContact = myDialog.findViewById(R.id.phone);
        pAge = myDialog.findViewById(R.id.age);
        pName.setText("Name:"+name);
        pBlood.setText("Blood Group : "+bloodgrp);
        pContact.setText(phone);
        pAge.setText(age);
        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.getInstance().signOut();
                Intent closeAll = new Intent(HomePage.this, login.class);
                startActivity(closeAll);
                finishAffinity();
            }
        });
    }

}
