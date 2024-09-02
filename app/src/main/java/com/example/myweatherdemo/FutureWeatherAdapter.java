package com.example.myweatherdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {

    private Context mContext;
    private List<DayWeatherBean> mWeatherBeans;

    public FutureWeatherAdapter(Context context, List<DayWeatherBean> weatherBeans) {
        mContext = context;
        this.mWeatherBeans = weatherBeans;
    }


    class WeatherViewHolder extends RecyclerView.ViewHolder{

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (mWeatherBeans == null) ? 0 : mWeatherBeans.size();
    }
}
