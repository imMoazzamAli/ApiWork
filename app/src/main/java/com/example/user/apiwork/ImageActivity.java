package com.example.user.apiwork;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.os.RecoverySystem;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PathUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.apiwork.Model.ModelBrightLetSignIn;
import com.example.user.apiwork.Model.ModelImage;
import com.example.user.apiwork.networking.ApiClient;
import com.example.user.apiwork.networking.ApiInterface;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;

    EditText etEmail, etPassword;
    Button btnSignIn;

    ImageView imgUpload;
    TextView txtExtra;
    Button btnChooseImage, btnUploadImage, btnUploadImageOther;

    public static int RC_IMAGE = 1;
    Bitmap bitmap;
    byte[] arrayData;
    Uri uriPath;

    String selectedFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        imgUpload = findViewById(R.id.imgUpload);
        txtExtra = findViewById(R.id.txtExtra);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnUploadImageOther = findViewById(R.id.btnUploadImageOther);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnSignIn.setOnClickListener(this);

        btnChooseImage.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
        btnUploadImageOther.setOnClickListener(this);
    }

    public void signInHere() {
        String request = "login";
        String customer_type = "private-landlord";
        String customer_email;
        String customer_password;

        if (TextUtils.isEmpty(etEmail.getText()) || TextUtils.isEmpty(etPassword.getText())) {
            Toast.makeText(this, "Both Fields Required", Toast.LENGTH_SHORT).show();

        } else {
            customer_email = etEmail.getText().toString();
            customer_password = etPassword.getText().toString();

            Call<ModelBrightLetSignIn> call = apiInterface.signInUser(request, customer_type, customer_email, customer_password);
            call.enqueue(new Callback<ModelBrightLetSignIn>() {
                @Override
                public void onResponse(Call<ModelBrightLetSignIn> call, Response<ModelBrightLetSignIn> response) {
                    Toast.makeText(ImageActivity.this, "Success.", Toast.LENGTH_SHORT).show();

                    String abc = "";
                    txtExtra.setText(abc);
                    txtExtra.append("action: " + response.body().getAction() + "\n");
                    txtExtra.append("code: " + response.code() + "\n");
                    txtExtra.append("url: " + response.body().getCustomer_profile_image() + "\n");
                    txtExtra.append("error: " + response.body().getError() + "\n");
                    txtExtra.append("code: " + response.code() + "\n");
                }

                @Override
                public void onFailure(Call<ModelBrightLetSignIn> call, Throwable t) {
                    Toast.makeText(ImageActivity.this, "Failure.", Toast.LENGTH_SHORT).show();
                }
            });

        }

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
            //Everything is fine.... image got successfully
            uriPath = data.getData();

            selectedFilePath = FilePath.getPath(this, uriPath);

            try {
                txtExtra.setText("URIPath.getPath(): " + uriPath.getPath() + "\n");
                txtExtra.append("URIPath: " + uriPath.toString() + "\n");
                txtExtra.append("SelectedFilePath: " + selectedFilePath + "\n");
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriPath);
                imgUpload.setImageBitmap(bitmap);
                btnUploadImage.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);

        arrayData = byteArrayOutputStream.toByteArray();
        String imgString = Base64.encodeToString(arrayData, Base64.DEFAULT);

        return imgString;
    }

    //function to upload image in string form
    public void uploadImageString() {
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";
        //get converted image form above function
        final String image = imageToString();

        Call<ModelImage> call = apiInterface.uploadImageString(request, customer_id, customer_type, customer_api_key, image);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();

                txtExtra.setText("Body: " + response.body() + "\n");
                txtExtra.append("URI Path: " + uriPath + "\n");
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadImageNew() {
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";

        //5th parameter
        //final File file = new File(uriPath.getPath());
        final File file = new File(selectedFilePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        //RequestBody reqFile = RequestBody.create(MediaType.parse("multipart"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), reqFile);


        Call<ModelImage> call = apiInterface.uploadImageNew(request, customer_id, customer_type, customer_api_key, body);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_LONG).show();
                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();

                txtExtra.setText("Body: " + response.body() + "\n\n");
                txtExtra.append("File Name: " + file.getName() + "\n\n");
                txtExtra.append("File Path: " + uriPath.getPath() + "\n\n");
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void uploadImageMultipart() {
        RequestBody request = RequestBody.create(MultipartBody.FORM, "upload-profile-image");
        RequestBody customer_id = RequestBody.create(MultipartBody.FORM, "o3WhpA==");
        RequestBody customer_type = RequestBody.create(MultipartBody.FORM, "private-landlord");
        RequestBody customer_api_key = RequestBody.create(MultipartBody.FORM, "b7e480c79fc30cdc");

/*
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";
*/

        //File file = new File(uriPath.toString());
        File file = new File(selectedFilePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        Call<ModelImage> call = apiInterface.uploadImageMultipart(request, customer_id, customer_type, customer_api_key, body);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadImageQueryMap() {
        //RequestBody request = RequestBody.create(MultipartBody.FORM, "upload-profile-image");
        RequestBody customer_id = RequestBody.create(MultipartBody.FORM, "o3WhpA==");
        RequestBody customer_type = RequestBody.create(MultipartBody.FORM, "private-landlord");
        RequestBody customer_api_key = RequestBody.create(MultipartBody.FORM, "b7e480c79fc30cdc");

        String request = "upload-profile-image";

/*
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";
*/

        Map<String, RequestBody> params = new HashMap<>();
        // params.put("request", request);
        params.put("customer_id", customer_id);
        params.put("customer_type", customer_type);
        params.put("customer_api_key", customer_api_key);


        //File file = new File(uriPath.toString());
        File file = new File(selectedFilePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        params.put("images", reqFile);

        Call<ModelImage> call = apiInterface.uploadImageQueryMap(request, params);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure ", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void uploadImageQuery() {
        //ali6@gmail.com information
        String request = "upload-profile-image";
        String customer_id = "o3WhpA==";
        String customer_type = "private-landlord";
        String customer_api_key = "b7e480c79fc30cdc";

       /* String request = "upload-profile-image";
        String customer_id = "onqfqQ==";
        String customer_type = "private-landlord";
        String customer_api_key = "9e52c2ea3e73465e";*/

        //File file = new File(uriPath.toString());
        //File file = new File(selectedFilePath);
        File file = new File(uriPath.toString());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), reqFile);

        Call<ModelImage> call = apiInterface.uploadImageQuery(request, customer_id, customer_type, customer_api_key, body);
        call.enqueue(new Callback<ModelImage>() {
            @Override
            public void onResponse(Call<ModelImage> call, Response<ModelImage> response) {
                Toast.makeText(ImageActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ImageActivity.this, "Body: " + response.body(), Toast.LENGTH_SHORT).show();

                txtExtra.setText("Body: " + response.body() + "\n\n");
                txtExtra.append("Success: " + response.body().getSuccess() + "\n");
                txtExtra.append("Error: " + response.body().getError() + "\n");
            }

            @Override
            public void onFailure(Call<ModelImage> call, Throwable t) {
                Toast.makeText(ImageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (view == btnSignIn) {
            signInHere();

        } else if (id == R.id.btnChooseImage) {
            chooseImageFromGallery();
        }
        if (id == R.id.btnUploadImage) {
            //uploadImageString();
            uploadImageNew();
        }

        if (id == R.id.btnUploadImageOther) {
            //uploadImageMultipart();
            //uploadImageQueryMap();
            uploadImageQuery();
        }
    }
}
