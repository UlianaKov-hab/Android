package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidproject.R;
import com.example.androidproject.application.HomeApplication;
import com.example.androidproject.constants.Urls;

public class CategoryItemActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
//передача за допомогою Bundle
        Bundle b = getIntent().getExtras();
        String name = ""; // or other values
        String image = "";
        if(b != null){
            name = b.getString("name");
            image = b.getString("image");
            TextView tx = findViewById(R.id.textViewCategoryName);
            ImageView imageView = findViewById(R.id.imageView);
            tx.setText(name);
            String url = Urls.BASE + image;
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(600))
                    .into(imageView);
        }



    }
}