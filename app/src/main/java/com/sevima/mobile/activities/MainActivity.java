package com.sevima.mobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sevima.mobile.R;
import com.sevima.mobile.fragments.CourseFragment;
import com.sevima.mobile.fragments.NewsFragment;
import com.sevima.mobile.server.ApiClient;
import com.sevima.mobile.server.ServerAPI;

public class MainActivity extends AppCompatActivity {

    private ServerAPI serverAPI;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverAPI = ApiClient.getRetrofitInstance(this).create(ServerAPI.class);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.course_nav){
                if(bottomNavigationView.getSelectedItemId() != R.id.course_nav){
                    Fragment selectedFragment = new CourseFragment(serverAPI);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
            }else if(menuItem.getItemId() == R.id.news_nav){
                if(bottomNavigationView.getSelectedItemId() != R.id.news_nav){
                    Fragment selectedFragment = new NewsFragment(serverAPI);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
            }else if(menuItem.getItemId() == R.id.logout_nav){
                SharedPreferences sharedPreferences = getSharedPreferences("AUTH",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                Toast.makeText(getApplicationContext(), "Logout berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
            return true;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseFragment(serverAPI)).commit();
    }
}