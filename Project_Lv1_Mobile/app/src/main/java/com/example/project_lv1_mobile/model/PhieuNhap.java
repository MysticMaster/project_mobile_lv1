package com.example.project_lv1_mobile.model;

import java.io.Serializable;
import java.util.HashMap;

public class PhieuNhap implements Serializable {
    private String idPhieuNhap, idMember, idProduct, ngayNhap;
    private int soLuongNhap, tongTien;

    public PhieuNhap() {
    }

    public PhieuNhap(String idPhieuNhap, String idMember, String idProduct, String ngayNhap, int soLuongNhap, int tongTien) {
        this.idPhieuNhap = idPhieuNhap;
        this.idMember = idMember;
        this.idProduct = idProduct;
        this.ngayNhap = ngayNhap;
        this.soLuongNhap = soLuongNhap;
        this.tongTien = tongTien;
    }

    public String getIdPhieuNhap() {
        return idPhieuNhap;
    }

    public PhieuNhap setIdPhieuNhap(String idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
        return this;
    }

    public String getIdMember() {
        return idMember;
    }

    public PhieuNhap setIdMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public PhieuNhap setIdProduct(String idProduct) {
        this.idProduct = idProduct;
        return this;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public PhieuNhap setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
        return this;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public PhieuNhap setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
        return this;
    }

    public int getTongTien() {
        return tongTien;
    }

    public PhieuNhap setTongTien(int tongTien) {
        this.tongTien = tongTien;
        return this;
    }

    public HashMap<String, Object> objectPhieuNhap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("idPhieuNhap", this.idPhieuNhap);
        data.put("idMember", this.idMember);
        data.put("idProduct", this.idProduct);
        data.put("ngayNhap", this.ngayNhap);
        data.put("soLuongNhap", this.soLuongNhap);
        data.put("tongTien", this.tongTien);

        return data;
    }
}
