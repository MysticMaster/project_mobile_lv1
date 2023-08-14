package com.example.project_lv1_mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.model.PhieuNhapChiTiet;
import com.example.project_lv1_mobile.model.PhieuXuat;
import com.example.project_lv1_mobile.model.PhieuXuatChiTiet;
import com.example.project_lv1_mobile.model.Product;
import com.example.project_lv1_mobile.tempDAO.PhieuNhapChiTietDAO;
import com.example.project_lv1_mobile.tempDAO.PhieuXuatChiTietDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {

    private final Context context;
    private List<Product> productList;
    private PhieuNhapChiTietDAO phieuNhapChiTietDAO;
    private PhieuXuatChiTietDAO phieuXuatChiTietDAO;
    private int checkRank;
    private String idMember;

    public SearchProductAdapter(Context context, List<Product> productList,
                                PhieuNhapChiTietDAO phieuNhapChiTietDAO,
                                PhieuXuatChiTietDAO phieuXuatChiTietDAO, int checkRank, String idMember) {
        this.context = context;
        this.productList = productList;
        this.phieuNhapChiTietDAO = phieuNhapChiTietDAO;
        this.phieuXuatChiTietDAO = phieuXuatChiTietDAO;
        this.checkRank = checkRank;
        this.idMember = idMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenSP.setText(productList.get(position).getProductName());
        holder.txtGiaSP.setText(Integer.toString(productList.get(position).getUnitPrice()));

        Glide.with(context).load(productList.get(position).getProductImageUri()).into(holder.ivImageProduct);

        Product product = productList.get(position);


        holder.iBtnAddSPChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PhieuNhapChiTiet> nhapChiTietList = new ArrayList<>();
                boolean check = false;
                int size = 0;

                nhapChiTietList.addAll(phieuNhapChiTietDAO.selectAllPNCT(idMember));
                for (int i = 0; i < nhapChiTietList.size(); i++) {
                    size++;
                }

                if (size == 5) {
                    Toast.makeText(context, "Tối đa 5 sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < nhapChiTietList.size(); i++) {
                    if (nhapChiTietList.get(i).getIdProduct().equals(product.getIdProduct())) {
                        check = true;
                        break;
                    }
                }


                if (check == false) {
                    if (checkRank == 0) {
                        inserPhieuNhapChiTiet(product);
                        Toast.makeText(context, "Đã thêm", Toast.LENGTH_SHORT).show();
                    } else if (checkRank == 1) {
                        inserPhieuXuatChiTiet(product);
                    }
                } else {
                    Toast.makeText(context, "Sản phẩm đã được chọn", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageProduct, iBtnAddSPChon;
        TextView txtTenSP, txtGiaSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageProduct = itemView.findViewById(R.id.ivImageProduct);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            iBtnAddSPChon = itemView.findViewById(R.id.iBtnAddSPChon);

        }
    }

    public void inserPhieuNhapChiTiet(Product product) {
        String idPhieuNhapCT = UUID.randomUUID().toString();
        String idProduct = product.getIdProduct();
        int soLuongNhap = 1;
        int soTien = product.getUnitPrice();
        PhieuNhapChiTiet phieuNhapChiTiet = new
                PhieuNhapChiTiet(idPhieuNhapCT, idMember, idProduct, soLuongNhap, soTien);

        phieuNhapChiTietDAO.insert(phieuNhapChiTiet);
    }

    public void inserPhieuXuatChiTiet(Product product) {
        String idPhieuXuatCT = UUID.randomUUID().toString();
        String idProduct = product.getIdProduct();
        int soLuongXuat = 1;
        int soTien = product.getUnitPrice();
        PhieuXuatChiTiet phieuXuatChiTiet = new
                PhieuXuatChiTiet(idPhieuXuatCT, idProduct, soLuongXuat, soTien);
        //  phieuXuatChiTiet.insert(phieuXuatChiTiet);
    }

    public void fillSearch(List<Product> listSearch){
        productList.clear();
        productList.addAll(listSearch);
        notifyDataSetChanged();
    }


}
