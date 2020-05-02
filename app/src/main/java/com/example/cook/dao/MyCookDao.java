package com.example.cook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cook.entity.MyCookEntity;

import java.util.List;

@Dao
public interface MyCookDao {


    @Insert
    void insert(MyCookEntity... myCookEntities);

    @Update
    void update(MyCookEntity... myCookEntities);

    @Delete
    void delete(MyCookEntity... myCookEntities);

    @Query("   SELECT * FROM MyCookEntity ORDER BY ID DESC ")
    LiveData<List<MyCookEntity>> findAllCollect();

    @Query("DELETE  FROM MyCookEntity  ")
    void deleteAllCollect();

    @Query("SELECT * FROM MyCookEntity WHERE  Title LIKE :pattern ORDER BY ID DESC  ")
    LiveData<List<MyCookEntity>> findCookWithPattern(String pattern);
}
