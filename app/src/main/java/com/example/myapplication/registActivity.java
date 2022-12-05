package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class registActivity extends AppCompatActivity {
    private Button regist, loginHere;
    private TextView nama, email, pws, confirmpws;
    private ImageView backToRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
    }

    private void initView(){
        backToRegist = findViewById(R.id.imageButton);
        regist = findViewById(R.id.button2);
        loginHere = findViewById(R.id.button);
        nama = findViewById(R.id.editTextTextEmailAddress2);
        email = findViewById(R.id.editTextTextEmailAddress);
        pws = findViewById(R.id.editTextTextEmailAddress3);
        confirmpws = findViewById(R.id.editTextTextPassword);
    }

}