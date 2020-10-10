package com.halo.signup;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images
    public int[] lst_images = {
            R.drawable.doctor,
            R.drawable.patient
    };
    // list of titles
    public String[] lst_title = {
            "DOCTOR",
            "PATIENT"
    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Login as doctor \nor ->","Login as Patient \n<- or"
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            Color.rgb( 107, 142, 183),
            Color.rgb( 107, 142, 183),
            //Color.rgb(53,55,55),
            //Color.rgb(239,85,85)
    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view== object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide =view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide =  view.findViewById(R.id.slideimg);
        final TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        imgslide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) txttitle.getText();
                if (text.equals("DOCTOR"))
                {
                    Intent drMain = new Intent(context,DrMainActivity.class);
                    context.startActivity(drMain);
                }
                else if (text.equals("PATIENT"))
                {
                    Intent main = new Intent(context,MainActivity.class);
                    context.startActivity(main);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
