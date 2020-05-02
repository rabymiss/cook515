package com.example.cook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cook.entity.RegisterEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
     void  insert(RegisterEntity...registerEntities);
    @Query("SELECT * FROM RegisterEntity ORDER BY ID DESC ")
    LiveData<List<RegisterEntity> >findAccount();
    @Query("DELETE  FROM RegisterEntity")
    void deleteAll();
}
