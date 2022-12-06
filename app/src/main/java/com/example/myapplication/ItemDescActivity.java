package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDescActivity extends AppCompatActivity implements View.OnClickListener {
    Button updateMenubtn, hapus;
    private TextView nama, harga, desc, jumlahItem;
    private ImageView tambah, kurang, gambar;
    Intent intent;
    menu Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desc);
        initView();
        getItem();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
        Call<List<menu>> request = menuService.detailMenu(getIntent().getIntExtra("id_menu", 0));
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if (response.isSuccessful()) {
                    ArrayList<menu> menus = new ArrayList<menu>();
                    menus.addAll(response.body());
                    Menu = menus.get(0);
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

    private void getItem() {
        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
        Call<List<menu>> request = menuService.detailMenu(getIntent().getIntExtra("id_menu", 0));
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if (response.isSuccessful()) {
                    ArrayList<menu> menus = new ArrayList<menu>();
                    menus.addAll(response.body());
                    Menu = menus.get(0);
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
        updateMenubtn = findViewById(R.id.confirm);
        updateMenubtn.setOnClickListener(this);
        nama = findViewById(R.id.NamaItem);
        harga = findViewById(R.id.HargaItem);
        desc = findViewById(R.id.DeskripsiItem);
        jumlahItem = findViewById(R.id.JumlahItem);
//        tambah = findViewById(R.id.TambahJumlahItem);
//        kurang = findViewById(R.id.KurangJumlahItem);
        gambar = findViewById(R.id.GambarItem);
        hapus = findViewById(R.id.delete);
        hapus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.confirm){
            intent = new Intent(this, update_desc.class);
            intent.putExtra("id_menu", Menu.getId_menu());
            startActivity(intent);
        } else if (view.getId() == R.id.delete) {
            menuService menuService = RetrofitClient.getClient("").create(menuService.class);
            Call<apiResponse> request = menuService.deleteMenu(getIntent().getIntExtra("id_menu", 0));
            request.enqueue(new Callback<apiResponse>() {
                @Override
                public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                    if (response.isSuccessful()) {
                        apiResponse res = response.body();
                        Toast.makeText(ItemDescActivity.this, res.getData(), Toast.LENGTH_SHORT).show();
                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                ItemDescActivity.this.finish();
                            }
                        }, 500, 500);
                    } else {
                        String error = apiResponse.readError(response.errorBody());
                        Log.d("errt", "" + error);
                        Toast.makeText(ItemDescActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<apiResponse> call, Throwable t) {
                    Log.d("DataModel", "" + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}