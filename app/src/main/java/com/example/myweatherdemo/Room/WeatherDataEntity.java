package com.example.myweatherdemo.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_data")
public class WeatherDataEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "city_id")
    public String cityId; // 城市ID

    @ColumnInfo(name = "weather_json")
    public String weatherJson; // 存储 JSON 数据

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getWeatherJson() {
        return weatherJson;
    }

    public void setWeatherJson(String weatherJson) {
        this.weatherJson = weatherJson;
    }

    @Override
    public String toString() {
        return "WeatherDataEntity{" +
                "id=" + id +
                ", cityId='" + cityId + '\'' +
                ", weatherJson='" + weatherJson + '\'' +
                '}';
    }
}
