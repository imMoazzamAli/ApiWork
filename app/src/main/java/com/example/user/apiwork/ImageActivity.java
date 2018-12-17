package com.example.user.apiwork;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.apiwork.Model.ModelImage;
import com.example.user.apiwork.networking.ApiClient;
import com.example.user.apiwork.networking.ApiInterface;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;

    ImageView imgUpload;
    TextView txtExtra;
    Button btnChooseImage, btnUploadImage;

    public static int RC_IMAGE = 1;
    Bitmap bitmap;
    Uri uriPath;

    String mediapath = "";
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgUpload = findViewById(R.id.imgUpload);

        txtExtra = findViewById(R.id.txtExtra);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnChooseImage.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
    }

    public void chooseImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RC_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_IMAGE && resultCode == RESULT_OK && data != null) {
           /* String image_name = "testing123.jpg";
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + image_name
            );

            uriPath = Uri.fromFile(file);*/


            //Everything is fine.... image got successfully
            uriPath = data.getData();
            mediapath = data.getData().toString();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriPath);
                imgUpload.setImageBitmap(bitmap);
                btnUploadImage.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() {
        //done using PRBHESH tutorial
        /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);

        byte[] imgByte = byteArrayOutputStream.toByteArray();
        String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);

        return imgString;*/

        //new video work
        //bitmap = BitmapFactory.decodeFile(uriPath.getPath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, 0);

    }

    public void uploadImageHere() {
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";
        //get converted image form above function
        final String image = imageToString();

        Call<ModelImage> call = apiInterface.uploadImage(request, customer_id, customer_type, customer_api_key, image);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                //Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    txtExtra.setText("byte array here" + image);

                    Toast.makeText(ImageActivity.this, "In IF Code: " + response.code(), Toast.LENGTH_LONG).show();
                    txtExtra.setText("Body: " + response.body() + "\n\n");
                    txtExtra.append("URI Path: " + uriPath + "\n\n");
                    txtExtra.append("Media Path: " + mediapath + "\n\n");

                } else {
                    Toast.makeText(ImageActivity.this, "Response Not Success & Null Body: " + response.code(), Toast.LENGTH_LONG).show();

                    txtExtra.setText("Body: " + response.body() + "\n\n");
                    //txtExtra.append("Get Images: " + response.body().getImages() + "\n\n");

                    txtExtra.append("URI Path: " + uriPath + "\n\n");
                    txtExtra.append("Media Path: " + mediapath);

                }
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void uploadImageNew() {

        ArrayList arrayList = new ArrayList();
        arrayList.add(mediapath);

        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";

        Call<ModelImage> call = apiInterface.uploadImageNew(request, customer_id, customer_type, customer_api_key, file);

        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();

                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                txtExtra.setText("Body: " + response.body() + "\n\n");
                txtExtra.append("URI Path" + uriPath + "\n\n");
                txtExtra.append("Media Path" + mediapath + "\n\n");

            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
  /*//get converted image form above function
        String path = uriPath.getPath();
        File file = null;
        try {
            file = new File(new URI(path).toString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uriPath)), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
*/

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnChooseImage) {
            chooseImageFromGallery();
        }
        if (id == R.id.btnUploadImage) {
            uploadImageHere();
            //uploadImageNew();
        }
    }
}
