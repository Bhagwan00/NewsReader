package example.com.newsreader;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Article> newsList;
    private Context context;

    public MyAdapter(List<Article> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder viewHolder, int i) {
        final Article article = newsList.get(i);
        Glide.with(context).load(article.getUrlToImage()).into(viewHolder.mImageView);
        viewHolder.mTitle.setText(article.getTitle());
        viewHolder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("news_img", article.getUrlToImage());
                intent.putExtra("news_title", article.getTitle());
                intent.putExtra("news_desc", article.getDescription());
                intent.putExtra("news_url", article.getUrl());
                intent.putExtra("is_offline", 0);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.title);
        }
    }
}
