package com.example.androidproject.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.CategoryItemActivity;
import com.example.androidproject.R;
import com.example.androidproject.application.HomeApplication;
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
//        CommonUtils.showLoading();
        CategoriesNetwork
                .getInstance()
                .getJSONApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        if(response.isSuccessful()){
                            List<CategoryItemDTO> data = response.body();
                            categoriesAdapter = new CategoriesAdapter(data, CatalogActivity.this::onClickByItem);
                            rcvCategories.setAdapter(categoriesAdapter);
//                        CommonUtils.hideLoading();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {

                    }
                });
    }

    private void onClickByItem(CategoryItemDTO category) {
        Toast.makeText(HomeApplication.getAppContext(), category.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CatalogActivity.this, CategoryItemActivity.class);

        //передача за допомогою Bundl
        Bundle b = new Bundle();
        b.putString("name", category.getName());
        b.putString("image", category.getImage());
        intent.putExtras(b); //Put your id to your next Intent

//        intent.putExtra("name", category.getName());
//        intent.putExtra("image", category.getImage());


        startActivity(intent);
        //finish();

    }


}
