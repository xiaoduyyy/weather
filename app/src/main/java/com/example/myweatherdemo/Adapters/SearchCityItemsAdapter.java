package com.example.myweatherdemo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherdemo.R;

import java.util.List;

public class SearchCityItemsAdapter extends RecyclerView.Adapter<SearchCityItemsAdapter.SearchCityItemsViewHolder> {

    List<String> citys;
    private OnItemClickListener onItemClickListener;

    // 定义点击事件接口
    public interface OnItemClickListener {
        void onItemClick(String cityInfo);
    }

    // 构造函数中传入点击监听器
    public SearchCityItemsAdapter(List<String> citys, OnItemClickListener listener) {
        this.citys = citys;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public SearchCityItemsAdapter.SearchCityItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_recyclerview_item, parent, false);
        return new SearchCityItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCityItemsAdapter.SearchCityItemsViewHolder holder, int position) {
        String cityInfo = citys.get(position);
        holder.cityItem.setText(cityInfo);

        String[] parts = cityInfo.split("——");  // 按 "——" 切割
        String cityName = parts[0];  // 获取第一个部分
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(cityName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citys == null ? 0 : citys.size();
    }

    public class SearchCityItemsViewHolder extends RecyclerView.ViewHolder {
        TextView cityItem;

        public SearchCityItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            cityItem = itemView.findViewById(R.id.cityName_item);
        }
    }

    public void updateCityList(List<String> newCityList) {
        this.citys = newCityList;
        notifyDataSetChanged();
    }
}

