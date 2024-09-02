package com.example.myweatherdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_CONTENT = "arg_content";

    private String title;
    private String content;

    public static MyBottomSheetDialogFragment newInstance(String title, String content) {
        MyBottomSheetDialogFragment fragment = new MyBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取传递过来的参数
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
        }

        // 设置 TextView 的内容
        TextView textViewTitle = view.findViewById(R.id.airquality_details_title);
        textViewTitle.setText(title);

        TextView textViewContent = view.findViewById(R.id.airquality_details_content);
        textViewContent.setText(content);
    }
}

