package example.com.newsreader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://newsapi.org/v2/";
//?country=in&category=business&apiKey=79823dff4bc04f52a6f672f40c37eda7
    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );
}
