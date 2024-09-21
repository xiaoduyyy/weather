package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myweatherdemo.Adapters.ViewPagerAdapter;
import com.example.myweatherdemo.Beans.AlarmDetailsBean;
import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.OtherTipsBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Fragments.MainActivityFragment;
import com.example.myweatherdemo.Others.NetUtil;
import com.example.myweatherdemo.R;
import com.example.myweatherdemo.Room.WeatherDao;
import com.example.myweatherdemo.Room.WeatherDataEntity;
import com.example.myweatherdemo.Room.WeatherDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import me.relex.circleindicator.CircleIndicator3;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private WeatherBean weatherBean;

    private Button addButton;

    private EditText searchCityText;

    List<WeatherBean> weatherBeanList = new ArrayList<>();

    ViewPagerAdapter myAdapter;

    private ViewPager2 main_viewpager;

    private WeatherDatabase db;

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("fan", "---------收到了---------" + weather);

                WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
                weatherDataEntity.weatherJson = weather;
                new Thread(() -> db.weatherDao().insertWeatherData(weatherDataEntity)).start();

                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);

                if (weatherBean == null) {
                    Log.e("fan", "解析后的 WeatherBean 为空");
                    return; // 如果 weatherBean 为空，直接返回
                }

                loadAllWeatherData();

                Log.d("fan", "-------------解析后的数据----------" + weatherBean.toString());


//                upDateUiOfWeather(weatherBean);
            }
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadAllWeatherData() {
        new Thread(() -> {
            List<WeatherDataEntity> entities = db.weatherDao().getAllWeatherData();
            List<WeatherBean> loadedWeatherBeans = new ArrayList<>();
            for (WeatherDataEntity entity : entities) {
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(entity.weatherJson, WeatherBean.class);
                loadedWeatherBeans.add(weatherBean);
                // 处理解析后的 weatherBean，比如更新 UI 或保存到列表
                Log.d("WeatherData", "城市天气数据: " + weatherBean.toString());
            }
            // 更新主线程中的 weatherBeanList
            runOnUiThread(() -> {
                myAdapter.updateData(loadedWeatherBeans);
            });
        }).start();
    }


//    //更新所有天气信息
//    private void upDateUiOfWeather(WeatherBean weatherBean) {
//        if (weatherBean == null) {
//            return;
//        }
//
//        if (weatherBean.getDaysWeather() == null || weatherBean.getDaysWeather().isEmpty()) {
//            Toast.makeText(this, "您输入的城市有误", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//    }

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

        db = Room.databaseBuilder(this,
                WeatherDatabase.class, "weather-database").build();

        main_viewpager = findViewById(R.id.main_viewpager);
        initViewPager2(main_viewpager);

        addButton = findViewById(R.id.add_button_title);
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundResource(R.drawable.sunny_background);



        Intent intentGetCity = getIntent();
        if (intentGetCity.getStringExtra("addCityName") != null) {
            String addCityName = intentGetCity.getStringExtra("addCityName");
            if (addCityName != null) {
                Log.d("addCityName", "onCreate: 接收到传递的城市");
                fetchWeatherDataForCity(addCityName);
            } else {
                Log.e("addCityName", "onCreate: 未能接收到传递");
            }
        } else {
            Log.d(TAG, "onCreate: nidielaile");
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchForCitysActivity.class);
                intent.putExtra("WeatherBeanList", (Serializable) weatherBeanList);
                startActivity(intent);
            }
        });

    }

    //加载viewpager2
    private void initViewPager2(ViewPager2 viewPager2) {
        WeatherDao weatherDao = db.weatherDao();
        weatherBeanList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<WeatherDataEntity> entities = weatherDao.getAllWeatherData();
                for (WeatherDataEntity entity : entities) {
                    Gson gson = new Gson();
                    WeatherBean weatherBean = gson.fromJson(entity.weatherJson, WeatherBean.class);
                    weatherBeanList.add(weatherBean);
                    // 处理解析后的 weatherBean，比如更新 UI 或保存到列表
                    Log.d("WeatherData", "城市天气数据: " + weatherBean.toString());
                }
                latch.countDown();
            }
        }).start();
        Log.d(TAG, "initViewPager2: " + weatherBeanList);
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 初始化适配器并传入 weatherBeanList
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), weatherBeanList);
        viewPager2.setAdapter(myAdapter);

        CircleIndicator3 indicator = findViewById(R.id.main_indicator);
        indicator.setViewPager(viewPager2);
        myAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
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