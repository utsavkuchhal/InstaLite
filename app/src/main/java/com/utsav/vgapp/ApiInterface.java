package com.utsav.vgapp;

import com.utsav.vgapp.Models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("everything")
    Call<News>  getspecificdata(@Query("q") String query, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<News> getNewsbyCategories(@Query("country") String country,@Query("category")String category ,@Query("apiKey") String apiKey);

}
