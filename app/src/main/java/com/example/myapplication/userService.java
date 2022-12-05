package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface userService {
    @GET("user")
    Call<List<user>> listUser();

    @GET("user/{id}")
    Call<user> detailUser(@Path("id") int id);

    @POST("user/login")
    Call<apiResponse> login(@Body user User);

    @POST("user")
    Call<apiResponse> register(@Body user User);

    @DELETE("user/{id}")
    Call<apiResponse> deleteUser(@Path("id") int id);
}
