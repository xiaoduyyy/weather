package com.example.myweatherdemo.Others;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class FadePageTransformer implements ViewPager2.PageTransformer{
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 1) {
            page.setAlpha(1 - Math.abs(position));
        } else {
            page.setAlpha(0);
        }
    }
}
