package com.example.myweatherdemo.Beans;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class WeatherBean implements Serializable {


    @SerializedName("cityid")
    private String cityid;

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("data")
    private List<DayWeatherBean> daysWeather;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<DayWeatherBean> getDaysWeather() {
        return daysWeather;
    }

    public void setDaysWeather(List<DayWeatherBean> daysWeather) {
        this.daysWeather = daysWeather;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "cityid='" + cityid + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", daysWeather=" + daysWeather +
                '}';
    }
}
