package com.example.myweatherdemo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.R;


public class MainActivityFragment extends Fragment {


    private static final String ARG_WEATHER = "city";

    private WeatherBean weatherBean;



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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);


        return view;
    }
}