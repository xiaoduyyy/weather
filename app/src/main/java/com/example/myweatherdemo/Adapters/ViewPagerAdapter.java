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

    List<WeatherBean> mWeatherList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<WeatherBean> weatherList) {
        super(fragmentActivity);
        this.mWeatherList = weatherList;
    }

    public ViewPagerAdapter(@NonNull Fragment fragment, List<WeatherBean> weatherList) {
        super(fragment);
        this.mWeatherList = weatherList;
    }


    public List<WeatherBean> getmWeatherList() {
        return mWeatherList;
    }

    public void setmWeatherList(List<WeatherBean> mWeatherList) {
        this.mWeatherList = mWeatherList;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<WeatherBean> weatherList) {
        super(fragmentManager, lifecycle);
        this.mWeatherList = weatherList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置传递不同的 WeatherBean 给 Fragment
        return MainActivityFragment.newInstance(mWeatherList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWeatherList == null ? 0 : mWeatherList.size();
    }

    public List<WeatherBean> updateData(List<WeatherBean> weatherList) {
        this.mWeatherList = weatherList;
        notifyDataSetChanged();
        return weatherList;
    }
}
