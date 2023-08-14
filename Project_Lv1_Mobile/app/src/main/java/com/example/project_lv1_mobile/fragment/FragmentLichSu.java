package com.example.project_lv1_mobile.fragment;

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
import com.example.project_lv1_mobile.adapter.ViewPager2LichSuAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentLichSu extends Fragment {

    private Context context;

    public FragmentLichSu() {
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
        View rootView = inflater.inflate(R.layout.fragment_lich_su, container, false);

        Bundle bundle = getArguments();

        TabLayout tabLayoutLichSu = rootView.findViewById(R.id.tabLayoutLichSu);
        ViewPager2 viewPager2LichSu = rootView.findViewById(R.id.viewPager2LichSu);
        ViewPager2LichSuAdapter adapter = new ViewPager2LichSuAdapter(getChildFragmentManager(), getLifecycle(), bundle);
        viewPager2LichSu.setAdapter(adapter);

        new TabLayoutMediator(tabLayoutLichSu, viewPager2LichSu, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Lịch sử xuất");
                        break;
                    case 1:
                        tab.setText("Lịch sử nhập");
                        break;
                }
            }
        }).attach();

        return rootView;
    }
}