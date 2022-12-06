package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvMenu;
    menuAdapter adapter;
    Intent intent;
    ImageView insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        ImageView btn = findViewById(R.id.imageView16);
        btn.setOnClickListener(this);
        rvMenu = findViewById(R.id.rvMenu);
        rvMenu.setNestedScrollingEnabled(false);
        insert = findViewById(R.id.imageView17);
        insert.setOnClickListener(this);
        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
        Call<List<menu>> request = menuService.listMenu();
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if(response.isSuccessful()){
                    List<menu> list = response.body();
                    Log.d("success", "list " + list.size());
                    adapter = new menuAdapter(homeActivity.this, list, new menuAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(menu Menu) {
                            intent = new Intent(homeActivity.this, ItemDescActivity.class);
                            intent.putExtra("id_menu", Menu.getId_menu());
//                            Log.d("id_menu", "onItemClick: " + Menu.getId_menu());
                            startActivity(intent);
                        }
                    });
                    rvMenu.setAdapter(adapter);
                    rvMenu.setLayoutManager(new LinearLayoutManager(homeActivity.this));
                }else {
                    String error = apiResponse.readError(response.errorBody());
                    Log.d("errt", "" + error);
                    Toast.makeText(homeActivity.this, error, Toast.LENGTH_SHORT).show();
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
    protected void onPostResume() {
        super.onPostResume();
        menuService menuService = RetrofitClient.getClient("").create(menuService.class);
        Call<List<menu>> request = menuService.listMenu();
        request.enqueue(new Callback<List<menu>>() {
            @Override
            public void onResponse(Call<List<menu>> call, Response<List<menu>> response) {
                if(response.isSuccessful()){
                    List<menu> list = response.body();
                    Log.d("success", "list " + list.size());
                    adapter = new menuAdapter(homeActivity.this, list, new menuAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(menu Menu) {
                            intent = new Intent(homeActivity.this, ItemDescActivity.class);
                            intent.putExtra("id_menu", Menu.getId_menu());
//                            Log.d("id_menu", "onItemClick: " + Menu.getId_menu());
                            startActivity(intent);
                        }
                    });
                    rvMenu.setAdapter(adapter);
                    rvMenu.setLayoutManager(new LinearLayoutManager(homeActivity.this));
                }else {
                    String error = apiResponse.readError(response.errorBody());
                    Log.d("errt", "" + error);
                    Toast.makeText(homeActivity.this, error, Toast.LENGTH_SHORT).show();
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
        if(view.getId() == R.id.imageView16){
            FirebaseAuth.getInstance().signOut();
            this.finish();
        } else if (view.getId() == R.id.imageView17){
            intent = new Intent(this, addActivity.class);
            startActivity(intent);
        }
    }
}