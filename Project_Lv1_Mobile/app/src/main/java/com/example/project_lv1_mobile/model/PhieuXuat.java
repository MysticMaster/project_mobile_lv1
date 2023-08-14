package com.example.project_lv1_mobile.model;

import java.io.Serializable;
import java.util.HashMap;

public class PhieuXuat implements Serializable {
    private String idPhieuXuat, idMember, memberName, ngayXuat;
    private int tongSoSPXuat, tongTien, thue;

    public HashMap<String, Object> objectPhieuXuat() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("idPhieuXuat", this.idPhieuXuat);
        data.put("idMember", this.idMember);
        data.put("ngayXuat", this.ngayXuat);
        data.put("tongTien", this.tongTien);

        return data;
    }
}
