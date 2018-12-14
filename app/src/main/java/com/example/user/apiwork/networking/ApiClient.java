package com.example.user.apiwork.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static String BASE_URL = "https://api-webservices.brightlet.com/";
    private static String BASE_URL1 = "https://reqres.in/";
    private static String BASE_URL2 = "https://jsonplaceholder.typicode.com/";


    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
//https://reqres.in/api/users/2

