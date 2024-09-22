package com.example.myweatherdemo.Others;

import android.util.Log;

import java.io.IOException;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetUtil {

    public static final String URL_WEATHER = "https://v1.yiketianqi.com/api?unescape=1&version=v9&appid=65691699&appsecret=4kL6bwx1";

    public static final String URL_CITY = "http://ykyaqi.tianqiapi.com/?version=alist&citycode=370100&appid=65691699&appsecret=4kL6bwx1";

    private static final String URL_CITY_DAY = "https://geoapi.qweather.com/v2/city/lookup?";
    private static final String API_KEY = "8d10337174fe4491894596b13e2155bb";

    public static String doGet(String urlStr) throws IOException {
        String result = "";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlStr)
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return result;
        }
    }

    public static String getWeatherOfCity(String city) throws IOException {
        String weatherUrl = URL_WEATHER + "&city=" + city;
        Log.d("fan11", "------------------weatherUrl------- " + weatherUrl);

        String weatherResult = doGet(weatherUrl);
        Log.d("fan11", "------------------weatherResult------- " + weatherResult);


        return weatherResult;
    }
    public static String getCitys(String name) throws IOException {
        String weatherUrl = URL_CITY_DAY + "location=" + name + "&key=" + API_KEY + "&range=cn";
        Log.d("fan22", "------------------weatherUrl------- " + weatherUrl);

        String cityResult = doGet(weatherUrl);
        Log.d("fan22", "------------------weatherResult------- " + cityResult);


        return cityResult;
    }
}
