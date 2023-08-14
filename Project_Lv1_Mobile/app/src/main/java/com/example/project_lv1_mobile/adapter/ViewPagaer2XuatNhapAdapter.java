package com.example.project_lv1_mobile.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_lv1_mobile.fragment.FragmentNhap;
import com.example.project_lv1_mobile.fragment.FragmentXuat;

public class ViewPagaer2XuatNhapAdapter extends FragmentStateAdapter {

    private Bundle bundle;
    public ViewPagaer2XuatNhapAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle bundle) {
        super(fragmentManager, lifecycle);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                FragmentXuat fragmentXuat = new FragmentXuat();
                fragmentXuat.setArguments(bundle);
                return fragmentXuat;
            case 1:
                FragmentNhap fragmentNhap = new FragmentNhap();
                fragmentNhap.setArguments(bundle);
                return fragmentNhap;
        }
        FragmentXuat fragmentXuat = new FragmentXuat();
        fragmentXuat.setArguments(bundle);
        return fragmentXuat;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
