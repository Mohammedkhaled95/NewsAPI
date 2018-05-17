package com.example.khaled.newsapi.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khaled.newsapi.Common.ISO8601DateParser;
import com.example.khaled.newsapi.DetailsArticle;
import com.example.khaled.newsapi.Interface.ItemClickListener;
import com.example.khaled.newsapi.Model.Article;
import com.example.khaled.newsapi.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListener itemClickListener;

    TextView articleTitle;
    RelativeTimeTextView articleTime;
    CircleImageView articleImage;




    public ListNewsViewHolder(View itemView) {
        super(itemView);

        articleImage = (CircleImageView) itemView.findViewById(R.id.article_image);
        articleTitle = (TextView) itemView.findViewById(R.id.article_title);
        articleTime = (RelativeTimeTextView) itemView.findViewById(R.id.article_time);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {


    private List<Article> articleList;
    private Context context;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.news_layout,parent,false);
        return new ListNewsViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position) {


        Picasso.get()
                .load(articleList.get(position).getUrlToImage())
                .into(holder.articleImage);
        if (articleList.get(position).getTitle().length() > 65)
            holder.articleTitle.setText(articleList.get(position).getTitle().substring(0,64)+"...");
        else
            holder.articleTitle.setText(articleList.get(position).getTitle()+"...");

        Date date = null;

        try {
            date = ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
        } catch (ParseException e) {

            e.printStackTrace();
        }

    if (date != null){
                holder.articleTime.setReferenceTime(date.getTime());
    }

/*

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent details = new Intent(context , DetailsArticle.class);
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                details.putExtra("webUrl",articleList.get(position).getUrl());
               context.startActivity(details);

            }
        });
*/


        //Set Event Click
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent detail = new Intent(context,DetailsArticle.class);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                detail.putExtra("webUrl",articleList.get(position).getUrl());
                context.startActivity(detail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articleList.size();

    }
}