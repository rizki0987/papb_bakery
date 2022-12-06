package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nama, harga, deskripsi;
    TextView stok;
    Button submit;
    ImageView plus, minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);
        nama = findViewById(R.id.NamaItem);
        harga = findViewById(R.id.HargaItem);
        stok = findViewById(R.id.JumlahItem);
        deskripsi = findViewById(R.id.DeskripsiItem);
        submit = findViewById(R.id.Confirm);
        submit.setOnClickListener(this);
        plus = findViewById(R.id.TambahJumlahItem);
        minus = findViewById(R.id.KurangJumlahItem);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Confirm){
            menu Menu = new menu();
            Menu.setId_jenis(1);
            Menu.setNama(nama.getText().toString());
            Log.d("insert", "onClick: " + Menu.getNama());
            Menu.setHarga(Integer.parseInt(harga.getText().toString()));
            Menu.setStok(Integer.parseInt(stok.getText().toString()));
            Log.d("insert", "onClick: " + Menu.getHarga());
            menuService menuService = RetrofitClient.getClient("").create(menuService.class);
            Log.d("insert", "onClick: " + Menu.getNama());
            Call<apiResponse> request = menuService.addMenu(Menu);
            request.enqueue(new Callback<apiResponse>() {
                @Override
                public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                    if (response.isSuccessful()) {
                        apiResponse res = response.body();
                        Toast.makeText(addActivity.this, res.getData(), Toast.LENGTH_SHORT).show();
                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                addActivity.this.finish();
                            }
                        }, 500, 500);
                    } else {
                        String error = apiResponse.readError(response.errorBody());
                        Log.d("errt", "" + error);
                        Toast.makeText(addActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<apiResponse> call, Throwable t) {
                    Log.d("DataModel", "" + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (view.getId() == R.id.TambahJumlahItem){
            stok.setText(String.valueOf(Integer.parseInt(stok.getText().toString()) + 1));
        } else if (view.getId() == R.id.KurangJumlahItem){
            if(Integer.parseInt(stok.getText().toString()) > 0){
                stok.setText(String.valueOf(Integer.parseInt(stok.getText().toString()) - 1));
            }
        }
    }
}
