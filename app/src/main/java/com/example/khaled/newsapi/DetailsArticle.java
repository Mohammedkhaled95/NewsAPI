package com.example.khaled.newsapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class DetailsArticle extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    WebView webView;
    AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        dialog = new SpotsDialog(this);
        dialog.show();
        //WebView
        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });


        if(getIntent() != null)
        {
            if(!getIntent().getStringExtra("webUrl").isEmpty())
                webView.loadUrl(getIntent().getStringExtra("webUrl"));
        }
    }

}
