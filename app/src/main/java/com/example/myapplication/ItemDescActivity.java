package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDescActivity extends AppCompatActivity {
    private Button AddtoCartbtn;
    private TextView nama, harga, desc, jumlahItem;
    private ImageView tambah, kurang, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desc);
        initView();
        getItem();
    }

    private void getItem() {
        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
//        Log.d("id_menu_item", "getItem: "+ getIntent().getIntExtra("id_menu", 0));
        Call<List<menu>> request = menuService.detailMenu(getIntent().getIntExtra("id_menu", 0));
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if (response.isSuccessful()) {
                    List menuList = response.body();
                    ArrayList<menu> menus = new ArrayList<menu>();
                    menus.addAll(menuList);
                    menu Menu = menus.get(0);
                    Log.d("item", "onResponse: " + Menu.getNama());
                    nama.setText(Menu.getNama());
                    harga.setText(String.valueOf(Menu.getHarga()));
                    jumlahItem.setText(String.valueOf(Menu.getStok()));
                    desc.setText(Menu.getDeskripsi());
                } else {
                    String error = apiResponse.readError(response.errorBody());
                    Log.d("errt", "" + error);
                    Toast.makeText(ItemDescActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<menu>> call, Throwable t) {
                Log.d("DataModel", "" + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView(){
        AddtoCartbtn = findViewById(R.id.AddToCartButton);
        nama = findViewById(R.id.NamaItem);
        harga = findViewById(R.id.HargaItem);
        desc = findViewById(R.id.DeskripsiItem);
        jumlahItem = findViewById(R.id.JumlahItem);
        tambah = findViewById(R.id.TambahJumlahItem);
        kurang = findViewById(R.id.KurangJumlahItem);
        gambar = findViewById(R.id.GambarItem);
    }

}