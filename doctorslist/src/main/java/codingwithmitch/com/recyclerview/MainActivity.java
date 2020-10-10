package codingwithmitch.com.recyclerview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("http://www.rajasthanhospitals.org/uploads/doctors/59c4f49ac5acc.jpg");
        mNames.add("Dr.N.D.Khimesra");

        mImageUrls.add("http://www.rajasthanhospitals.org/uploads/doctors/59c4dd629e6d8.jpg");
        mNames.add("Dr. Bharat Shah");

        mImageUrls.add("http://www.rajasthanhospitals.org/uploads/doctors/5b248f5b507d6.jpg");
        mNames.add("Dr. Vimal Ranka");

        mImageUrls.add("http://www.rajasthanhospitals.org/uploads/doctors/59c50f3b0a957.jpg");
        mNames.add("Dr. K.D.Tibrewala");



        mImageUrls.add("https://media.istockphoto.com/photos/heroes-are-ordinary-people-who-make-themselves-extraordinary-picture-id537738697");
        mNames.add("DR.Mahahual");

        mImageUrls.add("https://media.istockphoto.com/photos/mature-handsome-italian-man-doctor-against-gray-background-picture-id874697160");
        mNames.add("DR.Frozen Lake");


        mImageUrls.add("https://media.istockphoto.com/photos/mature-handsome-italian-man-doctor-against-gray-background-picture-id874697160");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://media.istockphoto.com/photos/mature-handsome-italian-man-doctor-against-gray-background-picture-id874697160");
        mNames.add("DR.Austrailia");

        mImageUrls.add("https://media.istockphoto.com/photos/mature-handsome-italian-man-doctor-against-gray-background-picture-id874697160");
        mNames.add("DR.Washington");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}






















