package com.example.cook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cook.entity.CollectCenterEntity;

import java.util.List;

@Dao
public interface CollectCenterDao {
    @Insert
    void insert(CollectCenterEntity... collectCenterEntities);

    @Update
    void update(CollectCenterEntity... collectCenterEntities);

    @Delete
    void delete(CollectCenterEntity... collectCenterEntities);

    @Query("   SELECT * FROM CollectCenterEntity ORDER BY ID DESC ")
    LiveData<List<CollectCenterEntity>> findAllCollect();

    @Query("DELETE  FROM CollectCenterEntity  ")
    void deleteAllCollect();

    @Query("SELECT * FROM CollectCenterEntity WHERE  Title LIKE :pattern ORDER BY ID DESC  ")
    LiveData<List<CollectCenterEntity>> findCookWithPattern(String pattern);

}
