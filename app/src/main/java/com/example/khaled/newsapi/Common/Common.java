package com.example.khaled.newsapi.Common;

import com.example.khaled.newsapi.Interface.IconBetterService;
import com.example.khaled.newsapi.Interface.NewsService;
import com.example.khaled.newsapi.Remote.IconClient;
import com.example.khaled.newsapi.Remote.RetrofitClient;

/**
 * Created by khaled on 03/05/18.
 */

public class Common {

    public static final String BASE_URL ="https://newsapi.org/";
    public static final String API_KEY = "0f3f1d5b72e24997be5caa90ac8d8090";


    public static NewsService getNewsService(){

        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);

    }



    public static IconBetterService  getIconService(String faviconURL){

            return IconClient.getClient(faviconURL).create(IconBetterService.class);

    }






    public static String getApiUrl(String source,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }


}
