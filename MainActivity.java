package example.com.newsreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;
import static example.com.newsreader.Api.BASE_URL;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit = null;
    private News news;
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.newslist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        loadData();

    }

    public void loadData(){
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("New News for You");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api api = retrofit.create(Api.class);
            // Set up progress before call

            Call<News> listCall = api.getNews("in", "business", "79823dff4bc04f52a6f672f40c37eda7");
            listCall.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    progressDoalog.dismiss();
                    news = response.body();
                    mAdapter = new MyAdapter(news.getArticles(), getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            progressDoalog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                startActivity(new Intent(this, NewsActivity.class));
                return true;
            case R.id.refresh:
//                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                loadData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
