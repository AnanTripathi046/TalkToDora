package com.project.talktodora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomAdapter customAdapter;
    LinearLayout linearLayout;
    List<ImageView> imageViewList=new ArrayList<>();
    MaterialButton skip,getStarted;
    View.OnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.dotcontainer);
        viewPager=findViewById(R.id.viewpager);
        customAdapter=new CustomAdapter(this);
        skip=findViewById(R.id.skip);
        getStarted=findViewById(R.id.getStarted);
        setlistener();
        initateViewPager();
        makethedots();
    }

    private void setlistener() {
        onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        };
        skip.setOnClickListener(onClickListener);
        getStarted.setOnClickListener(onClickListener);
    }

    private void makethedots() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        param.setMargins(5,0,5,0);
        for(int i=0;i<4;i++){
            ImageView imageView=new ImageView(this);
            if(i==0)
            imageView.setImageResource(R.drawable.active_dot);
            else
                imageView.setImageResource(R.drawable.inactive_dots);
            imageView.setLayoutParams(param);
            linearLayout.addView(imageView);
            imageViewList.add(imageView);
        }
    }

    private void initateViewPager() {
        viewPager.setAdapter(customAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    for (ImageView iv : imageViewList) {
                        iv.setImageResource(R.drawable.inactive_dots);
                    }
                    imageViewList.get(position).setImageResource(R.drawable.active_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}