package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myweatherdemo.Adapters.CityItemAdapter;
import com.example.myweatherdemo.Adapters.SearchCityItemsAdapter;
import com.example.myweatherdemo.Adapters.ViewPagerAdapter;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Others.NetUtil;
import com.example.myweatherdemo.R;
import com.example.myweatherdemo.Room.WeatherDao;
import com.example.myweatherdemo.Room.WeatherDataEntity;
import com.example.myweatherdemo.Room.WeatherDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SearchForCitysActivity extends AppCompatActivity {
    private final static String TAG = "SearchForCitysActivity";



    private View titleBarInclude;
    private Button backButton;

    private Button searchButton;
    private RecyclerView mRecyclerView, cityItemRecyclerView;

    private EditText searchCityText;
    private WeatherDatabase weatherDatabase;
    private List<WeatherBean> weatherList;

    private List<String> cityNames = new ArrayList<>();

    private SearchView searchView;

    private List<String> citys = new ArrayList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                String city = (String) msg.obj;
                Log.d("fan222", "---------收到了---------" + city);

                if (city == null) {
                    Toast.makeText(SearchForCitysActivity.this, "网络请求失败！！", Toast.LENGTH_SHORT).show();
                    return;
                }

                parseToCityString(city);
                cityItemRecyclerView.getAdapter().notifyDataSetChanged();

            }
        }
    };

    private void parseToCityString(String cityJson) {
        List<String> cityList = new ArrayList<>();

        // 将 JSON 字符串解析为 JsonObject
        JsonObject jsonObject = JsonParser.parseString(cityJson).getAsJsonObject();

        // 获取 "location" 数组
        JsonArray jsonArray = jsonObject.getAsJsonArray("location");

        if (jsonArray == null) {
            Log.e(TAG, "Location array is null");
            runOnUiThread(() -> Toast.makeText(SearchForCitysActivity.this, "未能获取到城市信息", Toast.LENGTH_SHORT).show());
            return;
        }

        // 遍历数组中的每个元素
        for (JsonElement jsonElement : jsonArray) {
            JsonObject cityObject = jsonElement.getAsJsonObject();

            // 获取城市名称和行政区信息
            String name = cityObject.get("name").getAsString();
            String adm1 = cityObject.get("adm1").getAsString();
            String adm2 = cityObject.get("adm2").getAsString();

            // 组合为所需的格式
            String cityInfo = name + "——" + adm1 + "——" + adm2;
            cityList.add(cityInfo);
        }

        citys = cityList;
        Log.d(TAG, "parseToCityString: xian" + citys);
        runOnUiThread(() -> {
            SearchCityItemsAdapter adapter = (SearchCityItemsAdapter) cityItemRecyclerView.getAdapter();
            if (adapter != null) {
                adapter.updateCityList(citys);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_citys);
        getSupportActionBar().hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        fetchCitys();

//        Intent intent = getIntent();
//        ArrayList<WeatherBean> weatherBeanList = (ArrayList<WeatherBean>) intent.getSerializableExtra("WeatherBeanList");

        weatherDatabase = Room.databaseBuilder(this, WeatherDatabase.class, "weather-database").build();
        WeatherDao weatherDao = weatherDatabase.weatherDao();




        cityItemRecyclerView = findViewById(R.id.search_recyclerview);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        cityItemRecyclerView.setLayoutManager(layoutManager1);
        // 创建适配器并传入点击事件监听器
        SearchCityItemsAdapter adapter = new SearchCityItemsAdapter(citys, new SearchCityItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String cityInfo) {
                Intent intent1 = new Intent(SearchForCitysActivity.this, AddCityActivity.class);
                intent1.putExtra("CityName", cityInfo);
                intent1.putStringArrayListExtra("CityNames", (ArrayList<String>) cityNames);
                startActivity(intent1);
            }
        });
        cityItemRecyclerView.setAdapter(adapter);

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                weatherList = new ArrayList<>();
                List<WeatherDataEntity> allWeatherData = weatherDao.getAllWeatherData();
                for (WeatherDataEntity allWeatherDatum : allWeatherData) {
                    String weatherJson = allWeatherDatum.getWeatherJson();
                    Gson gson = new Gson();
                    WeatherBean weatherBean = gson.fromJson(weatherJson, WeatherBean.class);
                    weatherList.add(weatherBean);
                    cityNames.add(weatherBean.getCity());
                    Log.d(TAG, "run: " + weatherBean);
                }
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //返回
        titleBarInclude = findViewById(R.id.title_bar_include);
        backButton = titleBarInclude.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.citys_item_recyclerview);
//        searchButton = (Button) findViewById(R.id.search_for_city_button);
//        searchCityText = findViewById(R.id.searchcity_text);
        searchView = findViewById(R.id.searchView);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new CityItemAdapter(weatherList, weatherDao, this));

//        //搜索城市
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(SearchForCitysActivity.this, AddCityActivity.class);
//                intent1.putExtra("CityName", searchCityText.getText().toString());
//                intent1.putStringArrayListExtra("CityNames", (ArrayList<String>) cityNames);
//                startActivity(intent1);
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // 显示 RecyclerView
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // 隐藏 RecyclerView
                    mRecyclerView.setVisibility(View.GONE);
                    fetchCitys(newText);  // 调用获取城市的方法
                }
                return true;
            }
        });

    }
    private void fetchCitys(String city) {
        if (city == null || city.trim().isEmpty()) {
            return;  // 不进行网络请求
        }
        new Thread(() -> {
            try {
                Log.d(TAG, "fetchCitys: 111111111111111111111111111");
                String weatherOfCity = NetUtil.getCitys(city);
                Log.d(TAG, "fetchCitys: 2222222222222222222222222222");

                if (weatherOfCity != null) {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = weatherOfCity;
                    mHandler.sendMessage(message);
                } else {
                    runOnUiThread(() -> Toast.makeText(SearchForCitysActivity.this, "未能获取城市数据", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SearchForCitysActivity.this, "网络请求失败：" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}