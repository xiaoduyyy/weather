package com.example.myweatherdemo.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myweatherdemo.Beans.WeatherBean;

import java.util.List;

@Dao
public interface WeatherDao {
    @Insert
    void insertWeatherData(WeatherDataEntity weatherData);

    @Query("SELECT * FROM weather_data")
    List<WeatherDataEntity> getAllWeatherData();

    @Query("DELETE FROM weather_data") // 清除所有数据
    void clearAllWeatherData();
}
