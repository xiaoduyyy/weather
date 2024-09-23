package com.example.myweatherdemo.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.Beans.DayWeatherBean;
import com.example.myweatherdemo.Beans.WeatherBean;
import com.example.myweatherdemo.R;
import com.example.myweatherdemo.Room.WeatherDao;
import com.example.myweatherdemo.Room.WeatherDataEntity;

import java.time.temporal.Temporal;
import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.CityItemViewHolder> {

    List<WeatherBean> weatherList;
    WeatherDao weatherDao;
    Context context;

    public CityItemAdapter(List<WeatherBean> weatherList) {
        this.weatherList = weatherList;
    }

    public CityItemAdapter(List<WeatherBean> weatherList, WeatherDao weatherDao) {
        this.weatherList = weatherList;
        this.weatherDao = weatherDao;
    }

    public CityItemAdapter(List<WeatherBean> weatherList, WeatherDao weatherDao, Context context) {
        this.weatherList = weatherList;
        this.weatherDao = weatherDao;
        this.context = context;
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

        // 为 itemView 设置长按删除事件
        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(position);  // 调用删除方法
            return true;
        });
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

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("确认删除")
                .setMessage("您确定要删除这个项目吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    removeItem(position);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void removeItem(int position) {
        if (position >= 0 && position < weatherList.size()) {
            WeatherBean weatherBean = weatherList.get(position);

            // 在数据库中删除该条记录
            new Thread(() -> {
                weatherDao.deleteWeatherDataByCityId(weatherBean.getCity()); // 根据城市 ID 删除

                // 使用 Handler 更新 UI
                new Handler(Looper.getMainLooper()).post(() -> {
                    // 从列表中删除数据并通知适配器更新视图
                    weatherList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, weatherList.size()); // 通知剩余项位置更新
                });
            }).start();
        } else {
            Log.e("CityItemAdapter", "Invalid position: " + position);
        }
    }
}
