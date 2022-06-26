package com.sevima.mobile.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {

    @GET("get-course")
    Call<String> getCourse();

    @GET("get-news")
    Call<String> getNews();

    @FormUrlEncoded
    @POST("post-login-user")
    Call<String> postLoginUser(@Field("email") String email, @Field("password") String password);
}
