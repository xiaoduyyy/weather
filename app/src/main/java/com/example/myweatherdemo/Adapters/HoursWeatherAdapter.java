package com.example.myweatherdemo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.HoursWeatherBean;
import com.example.myweatherdemo.R;

import java.util.List;

public class HoursWeatherAdapter extends RecyclerView.Adapter<HoursWeatherAdapter.HoursWeatherHolder> {

    private List<HoursWeatherBean> hoursWeatherBeanList;

    public HoursWeatherAdapter(List<HoursWeatherBean> hoursWeatherBeanList) {
        this.hoursWeatherBeanList = hoursWeatherBeanList;
    }

    @NonNull
    @Override
    public HoursWeatherAdapter.HoursWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_weather_card, parent, false);
        HoursWeatherHolder hoursWeatherHolder = new HoursWeatherHolder(view);
        return hoursWeatherHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HoursWeatherAdapter.HoursWeatherHolder holder, int position) {
        HoursWeatherBean hoursWeatherBean = hoursWeatherBeanList.get(position);
        holder.hour.setText(hoursWeatherBean.getHours());
        holder.weather.setText(hoursWeatherBean.getWea());
        holder.temperature.setText(hoursWeatherBean.getTem() + "°");
    }

    @Override
    public int getItemCount() {
        return hoursWeatherBeanList == null ? 0 : hoursWeatherBeanList.size();
    }

    public class HoursWeatherHolder extends RecyclerView.ViewHolder {

        TextView hour, weather, temperature;

        public HoursWeatherHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.hours_time);
            weather = itemView.findViewById(R.id.hours_weather);
            temperature = itemView.findViewById(R.id.hours_temperature);
        }
    }
}
