  package com.example.khaled.newsapi;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.khaled.newsapi.Adapter.ListNewsAdapter;
import com.example.khaled.newsapi.Common.Common;
import com.example.khaled.newsapi.Interface.NewsService;
import com.example.khaled.newsapi.Model.Article;
import com.example.khaled.newsapi.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    private static final String TAG = "ListNews";
    KenBurnsView kvb;
    DiagonalLayout diagonalLayout;
    android.app.AlertDialog dialog;
    NewsService mService;
    TextView topAuthor, topTitle;
    SwipeRefreshLayout swipeLayout;

    String source = "";
    String webHotUrl = "";

    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //service
        mService = Common.getNewsService();

        dialog = new SpotsDialog(this);

        //views
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.news_swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source , true);
            }
        });

        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonal_layout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //hot and latest news
                Intent detail = new Intent(getBaseContext(),DetailsArticle.class);
                detail.putExtra("webUrl",webHotUrl);
                startActivity(detail);

            }
        });

        kvb = (KenBurnsView) findViewById(R.id.top_image);
        topAuthor = (TextView)findViewById(R.id.top_author);
        topTitle = (TextView)findViewById(R.id.top_title);

        lstNews = (RecyclerView) findViewById(R.id.ls_news);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);



        //Intent
        if (getIntent() != null)
        {
            source = getIntent().getStringExtra("source");

            if (!source.isEmpty() ){
                Log.e(TAG, "onCreate: source content is "+source );
                loadNews(source,false);
            }
        }



    }

    private void loadNews( String source, boolean isRefreshed) {
        Log.e(TAG, "loadNews: start method to get articles" );
        Log.e(TAG, "loadNews: source is "+source +"and is refreshed "+isRefreshed );

        if (!isRefreshed)
        {

            Log.e(TAG, "loadNews: "+"is refreshed" );
            dialog.show();
            mService.getNewsArticles(Common.getApiUrl(source , Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();





                            Log.e(TAG, "onResponse: response articles   first title " +response.body().getArticles().get(0).getTitle());
                            Log.e(TAG, "onResponse: response status is " +response.body().getStatus());
                            Log.e(TAG, "onResponse: headers " +response.headers());
                            Log.e(TAG, "onResponse: message "+response.message() );

                            Log.e(TAG, "onResponse: response total results is " +response.body().getTotalResults());


                            if (response.body().getArticles().size()>0){
                              // Log.e(TAG, "onResponse: articles size =  "+response.body().getArticle().size() );

                                //get First article
                                Picasso.get()
                                        .load(response.body().getArticles().get(0).getUrlToImage())
                                        .into(kvb);

                                topTitle.setText(response.body().getArticles().get(0).getTitle());
                                topAuthor.setText(response.body().getArticles().get(0).getAuthor());

                                webHotUrl = response.body().getArticles().get(0).getUrl();


                                //load Remain Articles
                                List<Article> removeFirstItem = response.body().getArticles();
                                removeFirstItem.remove(0);
                                adapter = new ListNewsAdapter(removeFirstItem,getBaseContext());
                                adapter.notifyDataSetChanged();
                                lstNews.setAdapter(adapter);


                            }
                            Log.e(TAG, "onResponse: "+response.body().getArticles().size());
                            adapter = new ListNewsAdapter(response.body().getArticles(),getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            Log.e(TAG, "onFailure: news "+"something went wrong" );
                            Log.e(TAG, "onFailure: "+call.toString());
                            Log.e(TAG, "onFailure: "+t.getMessage()+"\n\n"+t.toString());

                        }
                    });

        }


        else {
            dialog.show();
            mService.getNewsArticles(Common.getApiUrl(source , Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {

                            dialog.dismiss();

                            //get First article
                            Picasso.get()
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kvb);

                            topTitle.setText(response.body().getArticles().get(0).getTitle());
                            topAuthor.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();


                            //load Remain Articles
                            List<Article> removeFirstItem = response.body().getArticles();
                            removeFirstItem.remove(0);
                            adapter = new ListNewsAdapter(removeFirstItem,getBaseContext());
                            adapter.notifyDataSetChanged();
                            lstNews.setAdapter(adapter);


                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });

            swipeLayout.setRefreshing(false);


        }

    }


}
