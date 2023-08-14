package com.example.project_lv1_mobile.tempSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TempDbHelper extends SQLiteOpenHelper {

    public TempDbHelper(@Nullable Context context) {
        super(context, "DatabaseTemp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablePhieuNhapCT = "CREATE TABLE PhieuNhapChiTiet(" +
                "idPhieuNhapCT TEXT PRIMARY KEY," +
                "idMember TEXT," +
                "idProduct TEXT," +
                "soLuongNhap INTEGER," +
                "soTien INTEGER);";
        db.execSQL(tablePhieuNhapCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String dropTablePhieuNhapCT = "DROP TABLE IF EXISTS PhieuNhapChiTiet";
            db.execSQL(dropTablePhieuNhapCT);
        }
    }
}
