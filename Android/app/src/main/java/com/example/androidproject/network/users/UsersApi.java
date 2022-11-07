package com.example.androidproject.network.users;

import com.example.androidproject.account.dto.LoginDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersApi {
    @POST("/api/account/login")
//    public Call<Void> create(@Body UserLoginDTO userLoginDTO);
    public Call<LoginResponse> loginUser(@Body LoginDTO userLoginDTO);
}
