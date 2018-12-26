package com.example.user.apiwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.apiwork.Model.ModelBrightLetRegister;
import com.example.user.apiwork.networking.ApiClient;
import com.example.user.apiwork.networking.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;

    TextView txtRegisterResponse;
    EditText etEmail;
    Button btnRegisterUser;
    Button btnGoForImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtRegisterResponse = findViewById(R.id.txtRegisterResponse);
        etEmail = findViewById(R.id.etEmail);

        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnGoForImage = findViewById(R.id.btnGoForImage);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnRegisterUser.setOnClickListener(this);
        btnGoForImage.setOnClickListener(this);
    }

    public void registerUser() {
        ModelBrightLetRegister modelRegister = new ModelBrightLetRegister("register", "Tenant",
                "Lala", "lala1@gmail.com", "12345678", "12345678901",
                "yes|no", "location", "ios|android");

        Call<ModelBrightLetRegister> call = apiInterface.registerUser("register", "Tenant", "Lala g",
                etEmail.getText().toString(), "12345678", "12345678901",
                "yes|no", "location", "ios|android");
        call.enqueue(new Callback<ModelBrightLetRegister>() {
            @Override
            public void onResponse(Call<ModelBrightLetRegister> call, Response<ModelBrightLetRegister> response) {
                Toast.makeText(RegisterActivity.this, "Success: " + response.code(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Body: " + response.body().getError(), Toast.LENGTH_SHORT).show();
                    txtRegisterResponse.setText("Code: " + response.code());

                    ModelBrightLetRegister registerResponse = response.body();

                    String content = "";
                    content += "\nEmail: " + registerResponse.getCustomer_email() + "\n";
                    content += "\nName: " + registerResponse.getCustomer_name() + "\n";
                    content += "\nType: " + registerResponse.getCustomer_type() + "\n";
                    content += "\nSuccess???: " + registerResponse.getSuccess() + "\n";
                    content += "\nUserAgent: " + registerResponse.getUser_agent() + "\n\n";
                    txtRegisterResponse.append(content);
                } else
                    Toast.makeText(RegisterActivity.this, "in else", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelBrightLetRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnRegisterUser) {
            if (TextUtils.isEmpty(etEmail.getText().toString())) {
                Toast.makeText(this, "Email must required", Toast.LENGTH_SHORT).show();

            } else {
                registerUser();
            }
        }

        if (id == R.id.btnGoForImage) {
            startActivity(new Intent(RegisterActivity.this, ImageActivity.class));
        }
    }
}
