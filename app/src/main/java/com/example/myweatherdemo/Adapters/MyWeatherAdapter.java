package com.example.myweatherdemo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.R;

import java.util.List;

public class MyWeatherAdapter extends RecyclerView.Adapter<MyWeatherAdapter.MyViewHolder> {

    private List<DayWeatherBean> dayWeatherBeans;

    public MyWeatherAdapter(List<DayWeatherBean> dayWeatherBeans) {
        this.dayWeatherBeans = dayWeatherBeans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seven_days_weather_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DayWeatherBean dayWeather = dayWeatherBeans.get(position);
        holder.date.setText(dayWeather.getDay());
        holder.weatherName.setText(dayWeather.getWea());
        WeatherIconSet(holder.weatherImage, dayWeather.getWea());
        holder.weatherTemperature.setText(dayWeather.getTem2() + "°" + "~" + dayWeather.getTem1() + "°");
    }

    //更新天气小图标
    private void WeatherIconSet(ImageView imageView, String weather) {
        switch (weather) {
            case "晴":
                imageView.setImageResource(R.drawable.weather_qing);
                break;
            case "阴":
                imageView.setImageResource(R.drawable.weather_yin);
                break;
            case "雨":
            case "小雨":
            case "中雨":
            case "大雨":
            case "暴雨":
                imageView.setImageResource(R.drawable.xiaoyu);
                break;
            case "雪":
            case "小雪":
            case "中雪":
            case "大雪":
                imageView.setImageResource(R.drawable.xiaoxue);
                break;
            case "多云":
                imageView.setImageResource(R.drawable.duoyun);
                break;
            case "雾":
                imageView.setImageResource(R.drawable.weather_wu);
                break;
            default:
                imageView.setImageResource(R.drawable.weather_error);
                break;
        }
    }
    @Override
    public int getItemCount() {
        return dayWeatherBeans == null ? 0 : dayWeatherBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, weatherTemperature, weatherName;

        ImageView weatherImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            weatherTemperature = itemView.findViewById(R.id.weather_temperature);
            weatherName = itemView.findViewById(R.id.weather_name);
            weatherImage = itemView.findViewById(R.id.weather_image);
        }
    }


}
