package com.sevima.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sevima.mobile.R;
import com.sevima.mobile.fragments.CourseFragment;
import com.sevima.mobile.fragments.NewsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.course_nav){
                if(bottomNavigationView.getSelectedItemId() != R.id.course_nav){
                    Fragment selectedFragment = new CourseFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
            }else if(menuItem.getItemId() == R.id.news_nav){
                if(bottomNavigationView.getSelectedItemId() != R.id.news_nav){
                    Fragment selectedFragment = new NewsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
            }else if(menuItem.getItemId() == R.id.logout_nav){
                //
            }
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseFragment()).commit();
    }
}