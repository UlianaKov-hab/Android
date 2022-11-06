package com.example.androidproject.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.R;
import com.example.androidproject.catalog.CatalogActivity;
import com.example.androidproject.dto.users.UserLoginDTO;
import com.example.androidproject.network.users.LoginResponse;
import com.example.androidproject.service.UsersNetwork;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private TextInputEditText textFieldEmail;
    private TextInputEditText textFieldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textFieldEmail = findViewById(R.id.textEmail);
        textFieldPassword = findViewById(R.id.textPassword);

    }

    public void handleLoginClick(View view)
    {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail(textFieldEmail.getText().toString());
        userLoginDTO.setPassword(textFieldPassword.getText().toString());
        UsersNetwork
                .getInstance()
                .getJSONApi()
                .loginUser(userLoginDTO)
                .enqueue(new Callback<LoginResponse>(){
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, CatalogActivity.class);

                            startActivity(intent); //запускаємо CatalogActivity
                            finish();
                        } else {
                            // error response, no access to resource?
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                    }

                });
    }
}