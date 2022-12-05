package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDescActivity extends AppCompatActivity {
    private Button AddtoCartbtn;
    private TextView nama, harga, desc, jumlahItem;
    private ImageView tambah, kurang, gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desc);
        initView();
        getBundle();
    }

    private void getBundle() {
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