package example.com.newsreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class NewsActivity extends AppCompatActivity {
    private DbNewsAdapter adapter;

    private NewsDatabase newsDatabase;

    List<NewsDataModel> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newsDatabase = NewsDatabase.getDatabase(this);

        final RecyclerView mRecyclerView = findViewById(R.id.dbnewslist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        newsList = newsDatabase.newsDao().getAll();
        Log.d("b", ""+newsList.size());
        adapter = new DbNewsAdapter(newsList, NewsActivity.this);
        mRecyclerView.setAdapter(adapter);

    }


}
