package com.example.androidproject.common;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidproject.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.graphics.Matrix;

import com.example.androidproject.BaseActivity;
import com.example.androidproject.R;
import com.example.androidproject.utils.CommonUtils;
import com.oginotihiro.cropview.CropUtil;
import com.oginotihiro.cropview.CropView;

import java.io.File;

import retrofit2.http.Url;

public class ChangeImageActivity extends BaseActivity {

    private  static int RESULT_LOAD_IMAGE=1;
    private CropView cropView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);

        cropView = findViewById(R.id.cropView);

        Intent modalSelectImage = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(modalSelectImage, RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            CommonUtils.showLoading();
            Uri selectedImage = data.getData();
            CropView cropView = (CropView) findViewById(R.id.cropView);
            cropView.of(selectedImage).asSquare().initialize(this);
//            cropView.of(selectedImage)
//                    //.withAspect(x, y)
//                    .withOutputSize(100, 100)
//                    .initialize(this);
//            CommonUtils.hideLoading();
        }
        else{
            //якщо натиснули кнопку назад, то викидає
            this.finish();
        }
    }
    public void RotateRightImage(View view) {
        //CropView cropView = (CropView) findViewById(R.id.cropView);
        cropView.setRotation(cropView.getRotation()+90);
    }
    public void RotateLeftImage(View view) {
        //CropView cropView = (CropView) findViewById(R.id.cropView);
        cropView.setRotation(cropView.getRotation()-90);
    }
    public void ChangeImage(View view) {
//        CommonUtils.showLoading();

        String fileTemp = java.util.UUID.randomUUID().toString();
        Bitmap croppedBitmap = cropView.getOutput();
        Matrix matrix = new Matrix();
        matrix.postRotate(cropView.getRotation());
        Bitmap rotatedBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);

        Uri destination = Uri.fromFile(new File(getCacheDir(), fileTemp));
        CropUtil.saveOutput(this, destination, rotatedBitmap, 90);
        CommonUtils.hideLoading();

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        int a = 32;
        Intent intent = new Intent();
//        intent.putExtra("base64", encoded);
        intent.putExtra("croppedUri", destination);
        setResult(300, intent);
        finish();
    }

    public void handleCropImage(View view) {

        String fileTemp = java.util.UUID.randomUUID().toString();
        Bitmap croppedBitmap = cropView.getOutput();
        Matrix matrix = new Matrix();
        matrix.postRotate(cropView.getRotation());
        Bitmap rotatedBitmap = Bitmap.createBitmap(croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, true);

        Uri destination = Uri.fromFile(new File(getCacheDir(), fileTemp));
        CropUtil.saveOutput(this, destination, rotatedBitmap, 90);

        Intent intent = new Intent();
        intent.putExtra("croppedUri", destination);
        setResult(300, intent);
        finish();
    }
}