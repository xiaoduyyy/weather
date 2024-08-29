package com.example.myweatherdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myweatherdemo.util.NetUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner mSpinner;
    private ArrayAdapter<String> mStringAdapter;
    private String[] mCities;
    private FusedLocationProviderClient fusedLocationClient;

    private ImageView weatherPicture;

    private TextView location, nowTempreture, weather, highAndLowTempreture;

    private TextView humidityTextview, pressureTextview, windDerectionTextview, windTextview, sunRiseTextview, sunSetTextview, visibilityTextview, airQuality;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("fan", "---------收到了---------" + weather);

                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                Log.d("fan", "-------------解析后的数据----------" + weatherBean.toString());
                upDateUiOfWeather(weatherBean);
//                try {
//                    JSONObject json = new JSONObject(weather);
//                    parseWeatherData(json); // 解析并更新UI
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }
    };

    private void upDateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null) {
            return;
        }

        List<DayWeatherBean> daysWeather = weatherBean.getDaysWeather();
        DayWeatherBean todayWeather = daysWeather.get(0);


        location.setText(weatherBean.getCity());
        nowTempreture.setText(todayWeather.getTem() + "℃");
        weather.setText(todayWeather.getWea());
        highAndLowTempreture.setText("最高" + todayWeather.getTem1() + "° 最低" + todayWeather.getTem2() +"°");
        humidityTextview.setText(todayWeather.getHumidity());
        pressureTextview.setText(todayWeather.getPressure());
        windDerectionTextview.setText(todayWeather.getWin()[0]);
        windTextview.setText(todayWeather.getWin_meter());
        sunRiseTextview.setText(todayWeather.getSunrise());
        sunSetTextview.setText(todayWeather.getSunset());
        visibilityTextview.setText(todayWeather.getVisibility());
        airQuality.setText(todayWeather.getAir_level());

        WeatherIconSet(todayWeather.getWea());

        if (todayWeather == null) {
            return;
        }
    }

    private void WeatherIconSet(String weather) {
        switch(weather) {
            case "晴":
                weatherPicture.setImageResource(R.drawable.weather_qing);
                break;
            case "阴":
                weatherPicture.setImageResource(R.drawable.weather_yin);
                break;
            case "雨":
                weatherPicture.setImageResource(R.drawable.xiaoyu);
                break;
            case "雪":
                weatherPicture.setImageResource(R.drawable.xiaoxue);
                break;
            case "多云":
                weatherPicture.setImageResource(R.drawable.duoyun);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        location = findViewById(R.id.location);
        nowTempreture = findViewById(R.id.temperature);
        weather = findViewById(R.id.weather_text);
        highAndLowTempreture = findViewById(R.id.high_and_low_tempreture_text);



        humidityTextview = findViewById(R.id.humidity_textview);
        pressureTextview = findViewById(R.id.pressure_textview);
        windDerectionTextview = findViewById(R.id.windDerection_textview);
        windTextview = findViewById(R.id.wind_textview);
        visibilityTextview = findViewById(R.id.visibility_textview);
        sunRiseTextview = findViewById(R.id.sunrise_textview);
        sunSetTextview = findViewById(R.id.sunset_textview);
        airQuality = findViewById(R.id.airqulity_textview);
        weatherPicture = findViewById(R.id.weather_picture);

        // 设置背景
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundResource(R.drawable.sunny_background);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        initView();


    }

    private void initView() {
        mSpinner = findViewById(R.id.city_item);
        mCities = getResources().getStringArray(R.array.cities);
        mStringAdapter = new ArrayAdapter<>(this, R.layout.sp_item, mCities);
        mSpinner.setAdapter(mStringAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();

                fetchWeatherDataForCity(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void fetchWeatherDataForCity(String cityName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String weatherOfCity = NetUtil.getWeatherOfCity(cityName);
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = weatherOfCity;

                    mHandler.sendMessage(message);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        //        String apiKey = "af81b5f7de21f481fa56e644e2395bcd";
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey + "&units=metric";
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String responseData = response.body().string();
//                    try {
//                        JSONObject json = new JSONObject(responseData);
//                        parseWeatherData(json);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

//    @SuppressLint("SetTextI18n")
//    private void parseWeatherData(JSONObject json) throws JSONException {
//        JSONObject main = json.getJSONObject("main");
//        JSONObject wind = json.getJSONObject("wind");
//        JSONObject clouds = json.getJSONObject("clouds");
//
//        int humidity = main.getInt("humidity");
//        double pressure = main.getDouble("pressure");
//        double windSpeed = wind.getDouble("speed");
//        int cloudCover = clouds.getInt("all");
//
//        // 直接从根 JSON 对象中获取 visibility
//        int visibility = json.getInt("visibility");
//
//        // 如果需要 UV 指数，使用 One Call API 获取
//        double uvi = 0; // 设置默认值
//
//
//        runOnUiThread(() -> {
//            humidityTextview.setText(humidity + "％");
//            pressureTextview.setText(pressure + " hPa");
//            windTextview.setText(windSpeed + " m/s");
//            visibilityTextview.setText(visibility + " m");
//            cloudCoverTextview.setText(cloudCover + "％");
//        });
//    }


}