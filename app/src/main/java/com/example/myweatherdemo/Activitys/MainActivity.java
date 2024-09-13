package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myweatherdemo.Beans.AlarmDetailsBean;
import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.OtherTipsBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Others.MyBottomSheetDialogFragment;
import com.example.myweatherdemo.Others.NetUtil;
import com.example.myweatherdemo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner mSpinner;
    private ArrayAdapter<String> mStringAdapter;
    private String[] mCities;
    private FusedLocationProviderClient fusedLocationClient;

    private Button searchButton;

    private EditText searchCityText;

    private ImageView weatherPicture;

    private TextView location, nowTempreture, weather, highAndLowTempreture;

    private TextView humidityTextview, pressureTextview, windDerectionTextview, windTextview, sunRiseTextview
            , sunSetTextview, visibilityTextview, airQuality, uvRays;

    private TextView alarmTypeAndLevel, alarmContent;

    private CardView alarmCardText, airQualityCardView;


    private WeatherBean weatherBean;

    private TextView todayWeatherTextview, todayTemperatureTextview, tomorrowWeatherTextview
            , tomorrowTemperatureTextview, aftertomorrowWeatherTextview, aftertomorrowTemperatureTextview;

    private ImageView todayWeatherImageview, tomorrowWeatherImageview, aftertomorrowWeatherImageview;

    private Button checkWeatherForSevenDaysButton;


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
            }
        }
    };

    //更新所有天气信息
    private void upDateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null) {
            return;
        }

        if (weatherBean == null || weatherBean.getDaysWeather() == null || weatherBean.getDaysWeather().isEmpty()) {
            Toast.makeText(this, "您输入的城市有误", Toast.LENGTH_SHORT).show();
            return;
        }

        this.weatherBean = weatherBean;

        List<DayWeatherBean> daysWeather = weatherBean.getDaysWeather();
        DayWeatherBean todayWeather = daysWeather.get(0);
        List<OtherTipsBean> otherTipsBeans = todayWeather.getmTipsBeans();
        OtherTipsBean otherTipsBean = otherTipsBeans.get(0);
        AlarmDetailsBean alarmDetails = todayWeather.getAlarm();

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
        airQuality.setText(" 空气质量 " + todayWeather.getAir_level());
        uvRays.setText(otherTipsBean.getLevel());

        alarmMessageSet(alarmDetails);
        WeatherIconSet(weatherPicture, todayWeather.getWea());


        todayWeatherTextview.setText(todayWeather.getWea());
        WeatherIconSet(todayWeatherImageview, todayWeather.getWea());
        todayTemperatureTextview.setText(todayWeather.getTem2() + "°" + "~" + todayWeather.getTem1() + "°");

        DayWeatherBean tomorrowWeatherBean = daysWeather.get(1);
        tomorrowWeatherTextview.setText(tomorrowWeatherBean.getWea());
        WeatherIconSet(tomorrowWeatherImageview, tomorrowWeatherBean.getWea());
        tomorrowTemperatureTextview.setText(tomorrowWeatherBean.getTem2() + "°" + "~" + tomorrowWeatherBean.getTem1() + "°");

        DayWeatherBean afterTomorrowWeatherBean = daysWeather.get(2);
        aftertomorrowWeatherTextview.setText(afterTomorrowWeatherBean.getWea());
        WeatherIconSet(aftertomorrowWeatherImageview, afterTomorrowWeatherBean.getWea());
        aftertomorrowTemperatureTextview.setText(afterTomorrowWeatherBean.getTem2() + "°" + "~" + afterTomorrowWeatherBean.getTem1() + "°");

        if (todayWeather == null) {
            return;
        }
    }

    //更新预警
    private void alarmMessageSet(AlarmDetailsBean alarmDetails) {
        if (alarmDetails.getAlarm_type().equals("") && alarmDetails.getAlarm_content().equals("")) {
            alarmTypeAndLevel.setText("今日无警报");
            alarmContent.setText("");
        } else {
            alarmTypeAndLevel.setText(alarmDetails.getAlarm_type() + alarmDetails.getAlarm_level() + "预警");
            alarmContent.setText(alarmDetails.getAlarm_content());
        }
    }

    //更新天气小图标
    private void WeatherIconSet(ImageView imageView, String weather) {
        switch(weather) {
            case "晴":
                imageView.setImageResource(R.drawable.weather_qing);
                break;
            case "阴":
                imageView.setImageResource(R.drawable.weather_yin);
                break;
            case "雨":
                imageView.setImageResource(R.drawable.xiaoyu);
                break;
            case "雪":
                imageView.setImageResource(R.drawable.xiaoxue);
                break;
            case "多云":
                imageView.setImageResource(R.drawable.duoyun);
                break;
            case "雾":
                imageView.setImageResource(R.drawable.weather_wu);
                break;
            default:
                imageView.setImageResource(R.drawable.weather_error);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        // 确保布局文件正确加载
//        mSpinner = findViewById(R.id.city_item);



        if (mSpinner != null) {
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
                    // 处理无选择的情况
                }
            });
        } else {
            Log.e("MainActivity", "Spinner not found in layout");
        }

        // 初始化其他视图
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
        uvRays = findViewById(R.id.rays_textview);
        alarmCardText = findViewById(R.id.alarm_card_text);
        alarmContent = findViewById(R.id.alarm_content_text);
        alarmTypeAndLevel = findViewById(R.id.alarm_typeandlevel_text);
        airQualityCardView = findViewById(R.id.airqulity_cardview);


        searchButton = findViewById(R.id.search_button_title);
        searchCityText = findViewById(R.id.search_text_textview);


        //天气信息
        todayWeatherTextview = findViewById(R.id.today_weather_textview);
        todayWeatherImageview = findViewById(R.id.today_weather_imageview);
        todayTemperatureTextview = findViewById(R.id.today_temperature_textview);
        tomorrowWeatherTextview = findViewById(R.id.tomorrow_weather_textview);
        tomorrowWeatherImageview = findViewById(R.id.tomorrow_weather_imageview);
        tomorrowTemperatureTextview = findViewById(R.id.tomorrow_temperature_textview);
        aftertomorrowWeatherTextview = findViewById(R.id.aftertomorrow_weather_textview);
        aftertomorrowWeatherImageview = findViewById(R.id.aftertomorrow_weather_imageview);
        aftertomorrowTemperatureTextview = findViewById(R.id.aftertomorrow_temperature_textview);

        checkWeatherForSevenDaysButton = (Button) findViewById(R.id.weather_report_details_button);


        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundResource(R.drawable.sunny_background);

