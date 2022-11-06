package com.example.androidproject.service;

import com.example.androidproject.constants.Urls;
import com.example.androidproject.network.users.UsersApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersNetwork {
    private static UsersNetwork mInstance;
    private static final String BASE_URL = Urls.BASE;
    private Retrofit mRetrofit;

    private UsersNetwork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static UsersNetwork getInstance(){
        if(mInstance == null)
            mInstance = new UsersNetwork();
        return  mInstance;
    }
    public UsersApi getJSONApi(){
        return mRetrofit.create(UsersApi.class);
    }
}
