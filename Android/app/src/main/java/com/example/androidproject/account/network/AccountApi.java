package com.example.androidproject.account.network;

import com.example.androidproject.account.dto.AccountResponseDTO;
import com.example.androidproject.account.dto.LoginDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/api/account/login")
    public Call<AccountResponseDTO> login(@Body LoginDTO model);
}
