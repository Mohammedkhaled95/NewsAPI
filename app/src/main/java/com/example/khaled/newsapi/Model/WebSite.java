package com.example.khaled.newsapi.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled on 03/05/18.
 */

public class WebSite {

    private String status;
    private List<Source> sources = new ArrayList<>();


    public WebSite() {
    }

    public WebSite(String status, List<Source> sourceList) {
        this.status = status;
        this.sources = sourceList;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {

        return sources;
    }


}
