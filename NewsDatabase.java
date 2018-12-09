package example.com.newsreader;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsDataModel.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public static NewsDatabase INSTANCE;
    public abstract NewsDao newsDao();

    public static NewsDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news_db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public static void destroyInstance(){
        INSTANCE  = null;
    }
}
