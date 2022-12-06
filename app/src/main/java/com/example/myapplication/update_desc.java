package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_desc extends AppCompatActivity implements View.OnClickListener {

    menu Menu;
    EditText nama, harga, deskripsi;
    TextView jumlah;
    Button update;
    ArrayList<menu> menus;

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
        menus = new ArrayList<menu>();



        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
        Call<List<menu>> request = menuService.detailMenu(getIntent().getIntExtra("id_menu", 0));
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if (response.isSuccessful()) {
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
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userService userService = RetrofitClient.getClient("").create(userService.class);
            Call<user> req = userService.getId(user.getUid());
            req.enqueue(new Callback<user>() {
                @Override
                public void onResponse(Call<user> call, Response<user> response) {
                    if (response.isSuccessful()) {
                        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
                        menu Menu = new menu();
                        Menu.setNama(nama.getText().toString());
                        Menu.setHarga(Integer.parseInt(harga.getText().toString()));
                        Menu.setStok(Integer.parseInt(jumlah.getText().toString()));
                        Menu.setDeskripsi(deskripsi.getText().toString());
                        Menu.setId_jenis(menus.get(0).getId_jenis());
                        Menu.setId_user(response.body().getId_user());
                        Call<apiResponse> request = menuService.updateMenu(getIntent().getIntExtra("id_menu", 0), Menu);
                        request.enqueue(new Callback<apiResponse>() {
                            @Override
                            public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                                if (response.isSuccessful()) {
                                    apiResponse res = response.body();
                                    Toast.makeText(update_desc.this, res.getData(), Toast.LENGTH_SHORT).show();
                                    new Timer().scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            update_desc.this.finish();
                                        }
                                    }, 500, 500);
                                } else {
                                    String error = apiResponse.readError(response.errorBody());
                                    Log.d("errt", "" + error);
                                    Toast.makeText(update_desc.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<apiResponse> call, Throwable t) {
                                Log.d("DataModel", "" + t.getMessage());
                                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        String error = apiResponse.readError(response.errorBody());
                        Log.d("errt", "" + error);
                        Toast.makeText(update_desc.this, error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<user> call, Throwable t) {
                    Log.d("DataModel", "" + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
