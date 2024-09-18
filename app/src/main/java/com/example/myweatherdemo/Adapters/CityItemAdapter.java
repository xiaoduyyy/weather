package com.example.myweatherdemo.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.CityItemViewHolder> {
    @NonNull
    @Override
    public CityItemAdapter.CityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CityItemAdapter.CityItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CityItemViewHolder extends RecyclerView.ViewHolder {


        public CityItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
