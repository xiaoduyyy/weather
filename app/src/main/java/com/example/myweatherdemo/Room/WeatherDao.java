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

    // 根据 ID 删除特定数据
    @Query("DELETE FROM weather_data WHERE id = :weatherId")
    void deleteWeatherDataById(int weatherId);

    // 根据 cityId 删除特定数据
    @Query("DELETE FROM weather_data WHERE city_id = :cityId")
    void deleteWeatherDataByCityId(String cityId);

    // 根据城市名称查询
    @Query("SELECT * FROM weather_data WHERE city_id = :cityName LIMIT 1")
    WeatherDataEntity findCityByName(String cityName);
}
