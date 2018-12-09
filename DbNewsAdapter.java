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

public class DbNewsAdapter extends RecyclerView.Adapter<DbNewsAdapter.ViewHolder> {
    private List<NewsDataModel> newsList;
    private Context context;

    public DbNewsAdapter(List<NewsDataModel> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public DbNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new DbNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DbNewsAdapter.ViewHolder viewHolder, int i) {
        final NewsDataModel newsDataModel = newsList.get(i);
        Glide.with(context).load(newsDataModel.getNewsImage()).into(viewHolder.mImageView);
        viewHolder.mTitle.setText(newsDataModel.getNewsTitle());
        viewHolder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("news_img", newsDataModel.getNewsImage());
                intent.putExtra("news_title", newsDataModel.getNewsTitle());
                intent.putExtra("news_desc", newsDataModel.getNewsDescription());
                intent.putExtra("news_url", newsDataModel.getNewsUrl());
                intent.putExtra("is_offline", 1);
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
