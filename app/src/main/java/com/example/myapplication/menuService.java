package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface menuService {
    @GET("menu")
    Call<List<menu>> listMenu();

    @GET("menu/{id}")
    Call<menu> detailMenu(@Path("id") int id);

    @POST("menu")
    Call<apiResponse> addMenu(@Body menu Menu);

    @PUT("menu/{id}")
    Call<apiResponse> updateMenu(@Body menu Menu);

    @DELETE("menu/{id}")
    Call<apiResponse> deleteMenu(@Path("id") int id);
}
