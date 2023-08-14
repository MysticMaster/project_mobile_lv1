package com.example.project_lv1_mobile.tempDAO;

import android.content.Context;

import com.example.project_lv1_mobile.tempSQLite.TempDbHelper;

import java.util.List;

public class PhieuXuatChiTietDAO {

    private final TempDbHelper tempDbHelper;

    public PhieuXuatChiTietDAO(Context context) {
        tempDbHelper = new TempDbHelper(context);
    }

}
