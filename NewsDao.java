package example.com.newsreader;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insertNews(NewsDataModel newsDataModel);

    @Query("SELECT * FROM news")
    List<NewsDataModel> getAll();

}
