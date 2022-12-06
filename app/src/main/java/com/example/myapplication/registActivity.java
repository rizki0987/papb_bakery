package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registActivity extends AppCompatActivity implements View.OnClickListener {
    private Button regist;
    private TextView nama, email, password, confirmpws, loginHere;
    private ImageView backToRegist;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && user.isEmailVerified()) {
            Intent home = new Intent(this, homeActivity.class);
            startActivity(home);
        }
    }

    private void initView(){
        backToRegist = findViewById(R.id.imageButton);
        backToRegist.setOnClickListener(this);
        regist = findViewById(R.id.button2);
        regist.setOnClickListener(this);
        loginHere = findViewById(R.id.button);
        loginHere.setOnClickListener(this);
        nama = findViewById(R.id.editTextTextEmailAddress2);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextEmailAddress3);
        confirmpws = findViewById(R.id.editTextTextPassword);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && user.isEmailVerified()) {
            Intent home = new Intent(this, homeActivity.class);
            startActivity(home);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton){
            this.finish();
        }else if(view.getId() == R.id.button2){
            if(!password.getText().toString().equals(confirmpws.getText().toString())){
                Toast.makeText(this, "your password doesnt match", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        UserProfileChangeRequest profilUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nama.getText().toString()).build();
                                        user.updateProfile(profilUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("update profil", "user profile updated");
                                                        }
                                                    }
                                                });
                                        final String email = user.getEmail();
                                        user.sendEmailVerification().addOnCompleteListener(registActivity.this, ((task1) -> {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(registActivity.this, "verification sent to " + email, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.e("Error verifikasi", "send email verifikasi", task.getException());
                                                Toast.makeText(registActivity.this, "failed to send verification email", Toast.LENGTH_SHORT).show();
                                            }
                                        }));
                                        user User = new user();
                                        User.setUid(user.getUid());
                                        User.setUsername(nama.getText().toString());
                                        User.setEmail(user.getEmail());
                                        User.setIs_admin(1);
                                        userService userService = RetrofitClient.getClient("").create(userService.class);
                                        Call<apiResponse> request = userService.register(User);
                                        request.enqueue(new Callback<apiResponse>() {
                                            @Override
                                            public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                                                if(response.isSuccessful()){
                                                    apiResponse list = response.body();
                                                    Log.d("success", "list " + list.getData());
                                                }else {
                                                    String error = apiResponse.readError(response.errorBody());
                                                    Log.d("errt", "" + error);
                                                    Toast.makeText(registActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<apiResponse> call, Throwable t) {
                                                Log.d("DataModel", "" + t.getMessage());
                                                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        if(user.isEmailVerified()) {
                                            Intent home = new Intent(registActivity.this, homeActivity.class);
                                            startActivity(home);
                                        }
                                    }
                                }
                            }
                        });
            }
        } else if (view.getId() == R.id.button){
            this.finish();
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
        }
    }
}