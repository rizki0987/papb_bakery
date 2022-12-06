package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_desc extends AppCompatActivity implements View.OnClickListener {

    menu Menu;
    EditText nama, harga, deskripsi;
    TextView jumlah;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_desc);
        nama = findViewById(R.id.NamaItem);
        harga = findViewById(R.id.HargaItem);
        jumlah = findViewById(R.id.JumlahItem);
        deskripsi = findViewById(R.id.DeskripsiItem);
        update = findViewById(R.id.Confirm);
        update.setOnClickListener(this);

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
                    jumlah.setText(String.valueOf(Menu.getStok()));
                    deskripsi.setText(Menu.getDeskripsi());
                } else {
                    String error = apiResponse.readError(response.errorBody());
                    Log.d("errt", "" + error);
                    Toast.makeText(update_desc.this, error, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<menu>> call, Throwable t) {
                Log.d("DataModel", "" + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Confirm){

        }
    }
}
