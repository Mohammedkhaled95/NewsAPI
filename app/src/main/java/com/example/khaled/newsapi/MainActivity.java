package com.example.khaled.newsapi;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.khaled.newsapi.Adapter.ListSourceAdapter;
import com.example.khaled.newsapi.Common.Common;
import com.example.khaled.newsapi.Interface.NewsService;
import com.example.khaled.newsapi.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG ="MainActivity" ;
    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    android.app.AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init paper lib
        Paper.init(this);
        mService = Common.getNewsService();

        //init views
        listWebsite = (RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        listWebsite.setLayoutManager(layoutManager);
        dialog = new SpotsDialog(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSources(true);
            }
        });

        loadWebsiteSources(false);





        
    }

    private void loadWebsiteSources(boolean isRefreshed) {


        if (!isRefreshed){

            String cash = Paper.book().read("cash");

            if (cash != null && !cash.isEmpty() && !cash.equals("null"))
            {
                //some data cashed
                WebSite webSite = new Gson().fromJson(cash , WebSite.class);
                adapter = new ListSourceAdapter(getBaseContext() , webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);

            }
            else{  //if not have cash

                //no data cashed so make a retrofit request
                Log.e(TAG, "loadWebsiteSources: "+"not have cash and may start mservice" );

                dialog.show();
                //Log.e(TAG, "loadWebsiteSources: "+mService.getSources().toString() );

                    mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                        adapter = new ListSourceAdapter(getBaseContext() , response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);


                        Paper.book().write("cash" , new Gson().toJson(response.body()));
                        dialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });


            }

        }else{      //  from swipe refreshing so make another Rerofit call
            Log.i(TAG, "loadWebsiteSources: from swipe");
            swipeRefreshLayout.setRefreshing(true);




            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                    adapter = new ListSourceAdapter(getBaseContext() , response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //saving data
                    Paper.book().write("cash" , new Gson().toJson(response.body()));

                    //hide alertDialog
                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });

        }


    }
}
