package com.example.androidproject;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.androidproject.categorycard.CategoriesAdapter;
import com.example.androidproject.dto.categories.CategoryItemDTO;
import com.example.androidproject.service.CategoriesNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    CategoriesAdapter categoriesAdapter;
    private RecyclerView rcvCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvCategories = findViewById(R.id.rcvCategories);
        rcvCategories.setHasFixedSize(true);
        rcvCategories.setLayoutManager(
                new GridLayoutManager(this,1, LinearLayoutManager.VERTICAL,false));

        requestServer();
//        ImageView imageView = (ImageView) findViewById(R.id.ivGirl);
//
//        Glide.with(this).load("http://10.0.2.2:5131/images/6_m.jpeg")
//                .apply((new RequestOptions().override(1000)))
//                .into(imageView);

//        ListView listView = (ListView) findViewById(R.id.myList);
//        final String[] catNames = new String[] {
//                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
//                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
//                "Китти", "Масяня", "Симба"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, catNames);
//        listView.setAdapter(adapter);



    }
    public void myClickImage(View view)
    {
//        Toast toast = Toast.makeText(this, "Hello", Toast.LENGTH_LONG);
//        toast.show();
//        requestServer();


    }
    private void requestServer() {
        MainActivity instance = this;
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

//                        Toast toast = Toast.makeText(instance, data.get(0).getName(), Toast.LENGTH_LONG);
//                        toast.show();
//                        ListView listView = (ListView) findViewById(R.id.myList);
//                        String[] stringArray = new String[data.size()];
//                        int index = 0;
//                        for (Object value : data) {
//                            stringArray[index] = (String) value;
//                            index++;
//                        }
//                        data.toArray(stringArray);
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(instance,
//                               android.R.layout.simple_list_item_1, stringArray);
//                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {
                    int a = 10;
                    a = 5;
                    }
                });
    }

}