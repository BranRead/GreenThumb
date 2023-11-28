package com.example.greenthumb;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Plant.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class PlantDatabase extends RoomDatabase {
    public abstract PlantDao plantDao();
}
