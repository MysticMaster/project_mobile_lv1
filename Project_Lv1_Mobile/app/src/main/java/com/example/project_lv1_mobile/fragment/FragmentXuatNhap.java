package com.example.project_lv1_mobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.adapter.ViewPagaer2XuatNhapAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentXuatNhap extends Fragment {

    private Context context;

    public FragmentXuatNhap() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_xuat_nhap, container, false);

        Bundle bundle = getArguments();

        TabLayout tabLayoutXuatNhap = rootView.findViewById(R.id.tabLayoutXuatNhap);
        ViewPager2 viewPager2XuatNhap = rootView.findViewById(R.id.viewPager2XuatNhap);
        ViewPagaer2XuatNhapAdapter adapter = new ViewPagaer2XuatNhapAdapter(getChildFragmentManager(), getLifecycle(), bundle);
        viewPager2XuatNhap.setAdapter(adapter);

        new TabLayoutMediator(tabLayoutXuatNhap, viewPager2XuatNhap, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Xuất Hàng");
                        break;
                    case 1:
                        tab.setText("Nhập Hàng");
                        break;
                }
            }
        }).attach();

        return rootView;
    }
}