package com.example.myweatherdemo.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myweatherdemo.Activitys.AlarmMessageActivity;
import com.example.myweatherdemo.Activitys.MainActivity;
import com.example.myweatherdemo.Activitys.SevenDaysWeatherActivity;
import com.example.myweatherdemo.Beans.AlarmDetailsBean;
import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.OtherTipsBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Others.MyBottomSheetDialogFragment;
import com.example.myweatherdemo.R;

import java.io.Serializable;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class MainActivityFragment extends Fragment {


    private static final String ARG_WEATHER = "city";

    private WeatherBean weatherBean;

    private ImageView weatherPicture;

    private TextView location, nowTempreture, weather, highAndLowTempreture;

    private TextView humidityTextview, pressureTextview, windDerectionTextview, windTextview, sunRiseTextview
            , sunSetTextview, visibilityTextview, airQuality, uvRays;

    private TextView alarmTypeAndLevel, alarmContent;

    private CardView alarmCardText, airQualityCardView;

    private TextView todayWeatherTextview, todayTemperatureTextview, tomorrowWeatherTextview
            , tomorrowTemperatureTextview, aftertomorrowWeatherTextview, aftertomorrowTemperatureTextview;

    private ImageView todayWeatherImageview, tomorrowWeatherImageview, aftertomorrowWeatherImageview;

    private Button checkWeatherForSevenDaysButton;







    public MainActivityFragment() {

    }


    public static MainActivityFragment newInstance(WeatherBean weatherBean) {
        MainActivityFragment fragment = new MainActivityFragment(); // 创建 Fragment 实例

        Bundle args = new Bundle(); // 创建 Bundle 对象
        args.putSerializable(ARG_WEATHER, weatherBean); // 将 WeatherBean 对象放入 Bundle
        fragment.setArguments(args); // 将 Bundle 设置给 Fragment

        return fragment; // 返回实例化的 Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // 从 Bundle 中取出 WeatherBean
            weatherBean = (WeatherBean) getArguments().getSerializable(ARG_WEATHER);
            if (weatherBean != null) {
                Log.d("WeatherBean", weatherBean.toString());
            } else {
                Log.e("WeatherBean", "WeatherBean is null");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        //初始化其他视图
        location = view.findViewById(R.id.location);
        nowTempreture = view.findViewById(R.id.temperature);
        weather = view.findViewById(R.id.weather_text);
        highAndLowTempreture = view.findViewById(R.id.high_and_low_tempreture_text);
        humidityTextview = view.findViewById(R.id.humidity_textview);
        pressureTextview = view.findViewById(R.id.pressure_textview);
        windDerectionTextview = view.findViewById(R.id.windDerection_textview);
        windTextview = view.findViewById(R.id.wind_textview);
        visibilityTextview = view.findViewById(R.id.visibility_textview);
        sunRiseTextview = view.findViewById(R.id.sunrise_textview);
        sunSetTextview = view.findViewById(R.id.sunset_textview);
        airQuality = view.findViewById(R.id.airqulity_textview);
        weatherPicture = view.findViewById(R.id.weather_picture);
        uvRays = view.findViewById(R.id.rays_textview);
        alarmCardText = view.findViewById(R.id.alarm_card_text);
        alarmContent = view.findViewById(R.id.alarm_content_text);
        alarmTypeAndLevel = view.findViewById(R.id.alarm_typeandlevel_text);
        airQualityCardView = view.findViewById(R.id.airqulity_cardview);
        //天气信息
        todayWeatherTextview = view.findViewById(R.id.today_weather_textview);
        todayWeatherImageview = view.findViewById(R.id.today_weather_imageview);
        todayTemperatureTextview = view.findViewById(R.id.today_temperature_textview);
        tomorrowWeatherTextview = view.findViewById(R.id.tomorrow_weather_textview);
        tomorrowWeatherImageview = view.findViewById(R.id.tomorrow_weather_imageview);
        tomorrowTemperatureTextview = view.findViewById(R.id.tomorrow_temperature_textview);
        aftertomorrowWeatherTextview = view.findViewById(R.id.aftertomorrow_weather_textview);
        aftertomorrowWeatherImageview = view.findViewById(R.id.aftertomorrow_weather_imageview);
        aftertomorrowTemperatureTextview = view.findViewById(R.id.aftertomorrow_temperature_textview);

        checkWeatherForSevenDaysButton = (Button) view.findViewById(R.id.weather_report_details_button);





        //设置天气信息
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




        // 预警详情点击事件
        alarmCardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlarmMessageActivity.class);
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
                bottomSheet.show(getActivity().getSupportFragmentManager(), "BottomSheetDialog");
            }
        });





        //查看近七日天气
        checkWeatherForSevenDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DayWeatherBean> dayWeatherBeans =  weatherBean.getDaysWeather();
                Intent intent = new Intent(getActivity(), SevenDaysWeatherActivity.class);
                intent.putExtra("weatherBean", (Serializable) weatherBean);
                startActivity(intent);
            }
        });


        return view;
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

}