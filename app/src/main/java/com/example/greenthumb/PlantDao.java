package com.example.greenthumb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlantDao {
    @Query("SELECT * FROM plant")
    List<Plant> getAll();

    @Insert
    void insertAll(Plant... plants);

    @Delete
    void delete(Plant plant);
}
