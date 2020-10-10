package com.halo.signup;

        import android.content.Intent;
        import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;

        import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class FlashActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout mDotLayout;
    private SlideAdapter myAdapter;
    FirebaseAuth fAuth;

    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        viewPager = findViewById(R.id.viewpager);
        mDotLayout = findViewById(R.id.mDotLayout);
        myAdapter = new SlideAdapter(this);
        viewPager.setAdapter(myAdapter);

        addDotsIndicator();

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser()!=null)
        {
            Intent hp = new Intent(FlashActivity.this, HomePage.class);
            startActivity(hp);
            finishAffinity();
        }
    }

    public void addDotsIndicator(){
        mDots=new TextView[2];
        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8266;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparent));

            mDotLayout.addView(mDots[i]);
        }
    }

}
