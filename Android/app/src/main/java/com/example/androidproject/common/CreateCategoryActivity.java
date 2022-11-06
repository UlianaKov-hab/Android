package com.example.androidproject.common;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.catalog.CatalogActivity;
import com.example.androidproject.dto.categories.CategoryCreateDTO;
import com.example.androidproject.service.CategoriesNetwork;
import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;

import com.example.androidproject.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCategoryActivity extends BaseActivity {

    int SELECT_CROPPER = 300;
    Uri uri;
    ImageView IVPreviewImage;
    private TextInputEditText txtCategoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        IVPreviewImage=findViewById(R.id.IVPreviewImage);
        txtCategoryName=findViewById(R.id.txtCategoryName);
    }


    public void handleCreateCategoryClick(View view)
    {
//        CommonUtils.setContext(this);
//        CommonUtils.showLoading();
        CategoryCreateDTO categoryCreateDTO=new CategoryCreateDTO();
        categoryCreateDTO.setName(txtCategoryName.getText().toString());
        categoryCreateDTO.setImage(uriGetBase64(uri));
        CategoriesNetwork
                .getInstance()
                .getJSONApi()
                .create(categoryCreateDTO)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        CommonUtils.hideLoading();
                        //створюємо намір запуску activity CatalogActivity
                        Intent intent = new Intent(CreateCategoryActivity.this, CatalogActivity.class);

                        startActivity(intent); //запускаємо CatalogActivity
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
//                        CommonUtils.hideLoading();
                    }
                });
    }
    // метод який перекодовує фотографію base64 в строку
    private String uriGetBase64(Uri uri)
    {
        try{
            Bitmap bitmap= null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // initialize byte stream
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            // compress Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            // Initialize byte array
            byte[] bytes=stream.toByteArray();
            // get base64 encoded string
            String sImage= Base64.encodeToString(bytes, Base64.DEFAULT);
            return sImage;
        }
        catch (Exception ex) {
            return null;
        }

    }
    // метод перекидає на іншу activity ChangeImageActivity
    public void handleSelectImageClick(View view)
    {
        Intent intent = new Intent(this, ChangeImageActivity.class);
        startActivityForResult(intent, SELECT_CROPPER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SELECT_CROPPER) {
            //String base64 = data.getStringExtra("base64");
            uri = (Uri) data.getParcelableExtra("croppedUri");
            IVPreviewImage.setImageURI(uri);
        }
    }

}