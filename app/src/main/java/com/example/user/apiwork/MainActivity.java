package com.example.user.apiwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.apiwork.Adapter.RecyclerAdapter;
import com.example.user.apiwork.Model.ModelComment;
import com.example.user.apiwork.Model.ModelSignIn;
import com.example.user.apiwork.Model.ModelSignInAllUser;
import com.example.user.apiwork.Model.ModelSignInData;
import com.example.user.apiwork.networking.ApiClient;
import com.example.user.apiwork.networking.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    ArrayList<ModelSignIn> modelSignInList;
    ApiInterface apiInterface;

    TextView txtResponse;

    Button btnUserSingle, btnUserList, btnUserSingleNotFound;
    Button btnGetSpecificRecord, btnGetRecordUsingQuery, btnGetRecordUsingQueryMultiple;

    Button btnJumpToRegisterActivity;

    EditText etId;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResponse = findViewById(R.id.txtResponse);
        progressBar = findViewById(R.id.progressBar);

        btnUserList = findViewById(R.id.btnUserList);
        btnUserSingle = findViewById(R.id.btnUserSingle);
        btnUserSingleNotFound = findViewById(R.id.btnUserSingleNotFound);

        btnGetSpecificRecord = findViewById(R.id.btnGetSpecificRecord);
        btnGetRecordUsingQuery = findViewById(R.id.btnGetRecordUsingQuery);
        btnGetRecordUsingQueryMultiple = findViewById(R.id.btnGetRecordUsingQueryMultiple);

        btnJumpToRegisterActivity = findViewById(R.id.btnJumpToRegisterActivity);

        etId = findViewById(R.id.etId);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        modelSignInList = new ArrayList<>();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnUserSingle.setOnClickListener(this);
        btnUserList.setOnClickListener(this);
        btnUserSingleNotFound.setOnClickListener(this);

        btnGetSpecificRecord.setOnClickListener(this);
        btnGetRecordUsingQuery.setOnClickListener(this);
        btnGetRecordUsingQueryMultiple.setOnClickListener(this);

        btnJumpToRegisterActivity.setOnClickListener(this);
    }

    public void getUserSingle() {
        progressBar.setVisibility(View.VISIBLE);

        Call<ModelSignInData> call = apiInterface.getUserOne();
        call.enqueue(new Callback<ModelSignInData>() {
            @Override
            public void onResponse(Call<ModelSignInData> call, Response<ModelSignInData> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.body() != null) {
                    ModelSignIn modelSignIn = response.body().getData();

                    txtResponse.setText("First Name: " + modelSignIn.getFirstName());
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    txtResponse.setText("single user not found from server");
                }
            }

            @Override
            public void onFailure(Call<ModelSignInData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getUserList() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        progressBar.setIndeterminate(true);

        Call<ModelSignInAllUser> call = apiInterface.getUserList();
        call.enqueue(new Callback<ModelSignInAllUser>() {
            @Override
            public void onResponse(Call<ModelSignInAllUser> call, Response<ModelSignInAllUser> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                if (response.body() != null) {
                    List<ModelSignIn> list = response.body().getData();

                    recyclerAdapter = new RecyclerAdapter(list);
                    recyclerView.setAdapter(recyclerAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    txtResponse.setText("user list not found from server");
                }
            }

            @Override
            public void onFailure(Call<ModelSignInAllUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getUserSingleNotFound() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setClickable(false);
        progressBar.setIndeterminate(true);

        Call<ModelSignIn> call = apiInterface.getUserOneNotFound();

        call.enqueue(new Callback<ModelSignIn>() {
            @Override
            public void onResponse(Call<ModelSignIn> call, Response<ModelSignIn> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                if (response.body() != null) {
                    txtResponse.setText("in if" + response.body());
                } else {
                    progressBar.setVisibility(View.GONE);
                    txtResponse.setText("Response body is null.... User not found from server");
                }
            }

            @Override
            public void onFailure(Call<ModelSignIn> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Functions: getCommentsByPath & getCommentsByQuery will produce same result
    // but Retrofit Attribute (e.g. @PATH/ @QUERY) is different due to URL
    //Because in some url we need @PATH using forward slash(i.e. /{postId}) "https://jsonplaceholder.typicode.com/posts/{postId}/comments"
    //And in some url we need @QUERY because url contains question mark(i.e. ?) https://jsonplaceholder.typicode.com/posts/?postId=3"

    public void getCommentsByPath() {
        progressBar.setVisibility(View.VISIBLE);

        Call<List<ModelComment>> call = apiInterface.getCommentsByPath(etId.getText().toString());
        call.enqueue(new Callback<List<ModelComment>>() {
            @Override
            public void onResponse(Call<List<ModelComment>> call, Response<List<ModelComment>> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                txtResponse.setText("");

                if (response.body() != null) {
                    Toast.makeText(MainActivity.this, "Code:" + response.code(), Toast.LENGTH_LONG).show();

                    List<ModelComment> comments = response.body();
                    for (ModelComment comment : comments) {
                        String content = "";
                        content = "\nEmail: " + comment.getEmail() + "\n";
                        txtResponse.append(content);
                        //Toast.makeText(MainActivity.this, "Name: " + comment.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Null body with code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelComment>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCommentsByQuery() {
        progressBar.setVisibility(View.VISIBLE);

        Call<List<ModelComment>> call = apiInterface.getCommentsByQuery(etId.getText().toString());
        call.enqueue(new Callback<List<ModelComment>>() {
            @Override
            public void onResponse(Call<List<ModelComment>> call, Response<List<ModelComment>> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                txtResponse.setText("");

                if (response.body() != null) {
                    List<ModelComment> comments = response.body();

                    for (ModelComment comment : comments) {
                        String content = "";
                        content = "\nEmail: " + comment.getEmail() + "\n";
                        txtResponse.append(content);
                    }
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelComment>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCommentsByQueryMultiple() {
        progressBar.setVisibility(View.VISIBLE);

        // sort using variables in ModelComments i.e. id, postId, email etc
        //In desc/asc order
        Call<List<ModelComment>> call = apiInterface.getCommentsByQueryMultiple(etId.getText().toString(), "id", "desc");
        call.enqueue(new Callback<List<ModelComment>>() {
            @Override
            public void onResponse(Call<List<ModelComment>> call, Response<List<ModelComment>> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                if (response.body() != null) {
                    txtResponse.append("\n\nDescending Order:\n");
                    List<ModelComment> comments = response.body();
                    for (ModelComment comment : comments) {
                        String content = "";
                        content = "\nEmail: " + comment.getEmail() + "\n";
                        txtResponse.append(content);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelComment>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View view) {
        Integer id = view.getId();

        if (id == R.id.btnUserSingle) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getUserSingle();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        if (id == R.id.btnUserList) {
            getUserList();
        }
        if (id == R.id.btnUserSingleNotFound) {
            getUserSingleNotFound();
        }
        if (id == R.id.btnGetSpecificRecord) {
            if (!TextUtils.isEmpty(etId.getText().toString())) {
                //new function for other link: https://jsonplaceholder.typicode.com
                //getComments using @PATH variable type given by retrofit
                getCommentsByPath();
            } else {
                Toast.makeText(this, "Required field", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.btnGetRecordUsingQuery) {
            getCommentsByQuery();
        }
        if (id == R.id.btnGetRecordUsingQueryMultiple) {
            getCommentsByQueryMultiple();
        }
        if (id == R.id.btnJumpToRegisterActivity) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }

    }
}
