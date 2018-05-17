package com.example.khaled.newsapi.Model;

import java.util.ArrayList;
import java.util.List;

public class News {


    private String status;
    private String totalResults;
    private List<Article> articles=new ArrayList<>();




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }



    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> article) {
        this.articles = article;
    }

}