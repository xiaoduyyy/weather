package com.example.myweatherdemo.Beans;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "alarm_details_table",
        foreignKeys = @ForeignKey(entity = WeatherBean.class,
                parentColumns = "id",
                childColumns = "weatherId",
                onDelete = ForeignKey.CASCADE))
public class AlarmDetailsBean implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int weatherId;  // 用于关联WeatherBean
    @SerializedName("alarm_type")
    private String alarm_type;

    @SerializedName("alarm_level")
    private String alarm_level;

    @SerializedName("alarm_content")
    private String alarm_content;

    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getAlarm_level() {
        return alarm_level;
    }

    public void setAlarm_level(String alarm_level) {
        this.alarm_level = alarm_level;
    }

    public String getAlarm_content() {
        return alarm_content;
    }

    public void setAlarm_content(String alarm_content) {
        this.alarm_content = alarm_content;
    }

    @Override
    public String toString() {
        return "AlarmDetailsBean{" +
                "alarm_type='" + alarm_type + '\'' +
                ", alarm_level='" + alarm_level + '\'' +
                ", alarm_content='" + alarm_content + '\'' +
                '}';
    }
}
