package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, register;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button2);
        register = findViewById(R.id.button4);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && user.isEmailVerified()) {
            Intent home = new Intent(this, homeActivity.class);
            startActivity(home);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button2){
            intent = new Intent(this, loginActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.button4){
            intent = new Intent(this, registActivity.class);
            startActivity(intent);
        }
    }
}