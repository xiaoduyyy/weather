package com.example.myweatherdemo.Others;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {

    private float startX, startY;
    private boolean isVpDragging;  // 用于标记 ViewPager2 是否正在拖动

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                startY = e.getY();
                getParent().requestDisallowInterceptTouchEvent(true);  // 默认禁止父类拦截
                break;

            case MotionEvent.ACTION_MOVE:
                float endX = e.getX();
                float endY = e.getY();
                float dx = Math.abs(endX - startX);
                float dy = Math.abs(endY - startY);

                // 如果垂直滑动距离大于水平滑动距离，处理 RecyclerView 滑动
                if (dy > dx) {
                    getParent().requestDisallowInterceptTouchEvent(true);  // 允许 RecyclerView 滑动
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false); // 允许 ViewPager2 滑动
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);  // 允许父控件再次拦截事件
                break;
        }
        return super.onInterceptTouchEvent(e);
    }
}
