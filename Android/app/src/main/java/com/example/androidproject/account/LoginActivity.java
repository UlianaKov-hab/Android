package com.example.androidproject.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.account.dto.AccountResponseDTO;
import com.example.androidproject.account.dto.LoginDTO;
import com.example.androidproject.account.dto.ValidationRegisterDTO;
import com.example.androidproject.account.network.AccountService;
import com.example.androidproject.application.HomeApplication;
import com.example.androidproject.application.JwtSecurityService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private TextView tvInfo;
    private TextInputLayout textFieldEmail;
    private TextInputEditText txtEmail;

    private TextInputLayout textFieldPassword;
    private TextInputEditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvInfo = findViewById(R.id.tvInfo);
        textFieldEmail = findViewById(R.id.textFieldEmail);
        txtEmail = findViewById(R.id.txtEmail);

        textFieldPassword = findViewById(R.id.textFieldPassword);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void handleClick(View view) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(txtEmail.getText().toString());
        loginDTO.setPassword(txtPassword.getText().toString());

        if (!validationFields(loginDTO))
            return;

        AccountService.getInstance()
                .jsonApi()
                .login(loginDTO)
                .enqueue(new Callback<AccountResponseDTO>() {
                    @Override
                    public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {

                        if (response.isSuccessful()) {
                            AccountResponseDTO data = response.body();

                            JwtSecurityService jwtService = (JwtSecurityService) HomeApplication.getInstance();
                            jwtService.saveJwtToken(data.getToken());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                showErrorsServer(response.errorBody().string());
                            } catch (Exception e) {
                                tvInfo.setText("?????????????? ??????????");
                                tvInfo.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.red));
                                System.out.println("------Error response parse body-----");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
                        //CommonUtils.hideLoading();
                        String str = t.toString();
                        int a = 12;
                    }
                });

    }

    private boolean validationFields(LoginDTO loginDTO) {
        textFieldEmail.setError("");
        if (loginDTO.getEmail().equals("")) {
            textFieldEmail.setError("?????????????? ??????????");
            return false;
        }

        textFieldPassword.setError("");
        if (loginDTO.getPassword().equals("")) {
            textFieldPassword.setError("?????????????? ????????????");
            return false;
        }

        return true;
    }

    private void showErrorsServer(String json) {
        Gson gson = new Gson();
        ValidationRegisterDTO result = gson.fromJson(json, ValidationRegisterDTO.class);
        String str = "";
        if (result.getErrors().getEmail() != null) {
            for (String item : result.getErrors().getEmail()) {
                str += item + "\n";
            }
        }
        textFieldEmail.setError(str);

        str = "";
        if (result.getErrors().getFirstName() != null) {
            for (String item : result.getErrors().getFirstName()) {
                str += item + "\n";
            }
        }
        textFieldPassword.setError(str);
    }
}