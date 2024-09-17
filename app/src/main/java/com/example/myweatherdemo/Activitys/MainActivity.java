package com.example.myweatherdemo.Activitys;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.myweatherdemo.Adapters.ViewPagerAdapter;
import com.example.myweatherdemo.Beans.AlarmDetailsBean;
import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.OtherTipsBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Fragments.MainActivityFragment;
import com.example.myweatherdemo.Others.NetUtil;
import com.example.myweatherdemo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner mSpinner;
    private ArrayAdapter<String> mStringAdapter;
    private FusedLocationProviderClient fusedLocationClient;



    private WeatherBean weatherBean;

    private Button searchButton;

    private EditText searchCityText;


    List<WeatherBean> weatherBeanList = new ArrayList<>();
    ViewPagerAdapter myAdapter;

    private TabLayout tab_layout;
    private ViewPager2 main_viewpager;


    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("fan", "---------收到了---------" + weather);

                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);

                if (weatherBean == null) {
                    Log.e("fan", "解析后的 WeatherBean 为空");
                    return; // 如果 weatherBean 为空，直接返回
                }

                weatherBeanList.add(weatherBean);

                Log.d("fan", "-------------解析后的数据----------" + weatherBean.toString());

                // 通知适配器数据发生变化
                myAdapter.notifyDataSetChanged();

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



//        tab_layout = findViewById(R.id.tab_layout);
        main_viewpager = findViewById(R.id.main_viewpager);
        initViewPager2(main_viewpager);









        searchButton = findViewById(R.id.search_button_title);
        searchCityText = findViewById(R.id.search_text_textview);




        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundResource(R.drawable.sunny_background);

//        // 其他初始化代码
//        initView();






    }

    //加载viewpager2
    private void initViewPager2(ViewPager2 viewPager2) {

        // 初始化适配器并传入 weatherBeanList
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), weatherBeanList);
        viewPager2.setAdapter(myAdapter);

        fetchWeatherDataForCity("北京");
        fetchWeatherDataForCity("西安");
        fetchWeatherDataForCity("上海");


    }


//    private void initView() {
////        mSpinner = findViewById(R.id.city_item);
//        mCities = getResources().getStringArray(R.array.cities);
//        mStringAdapter = new ArrayAdapter<>(this, R.layout.sp_item, mCities);
//        mSpinner.setAdapter(mStringAdapter);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedCity = parent.getItemAtPosition(position).toString();
//                fetchWeatherDataForCity(selectedCity);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

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