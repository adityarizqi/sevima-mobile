package com.sevima.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sevima.mobile.R;
import com.sevima.mobile.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private final Context context;
    private final List<News> newsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());

        CircularProgressDrawable drawable = new CircularProgressDrawable(context);
        drawable.setColorSchemeColors(R.color.blue_200, R.color.blue_500, R.color.blue_700);
        drawable.setCenterRadius(30f);
        drawable.setStrokeWidth(5f);
        drawable.start();

        Glide.with(context).load(news.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(drawable)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,price,content;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_view);
            price = itemView.findViewById(R.id.price_view);
            content = itemView.findViewById(R.id.content_view);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

}
