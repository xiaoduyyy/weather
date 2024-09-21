package com.example.myweatherdemo.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_data")
public class WeatherDataEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "weather_json")
    public String weatherJson; // 存储 JSON 数据

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeatherJson() {
        return weatherJson;
    }

    public void setWeatherJson(String weatherJson) {
        this.weatherJson = weatherJson;
    }

    @Override
    public String
    toString() {
        return "WeatherDataEntity{" +
                "id=" + id +
                ", weatherJson='" + weatherJson + '\'' +
                '}';
    }
}
