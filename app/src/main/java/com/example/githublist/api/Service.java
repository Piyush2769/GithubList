package com.example.githublist.api;

import com.example.githublist.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service
{
    @GET("/search/users?q=language:java+location:india")
    Call<ItemResponse> getItems();




}
