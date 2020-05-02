package com.example.cook.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cook.dao.CollectCenterDao;
import com.example.cook.dao.CookShowCardDao;
import com.example.cook.dao.MyCookDao;
import com.example.cook.dao.UserDao;
import com.example.cook.entity.CollectCenterEntity;
import com.example.cook.entity.MyCookEntity;
import com.example.cook.entity.RegisterEntity;
import com.example.cook.entity.show.ShowCarEntity;

@Database(entities = {ShowCarEntity.class, CollectCenterEntity.class, MyCookEntity.class, RegisterEntity.class}, version = 1, exportSchema = false)


public abstract class CookDatabase extends RoomDatabase {

    private static CookDatabase INSTANCE;

    public static synchronized CookDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CookDatabase.class, "cook_database").build();

        }
        return INSTANCE;
    }

    public abstract CookShowCardDao getShowCardDao();

    public abstract CollectCenterDao getCollectCenterDao();

    public abstract MyCookDao getMyCookDao();
    public abstract UserDao getUserDao();


}
