package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface userService {
    @GET("user")
    Call<List<user>> listUser();

    @POST("user/login")
    Call<apiResponse> login(@Body user User);

    @POST("user")
    Call<apiResponse> register(@Body user User);

}
