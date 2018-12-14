package com.example.user.apiwork.networking;

import com.example.user.apiwork.Model.ModelComment;
import com.example.user.apiwork.Model.ModelImage;
import com.example.user.apiwork.Model.ModelPost;
import com.example.user.apiwork.Model.ModelRegister;
import com.example.user.apiwork.Model.ModelSignIn;
import com.example.user.apiwork.Model.ModelSignInAllUser;
import com.example.user.apiwork.Model.ModelSignInData;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    //all below functions are of ReqRes url
    @GET("api/users/2")
    Call<ModelSignInData> getUserOne();

    @GET("api/users?page=2")
    Call<ModelSignInAllUser> getUserList();

    @GET("api/users/23")
    Call<ModelSignIn> getUserOneNotFound();

    //In case of @PATH the variable name within @PATH("postId") i.e. postId can be different from API
    @GET("https://jsonplaceholder.typicode.com/posts/{postId}/comments")
    Call<List<ModelComment>> getCommentsByPath(@Path("postId") String id);

    //In case of @QUERY the variable name within @Query("postId") i.e. postId should be exact of same name as in API
    @GET("https://jsonplaceholder.typicode.com/comments")
    Call<List<ModelComment>> getCommentsByQuery(@Query("postId") String id);

    //In case of @QUERY the variable name within @Query("postId or _sort or _order") i.e. postId/_sort/_order should be exact of same name as in API
    @GET("https://jsonplaceholder.typicode.com/comments")
    Call<List<ModelComment>> getCommentsByQueryMultiple(
            @Query("postId") String postId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @POST("posts")
    Call<ModelPost> postUser(@Body ModelPost modelPost);

    @FormUrlEncoded
    @POST("https://api-webservices.brightlet.com?")
    Call<ModelRegister> registerUser(
            @Field("request") String request,
            @Field("customer_type") String customer_type,
            @Field("customer_name") String customer_name,
            @Field("customer_email") String customer_email,
            @Field("customer_password") String customer_password,
            @Field("customer_mobile_number") String customer_mobile_number,
            @Field("customer_share_data") String customer_share_data,
            @Field("remote_address") String remote_address,
            @Field("user_agent") String user_agent
    );

    @FormUrlEncoded
    @POST("https://api-webservices.brightlet.com/api_media_upload")
    Call<ModelImage> uploadImage(
            @Field("request") String request,
            @Field("customer_id") String customer_id,
            @Field("customer_type") String customer_type,
            @Field("customer_api_key") String customer_api_key,
            @Field("images") String images
    );

    @Multipart
    @POST("https://api-webservices.brightlet.com/api_media_upload?")
    Call<ModelImage> uploadImageOther(
            @Part("request") String request,
            @Part("customer_id") String customer_id,
            @Part("customer_type") String customer_type,
            @Part("customer_api_key") String customer_api_key,
            @Part("images") MultipartBody.Part images
    );

    //Please consider below function
    @FormUrlEncoded
    @POST("https://api-webservices.brightlet.com/api_media_upload")
    Call<ModelImage> uploadImageNew(
            @Field("request") String request,
            @Field("customer_id") String customer_id,
            @Field("customer_type") String customer_type,
            @Field("customer_api_key") String customer_api_key,
            @Field("images") File images
    );

}
