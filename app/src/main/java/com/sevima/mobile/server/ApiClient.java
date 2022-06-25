package com.sevima.mobile.server;

import android.content.Context;

import com.sevima.mobile.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(Context context) {
        if(retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + context.getString(R.string.api_key))
                        .build();
                return chain.proceed(newRequest);
            }).connectTimeout(0, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS)
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Config.BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
