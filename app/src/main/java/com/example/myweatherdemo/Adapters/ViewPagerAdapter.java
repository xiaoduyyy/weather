package com.example.myweatherdemo.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.Fragments.MainActivityFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    List<WeatherBean> weatherList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<WeatherBean> weatherList) {
        super(fragmentActivity);
        this.weatherList = weatherList;
    }

    public ViewPagerAdapter(@NonNull Fragment fragment, List<WeatherBean> weatherList) {
        super(fragment);
        this.weatherList = weatherList;
    }


    public List<WeatherBean> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherBean> weatherList) {
        this.weatherList = weatherList;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<WeatherBean> weatherList) {
        super(fragmentManager, lifecycle);
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置传递不同的 WeatherBean 给 Fragment
        return MainActivityFragment.newInstance(weatherList.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }
}
