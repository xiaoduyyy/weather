package com.example.myweatherdemo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.R;

import java.time.temporal.Temporal;
import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.CityItemViewHolder> {

    List<WeatherBean> weatherList;


    public CityItemAdapter(List<WeatherBean> weatherList) {
        this.weatherList = weatherList;
    }


    @NonNull
    @Override
    public CityItemAdapter.CityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_card, parent, false);
        CityItemViewHolder cityItemViewHolder = new CityItemViewHolder(view);
        return cityItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityItemAdapter.CityItemViewHolder holder, int position) {
        WeatherBean weatherBean = weatherList.get(position);
        List<DayWeatherBean> daysWeather = weatherBean.getDaysWeather();
        DayWeatherBean todayWeather = daysWeather.get(0);
        holder.location.setText(weatherBean.getCity());
        holder.weather.setText(todayWeather.getWea());
        holder.temperature.setText(todayWeather.getTem2() + "°" + "/" + todayWeather.getTem1() + "°");
        holder.currentTemperature.setText(todayWeather.getTem() + "°");
    }

    @Override
    public int getItemCount() {
        return weatherList == null ? 0 : weatherList.size();
    }

    public class CityItemViewHolder extends RecyclerView.ViewHolder {

        TextView location, weather, temperature, currentTemperature;

        public CityItemViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.card_location_item);
            weather = itemView.findViewById(R.id.card_weather_item);
            temperature = itemView.findViewById(R.id.card_temperature_highandlow_item);
            currentTemperature = itemView.findViewById(R.id.card_temperature_item);
        }
    }
}
