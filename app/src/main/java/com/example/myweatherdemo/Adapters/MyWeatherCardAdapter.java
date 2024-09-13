package com.example.myweatherdemo.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.R;

public class MyWeatherCardAdapter extends RecyclerView.Adapter<MyWeatherCardAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyWeatherCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeatherCardAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, highAndLowTemperature, weather, temperature;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.card_location_item);
            highAndLowTemperature = itemView.findViewById(R.id.card_temperature_highandlow_item);
            weather = itemView.findViewById(R.id.card_weather_item);
            temperature = itemView.findViewById(R.id.card_temperature_item);
        }
    }
}
