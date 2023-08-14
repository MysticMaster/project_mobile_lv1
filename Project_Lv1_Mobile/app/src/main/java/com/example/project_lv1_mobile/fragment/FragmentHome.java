package com.example.project_lv1_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project_lv1_mobile.NavigationActivity;
import com.example.project_lv1_mobile.ProductActivity;
import com.example.project_lv1_mobile.ProductTypeActivity;
import com.example.project_lv1_mobile.R;

import org.checkerframework.common.subtyping.qual.Bottom;


public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnQLLoaiSP = rootView.findViewById(R.id.btnQLLoaiSP);
        Button btnQLSP = rootView.findViewById(R.id.btnQLSP);

        Bundle bundle = getArguments();

        btnQLLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProductType = new Intent(getActivity(), ProductTypeActivity.class);
                toProductType.putExtras(bundle);
                startActivity(toProductType);
            }
        });

        btnQLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProduct = new Intent(getActivity(), ProductActivity.class);
                toProduct.putExtras(bundle);
                startActivity(toProduct);
            }
        });

        return rootView;
    }
}