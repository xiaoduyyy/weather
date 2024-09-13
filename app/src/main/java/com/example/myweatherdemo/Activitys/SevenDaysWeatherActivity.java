package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Adapters.MyWeatherAdapter;
import com.example.myweatherdemo.R;

public class SevenDaysWeatherActivity extends AppCompatActivity {


    private View titleBarInclude;


    private Button backButton;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_days_weather);
        getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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

        mRecyclerView = findViewById(R.id.recyclerview);


        Intent intent = getIntent();
        WeatherBean weatherBean = (WeatherBean) intent.getSerializableExtra("weatherBean");
        MyWeatherAdapter myWeatherAdapter = new MyWeatherAdapter(weatherBean.getDaysWeather());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(myWeatherAdapter);
    }
}