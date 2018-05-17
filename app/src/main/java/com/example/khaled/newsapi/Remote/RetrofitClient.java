package com.example.khaled.newsapi.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khaled on 03/05/18.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit;

    }
}
