package com.sevima.mobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.sevima.mobile.R;
import com.sevima.mobile.server.ApiClient;
import com.sevima.mobile.server.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ServerAPI serverAPI;

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

        if(getSharedPreferences("AUTH",MODE_PRIVATE).getString("email",null) != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

        serverAPI = ApiClient.getRetrofitInstance(this).create(ServerAPI.class);
    }

    private void login(String email, String password) {
        Call<String> call = serverAPI.postLoginUser(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body() != null){
                    try {
                        JSONObject object = new JSONObject(response.body());
                        Toast.makeText(LoginActivity.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                        if(object.getInt("status") == 200){
                            SharedPreferences sharedPreferences = getSharedPreferences("AUTH",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",email).apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}