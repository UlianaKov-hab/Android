package com.example.androidproject.catalog;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.R;
import com.example.androidproject.categorycard.CategoriesAdapter;
import com.example.androidproject.dto.categories.CategoryItemDTO;
import com.example.androidproject.service.CategoriesNetwork;
import com.example.androidproject.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends BaseActivity {


    CategoriesAdapter categoriesAdapter;
    private RecyclerView rcvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        rcvCategories = findViewById(R.id.rcvCategories);
        rcvCategories.setHasFixedSize(true);
        rcvCategories.setLayoutManager(
                new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));
        requestServer();
    }
    private void requestServer()
    {
        CommonUtils.showLoading();
        CategoriesNetwork
                .getInstance()
                .getJSONApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        List<CategoryItemDTO> data = response.body();
                        categoriesAdapter = new CategoriesAdapter(data);
                        rcvCategories.setAdapter(categoriesAdapter);
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {

                    }
                });
    }
}
