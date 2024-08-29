package com.example.myweatherdemo.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;

public class NetUtil {

    public static final String URL_WEATHER = "https://v1.yiketianqi.com/api?unescape=1&version=v9&appid=65691699&appsecret=4kL6bwx1";

    public static String doGet(String urlStr) throws IOException {
        String result = "";
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL urL = new URL(urlStr);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);

            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return result;
    }

    public static String getWeatherOfCity(String city) throws IOException {
        String weatherUrl = URL_WEATHER + "&city=" + city;
        Log.d("fan", "------------------weatherUrl------- " + weatherUrl);

        String weatherResult = doGet(weatherUrl);
        Log.d("fan", "------------------weatherResult------- " + weatherUrl);

        return weatherResult;
    }
}
