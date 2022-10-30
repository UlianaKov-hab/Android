package com.example.androidproject.service;

import com.example.androidproject.constants.Urls;
import com.example.androidproject.network.CategoriesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesNetwork {
    private static CategoriesNetwork mInstance;
    private static final String BASE_URL = Urls.BASE;
    private Retrofit mRetrofit;

    private  CategoriesNetwork()
    {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static CategoriesNetwork getInstance(){
        if(mInstance == null)
            mInstance = new CategoriesNetwork();
        return  mInstance;
    }
    public CategoriesApi getJSONApi(){
        return mRetrofit.create(CategoriesApi.class);
    }
}