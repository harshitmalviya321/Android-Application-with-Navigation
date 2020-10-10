package com.halo.signup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdaptor extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final Integer[] imageCalArray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] dateArray;

    public CustomListAdaptor(Activity context, String[] nameArrayParam, String[] dateArrayParam, Integer[] imageCalArrayParam){

        super(context,R.layout.list_row , nameArrayParam);
        this.context=context;
        this.imageCalArray = imageCalArrayParam;
        this.nameArray = nameArrayParam;
        this.dateArray = dateArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.drName);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.date);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.calendar);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(dateArray[position]);
        imageView.setImageResource(imageCalArray[position]);

        return rowView;

    };




}
