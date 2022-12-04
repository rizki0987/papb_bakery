package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface menuService {
    @GET("menu")
    Call<List<menu>> listMenu();

    @POST("menu")
    Call<apiResponse> addMenu(@Body menu Menu);

    @DELETE("menu/{id}")
    Call<apiResponse> deletMenu(@Path("id") int id);
}
