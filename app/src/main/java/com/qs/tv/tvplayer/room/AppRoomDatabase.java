package com.qs.tv.tvplayer.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.qs.tv.tvplayer.room.dao.PlayerItemDao;
import com.qs.tv.tvplayer.room.entity.PlayerItem;

@Database(entities = {PlayerItem.class}, version = 1)
@TypeConverters({Converters.class, MapConverters.class})
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract PlayerItemDao playerItemDao();

    public static final String DB_NAME = "tvPlayer";

    private static AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppRoomDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public static void refreshInstance(Context context) {
        INSTANCE = null;
        getInstance(context);
    }
}
