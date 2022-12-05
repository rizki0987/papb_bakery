package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface pesananService {
    @GET("pesanan")
    Call<List<pesanan>> listPesanan();

    @GET("pesanan/{id}")
    Call<pesanan> detailPesanan(@Path("id") int id);

    @POST("pesanan")
    Call<apiResponse> tambahPesanan(@Body pesanan Pesanan);

    @POST("pesanan/{id}")
    Call<apiResponse> tambahPesanMenu(@Path("id") int id, @Body int[] menu);

    @DELETE("pesanan/menu/{id}")
    Call<apiResponse> hapusPesanMenu(@Path("id") int id, @Body int id_menu);

    @PUT("pesanan/{id}")
    Call<apiResponse> bayarPesanan(@Path("id") int id);
}
