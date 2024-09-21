package com.example.myweatherdemo.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Adapters.CityItemAdapter;
import com.example.myweatherdemo.Adapters.ViewPagerAdapter;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchForCitysActivity extends AppCompatActivity {

    private View titleBarInclude;
    private Button backButton;

    private Button searchButton;
    RecyclerView mRecyclerView;

    private EditText searchCityText;

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


        Intent intent = getIntent();
        ArrayList<WeatherBean> weatherBeanList = (ArrayList<WeatherBean>) intent.getSerializableExtra("WeatherBeanList");

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
        searchButton = (Button) findViewById(R.id.search_for_city_button);
        searchCityText = findViewById(R.id.searchcity_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new CityItemAdapter(weatherBeanList));

        //搜索城市
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SearchForCitysActivity.this, AddCityActivity.class);
                intent1.putExtra("CityName", searchCityText.getText().toString());
                startActivity(intent1);
            }
        });



    }
}