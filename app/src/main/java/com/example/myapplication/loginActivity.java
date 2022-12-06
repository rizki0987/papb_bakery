package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    Button login;
    EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        back = findViewById(R.id.imageButton);
        back.setOnClickListener(this);
        login = findViewById(R.id.button2);
        login.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && user.isEmailVerified()) {
            Intent home = new Intent(this, homeActivity.class);
            home.putExtra("email", user.getEmail());
            startActivity(home);
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton){
            this.finish();
        }else if(view.getId() == R.id.button2){
            mAuth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                    Log.d("fbAuth", "" + task.getResult().getSignInMethods().size());
                    if (task.getResult().getSignInMethods().size() == 0) {
                        Toast.makeText(loginActivity.this, "This email has not been registered", Toast.LENGTH_SHORT).show();
                    }else{
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(loginActivity.this, (task1 -> {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            if(user.isEmailVerified()) {
                                                Intent home = new Intent(loginActivity.this, homeActivity.class);
                                                home.putExtra("email", email.getText().toString());
                                                startActivity(home);
                                            } else {
                                                Toast.makeText(loginActivity.this, "not verified", Toast.LENGTH_LONG).show();
                                            }
                                        }else {
                                            Toast.makeText(loginActivity.this, "auth failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }));
                    }
                }
            });
        }
    }
}