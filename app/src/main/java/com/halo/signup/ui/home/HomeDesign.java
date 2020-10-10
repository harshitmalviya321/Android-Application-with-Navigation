package com.halo.signup.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.halo.signup.R;

import androidx.viewpager.widget.PagerAdapter;

public class HomeDesign extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images
    public int[] lst_images = {
            R.drawable.locatedoctor,
            R.drawable.previousappointment,
            R.drawable.appoint
    };
    // list of titles

    // list of descriptions

    // list of background colors



    public HomeDesign(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_images.length;
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
        TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);

        imgslide.setImageResource(lst_images[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
