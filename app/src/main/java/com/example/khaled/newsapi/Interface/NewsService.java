package com.example.khaled.newsapi.Interface;

import com.example.khaled.newsapi.Common.Common;
import com.example.khaled.newsapi.Model.News;
import com.example.khaled.newsapi.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by khaled on 03/05/18.
 */

public interface NewsService {
    @GET("/v2/sources?apiKey=" + Common.API_KEY)
    Call<WebSite> getSources();


    @GET
    Call<News> getNewsArticles(@Url String url);
}
