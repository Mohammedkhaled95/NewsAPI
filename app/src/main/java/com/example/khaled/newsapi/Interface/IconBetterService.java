package com.example.khaled.newsapi.Interface;

import com.example.khaled.newsapi.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconBetterService {



    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);
} 