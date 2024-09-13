package com.example.myweatherdemo.Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

// "day":"15日（星期三）",
//         "date":"2020-07-15",
//         "week":"星期三",
//         "wea":"阴", #实时天气情况
//            "wea_img":"yin", #天气情况对应图标
//            "wea_day":"阵雨", #白天天气情况
//            "wea_day_img":"lei",
//                    "wea_night":"阵雨", #夜间天气情况
//            "wea_night_img":"lei",
//                    "tem":"25", #当前温度
//            "tem1":"29", #高温
//            "tem2":"23", #低温
//            "humidity":"97%", #湿度
//            "visibility":"14.22km", #能见度
//            "pressure":"961", #气压
//            "win":[
//                    "无持续风向", #白天风向
//                "无持续风向"  #夜间风向
//            ],
//                    "win_speed":"<3级", #风速等级
//            "win_meter":"小于12km/h", #风速
//            "sunrise":"06:09", #日出时间
//            "sunset":"20:01",  #日落时间
//            "air":"14", #空气质量
//            "air_level":"优",
//                    "air_tips":"空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！",
//                    "alarm":{ #预警信息
//                "alarm_type":"暴雨", #预警类型
//                "alarm_level":"橙色", #预警等级
//                "alarm_content":"内江市气象台7月15日5时23分发布第16号暴雨橙色预警信号:
//预计我市市中区部分地方3小时降雨量将达50毫米以上，并伴有雷电和阵性大风，请加强防范强降水、雷电和大风等带来的危害。
//        （预警信息来源：国家预警信息发布中心）" #预警详情描述
//        },

public class DayWeatherBean implements Serializable {



    @SerializedName("day")
    private String day;

    @SerializedName("date")
    private String date;

    @SerializedName("week")
    private String week;

    @SerializedName("wea")
    private String wea;

    @SerializedName("wea_day")
    private String wea_day;

    @SerializedName("wea_night")
    private String wea_night;

    @SerializedName("tem")
    private String tem;

    @SerializedName("tem1")
    private String tem1;

    @SerializedName("tem2")
    private String tem2;

    @SerializedName("humidity")
    private String humidity;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("pressure")
    private String pressure;

    @SerializedName("win")
    private String[] win;

    @SerializedName("win_meter")
    private String win_meter;

    @SerializedName("sunrise")
    private String sunrise;

    @SerializedName("sunset")
    private String sunset;

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    @SerializedName("air")
    private String air;

    @SerializedName("air_level")
    private String air_level;

    @SerializedName("air_tips")
    private String air_tips;

    @SerializedName("index")
    private List<OtherTipsBean> mTipsBeans;

    @SerializedName("alarm")
    private AlarmDetailsBean alarm;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getAir_tips() {
        return air_tips;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_day() {
        return wea_day;
    }

    public List<OtherTipsBean> getmTipsBeans() {
        return mTipsBeans;
    }

    public void setmTipsBeans(List<OtherTipsBean> mTipsBeans) {
        this.mTipsBeans = mTipsBeans;
    }

    public void setWea_day(String wea_day) {
        this.wea_day = wea_day;
    }

    public String getWea_night() {
        return wea_night;
    }

    public void setWea_night(String wea_night) {
        this.wea_night = wea_night;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String[] getWin() {
        return win;
    }

    public void setWin(String[] win) {
        this.win = win;
    }

    public String getWin_meter() {
        return win_meter;
    }

    public void setWin_meter(String win_meter) {
        this.win_meter = win_meter;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public AlarmDetailsBean getAlarm() {
        return alarm;
    }

    public void setAlarm(AlarmDetailsBean alarm) {
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        return "DayWeatherBean{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_day='" + wea_day + '\'' +
                ", wea_night='" + wea_night + '\'' +
                ", tem='" + tem + '\'' +
                ", tem1='" + tem1 + '\'' +
                ", tem2='" + tem2 + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", win=" + Arrays.toString(win) +
                ", win_meter='" + win_meter + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", air='" + air + '\'' +
                ", air_level='" + air_level + '\'' +
                ", air_tips='" + air_tips + '\'' +
                ", mTipsBeans=" + mTipsBeans +
                ", alarm=" + alarm +
                '}';
    }
}