//        // 其他初始化代码
//        initView();

        // 预警详情点击事件
        alarmCardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmMessageActivity.class);
                String alarmTitle = alarmTypeAndLevel.getText().toString();
                String alarmDetails = alarmContent.getText().toString();
                intent.putExtra("alarm_title", alarmTitle);
                intent.putExtra("alarm_details", alarmDetails);
                startActivity(intent);
            }
        });

        //空气质量点击详情
        airQualityCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DayWeatherBean> daysWeather = weatherBean.getDaysWeather();
                DayWeatherBean todayWeather = daysWeather.get(0);
                MyBottomSheetDialogFragment bottomSheet = MyBottomSheetDialogFragment.newInstance("空气质量 " + todayWeather.getAir_level() + " " + todayWeather.getAir(), "  " + todayWeather.getAir_tips());
                bottomSheet.show(getSupportFragmentManager(), "BottomSheetDialog");
            }
        });

        searchCityText.setVisibility(View.GONE);

        // 查询按钮点击事件
        searchButton.setOnClickListener(new View.OnClickListener() {
            private boolean isEditTextVisible = false;
            @Override
            public void onClick(View v) {
                if (!isEditTextVisible) {
                    // 第一次点击时显示 EditText
                    searchCityText.setVisibility(View.VISIBLE);
                    isEditTextVisible = true;
                } else {
                    fetchWeatherDataForCity(String.valueOf(searchCityText.getText()));
                }
            }
        });


        //查看近七日天气
        checkWeatherForSevenDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DayWeatherBean> dayWeatherBeans =  weatherBean.getDaysWeather();
                Intent intent = new Intent(MainActivity.this, SevenDaysWeatherActivity.class);
                intent.putExtra("weatherBean", (Serializable) weatherBean);
                startActivity(intent);
            }
        });




    }

    private void initView() {
//        mSpinner = findViewById(R.id.city_item);
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
    }

}