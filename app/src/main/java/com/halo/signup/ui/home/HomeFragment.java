package com.halo.signup.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.halo.signup.R;
import com.synnapps.carouselview.CarouselView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment<HomeViewModel> extends Fragment {

    private ViewPager viewPager;
    private HomeDesign myAdapter;

    Timer timer;
    TextView content;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId,name,phone,age,bloodgrp;
    CarouselView customCarouselView;




    //int[] sampleImages = {R.drawable.d, R.drawable.c, R.drawable.a, R.drawable.b, R.drawable.am};
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image2&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image3&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);


        viewPager = root.findViewById(R.id.viewpager);
        myAdapter = new HomeDesign(getContext());
        viewPager.setAdapter(myAdapter);


        View.OnClickListener pauseOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCarouselView.reSetSlideInterval(0);
            }
        };
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%3);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3500, 3500);


//        collectionReference = fStore.collection("details");
//        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                String data = "";
//                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    DataItems dataItems = documentSnapshot.toObject(DataItems.class);
//
//                    String name = dataItems.getName();
//                    String phone = dataItems.getPhone();
//                    String age = dataItems.getAge();
//                    String bloodGrp = dataItems.getBloodGrp();
//                    String gender = dataItems.getGender();
//
//                    data += "Hey " + name + "! \n\nWelcome to EMERGENCY MEDICAL SERVICES. Using our application, you can locate doctors and appoint them.\nWhen you appoint a doctor, your confirmation will be sent to your contact no.: " + phone + "\n\nYour details are as follows: \n\nAge :" + age + "\n\nGender : " + gender + "\n\nBlood Group : " + bloodGrp;
//                }
//                content.setText(data);
//            }
//        });





        //content.setText(String.format("Hey %s! \n\nWelcome to EMERGENCY MEDICAL SERVICES. Using our application, you can locate doctors and appoint them.\nWhen you appoint a doctor, your confirmation will be sent to your contact no.: %s\n\nYour details are as follows: \n\nAge :%s\n\nGender : %s\n\nBlood Group : %s", name, phone, age, gender, bloodgrp));


//        back = getView().findViewById(R.id.back);
//        viewPager = getView().findViewById(R.id.viewpager);
//        mDotLayout = getView().findViewById(R.id.mDotLayout);
//        viewPager.setAdapter(new SlideAdapter(getActivity());
//
//        addDotsIndicator();

        return root;


    }

//    public void addDotsIndicator(){
//       mDots=new TextView[2];
//         for(int i=0;i<mDots.length;i++){
//            mDots[i]=new TextView(getActivity());
//            mDots[i].setText(Html.fromHtml("&#8266;"));
//            mDots[i].setTextSize(35);
//            mDots[i].setTextColor(getResources().getColor(R.color.transparent));
//
//            mDotLayout.addView(mDots[i]);
//        }
//    }



}