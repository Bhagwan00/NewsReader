package example.com.newsreader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView mTitle, mDesc;
    FloatingActionButton fab;

    private NewsDatabase newsDatabase;

    String url;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageView);
        mTitle = findViewById(R.id.titleDetail);
        mDesc = findViewById(R.id.descDetail);
        fab = findViewById(R.id.fab);

        final String title = getIntent().getStringExtra("news_title");
        final String desc = getIntent().getStringExtra("news_desc");
        final String img = getIntent().getStringExtra("news_img");
        url = getIntent().getStringExtra("news_url");
        status = getIntent().getIntExtra("is_offline", 1);

        mTitle.setText(title);
        mDesc.setText(desc);
        Glide.with(DetailActivity.this).load(img).into(imageView);


        newsDatabase = NewsDatabase.getDatabase(this);
        if (status == 1) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsDataModel newsDataModel = new NewsDataModel();
                    newsDataModel.setNewsTitle(title);
                    newsDataModel.setNewsDescription(desc);
                    newsDataModel.setNewsImage(img);
                    newsDataModel.setNewsUrl(url);
                    insertNews(newsDataModel);
                    Snackbar.make(view, "News Saved Successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void insertNews(final NewsDataModel newsDataModel) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                newsDatabase.newsDao().insertNews(newsDataModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                Toast.makeText(DetailActivity.this, "News Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

}
