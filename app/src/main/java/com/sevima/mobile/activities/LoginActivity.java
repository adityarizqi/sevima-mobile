package com.sevima.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.sevima.mobile.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.loginBtn).setOnClickListener(view -> {
            String email = ((EditText)findViewById(R.id.emailInput)).getText().toString();
            String password = ((EditText)findViewById(R.id.passwordInput)).getText().toString();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                login(email, password);
            }
        });
    }

    private void login(String email, String password) {
        
    }
}