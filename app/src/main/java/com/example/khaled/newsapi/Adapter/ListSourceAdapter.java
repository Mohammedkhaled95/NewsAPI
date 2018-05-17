package com.example.khaled.newsapi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khaled.newsapi.Common.Common;
import com.example.khaled.newsapi.Interface.IconBetterService;
import com.example.khaled.newsapi.Interface.ItemClickListener;
import com.example.khaled.newsapi.ListNews;
import com.example.khaled.newsapi.Model.IconBetterIdea;
import com.example.khaled.newsapi.Model.WebSite;
import com.example.khaled.newsapi.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by khaled on 04/05/18.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListener itemClickListener;

    MaterialLetterIcon sourceImage;
    TextView sourceTitle;





    public ListSourceViewHolder(View itemView) {
        super(itemView);

        sourceImage = (MaterialLetterIcon) itemView.findViewById(R.id.source_image);
        sourceTitle = (TextView) itemView.findViewById(R.id.source_name);
        






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


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{

    private static final String TAG = "ListSourceAdapter";
    private Context context;
    private WebSite webSite;
    private IconBetterService mService;

     int clickedSource;
    public String firstLetter;




    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService(webSite.getSources().get(clickedSource).getUrl());


    }


    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {





/*
        clickedSource = position;
        StringBuilder iconBetterApi = new StringBuilder(webSite.getSources().get(position).getUrl());
        iconBetterApi.append("/favicon.ico");

      mService.getIconUrl(iconBetterApi.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {

                IconBetterIdea iconBetterIdea = response.body();

                if (iconBetterIdea.getIcons().size()>0){





                    if (response.body().getIcons().size() > 0){
                        if (response.body().getIcons().get(0).getUrl() != null){
                            Picasso.get()
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(holder.sourceImage);
                        }
                        else{
                            holder.sourceImage.setImageResource(R.drawable.index);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {

                Log.e(TAG, "  onFailure: image not found" );
                Log.e(TAG,"    local message "+ t.getLocalizedMessage() );
                Log.e(TAG, " message "+t.getMessage() );
                Log.e(TAG,   " tostring "+t.toString() );

            }
        });
*/
        firstLetter = String.valueOf(webSite.getSources().get(position).getName().toString().substring(0,1));


        holder.sourceImage.setInitials(true);
        holder.sourceImage.setLetterSize(26);
        holder.sourceImage.setShapeColor(R.color.colorBackground);
        holder.sourceImage.setLetterColor(R.color.colorLetter);
        holder.sourceImage.setLettersNumber(2);

        holder.sourceImage.setLetter(firstLetter);


        holder.sourceImage.setShapeType(MaterialLetterIcon.Shape.RECT);



        holder.sourceTitle.setText(webSite.getSources().get(position).getName());








        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNews.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("source",webSite.getSources().get(position).getId());
                Log.e(TAG, "onClick: website get source "+webSite.getSources().toString());
                Log.e(TAG, "onClick: website  sources numbers "+webSite.getSources().size());
                Log.e(TAG, "onClick: id of a selected source"+webSite.getSources().get(position).getId());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
