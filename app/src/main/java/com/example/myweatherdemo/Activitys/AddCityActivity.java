package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Adapters.MyWeatherAdapter;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Others.NetUtil;
import com.example.myweatherdemo.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class AddCityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private WeatherBean weatherBean;

    private TextView cityNameTextView, addCityTextView;

    private Button addCityButoon, backButton;

    private int isLive = 0;
    private View titleBarInclude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.addcity_cityweather_recyclerview);
        cityNameTextView = findViewById(R.id.addcity_cityname_textview);
        addCityButoon = (Button) findViewById(R.id.addcity_commit_button);
        addCityTextView = findViewById(R.id.addcity_add_or_go_textview);


        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CityName");
        fetchWeatherDataForCity(cityName);
        ArrayList<String> cityNames = intent.getStringArrayListExtra("CityNames");

        for (String name : cityNames) {
            if (name.equals(cityName)) {
                isLive = 1;
                addCityButoon.setBackgroundResource(R.drawable.goto_icon);
                addCityTextView.setText("前往主页查看");
            }
        }

        addCityButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                if (isLive == 0) {
                    intent.putExtra("addCityName", cityName);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //返回
        titleBarInclude = findViewById(R.id.title_bar_include);
        backButton = titleBarInclude.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("fan", "---------收到了---------" + weather);

                Gson gson = new Gson();
                // 将解析后的 weatherBean 赋值给全局变量
                weatherBean = gson.fromJson(weather, WeatherBean.class);

                if (weatherBean == null) {
                    Log.e("fan", "解析后的 WeatherBean 为空");
                    return; // 如果 weatherBean 为空，直接返回
                }

                // 在这里你可以调用更新 UI 的方法
                updateUI();
            }
        }
    };

    private void updateUI() {
        cityNameTextView.setText(weatherBean.getCity());
        // 初始化 Adapter 并绑定数据到 RecyclerView
        MyWeatherAdapter myWeatherAdapter = new MyWeatherAdapter(weatherBean.getDaysWeather());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myWeatherAdapter);
    }

}