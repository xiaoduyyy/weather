package com.example.myweatherdemo.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherDataEntity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
