package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface menuService {
    @GET("menu")
    Call<List<menu>> listMenu();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("menu")
    Call<apiResponse> addMenu(@Body menu Menu);
}
