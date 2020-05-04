package com.example.cook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cook.entity.show.ShowCarEntity;

import java.util.List;

@Dao
public interface CookShowCardDao {

    @Insert
    void insertCook(ShowCarEntity... showCarEntities);

    @Delete
    void deleteCook(ShowCarEntity... showCarEntities);

    @Update
    void updateCook(ShowCarEntity... showCarEntities);

    @Query("DELETE FROM ShowCarEntity")
    void deleteAllCooks();

    @Query("SELECT * FROM ShowCarEntity ORDER BY ID DESC")
    LiveData<List<ShowCarEntity>> getAllCooksLive();
    @Query("SELECT * FROM ShowCarEntity WHERE  cookType LIKE :pattern ORDER BY ID DESC")
    LiveData<List<ShowCarEntity>> findzaoWithPattern(String pattern);

    @Query("SELECT * FROM ShowCarEntity WHERE  title LIKE :pattern ORDER BY ID DESC")
    LiveData<List<ShowCarEntity>> findCookWithPattern(String pattern);
    @Query("SELECT * FROM ShowCarEntity WHERE  username LIKE :pattern ORDER BY ID DESC")
    LiveData<List<ShowCarEntity>> findCookusernamePattern(String pattern);
    @Query("SELECT * FROM ShowCarEntity WHERE  t1 LIKE :pattern ORDER BY ID DESC")
    LiveData<List<ShowCarEntity>> findtype(String pattern);
}
