package com.example.myweatherdemo.Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HoursWeatherBean implements Serializable {
    @SerializedName("hours")
    private String hours;

    @SerializedName("wea")
    private String wea;

    @SerializedName("tem")
    private String tem;

    @SerializedName("win")
    private String win;

    @SerializedName("win_speed")
    private String win_speed;


    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    @Override
    public String toString() {
        return "HoursWeatherBean{" +
                "hours='" + hours + '\'' +
                ", wea='" + wea + '\'' +
                ", tem='" + tem + '\'' +
                ", win='" + win + '\'' +
                ", win_speed='" + win_speed + '\'' +
                '}';
    }

}
