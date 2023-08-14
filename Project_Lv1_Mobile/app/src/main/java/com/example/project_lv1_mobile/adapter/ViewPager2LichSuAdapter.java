package com.example.project_lv1_mobile.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_lv1_mobile.fragment.FragmentLichSuNhap;
import com.example.project_lv1_mobile.fragment.FragmentLichSuXuat;
import com.example.project_lv1_mobile.fragment.FragmentNhap;
import com.example.project_lv1_mobile.fragment.FragmentXuat;

public class ViewPager2LichSuAdapter extends FragmentStateAdapter {

    private Bundle bundle;

    public ViewPager2LichSuAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle bundle) {
        super(fragmentManager, lifecycle);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                FragmentLichSuXuat fragmentLichSuXuat = new FragmentLichSuXuat();
                fragmentLichSuXuat.setArguments(bundle);
                return fragmentLichSuXuat;
            case 1:
                FragmentLichSuNhap fragmentLichSuNhap = new FragmentLichSuNhap();
                fragmentLichSuNhap.setArguments(bundle);
                return fragmentLichSuNhap;
        }
        FragmentLichSuXuat fragmentLichSuXuat = new FragmentLichSuXuat();
        fragmentLichSuXuat.setArguments(bundle);
        return fragmentLichSuXuat;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
