package com.example.project_lv1_mobile.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.IntroProductActivity;
import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.dao.ProductDAO;
import com.example.project_lv1_mobile.model.Member;
import com.example.project_lv1_mobile.model.Product;
import com.example.project_lv1_mobile.model.ProductType;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private List<Product> productList;
    private final ProductDAO productDAO;
    private String idMember;

    public ProductAdapter(Context context, List<Product> productList, ProductDAO productDAO, String idMember) {
        this.context = context;
        this.productList = productList;
        this.productDAO = productDAO;
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
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageProduct;
        TextView txtTenSP, txtGiaSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageProduct = itemView.findViewById(R.id.ivImageProduct);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(((Activity) context), IntroProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idProduct", productList.get(getAdapterPosition()).getIdProduct());
                    bundle.putString("idMember", idMember);
                    intent.putExtras(bundle);
                    ((Activity) context).startActivity(intent);
                }
            });

            DocumentReference reference = FirebaseFirestore.getInstance().collection("MEMBER").document(idMember);
            reference.get().addOnCompleteListener(task -> {
                DocumentSnapshot snapshot = task.getResult();
                Member member = snapshot.toObject(Member.class);

                if (member.getRank() == 0) {
                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Product product = productList.get(getAdapterPosition());

                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("TYPE")
                                    .document(product.getIdProductType());
                            documentReference.get().addOnCompleteListener(taskType -> {
                                DocumentSnapshot documentSnapshot = taskType.getResult();
                                ProductType productType = documentSnapshot.toObject(ProductType.class);

                                openDialogUpdateProduct(product, productType);
                            });

                            return false;
                        }
                    });
                }
            });


        }
    }

    private void openDialogUpdateProduct(Product product, ProductType productType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_product, null);
        builder.setView(view);
        Dialog dialogDialogUp = builder.create();
        dialogDialogUp.show();

        TextView txtFillProductTypeUp = view.findViewById(R.id.txtFillProductTypeUp);
        EditText edtProductNameUp = view.findViewById(R.id.edtProductNameUp);
        EditText edtDonGiaUp = view.findViewById(R.id.edtDonGiaUp);
        TextView txtFillStatusUp = view.findViewById(R.id.txtFillStatusUp);
        TextView txtNgungKinhDoanh = view.findViewById(R.id.txtNgungKinhDoanh);
        TextView txtKinhDoanh = view.findViewById(R.id.txtKinhDoanh);
        TextView txtUpdateProductSub = view.findViewById(R.id.txtUpdateProductSub);
        TextView txtCancelUpProduct = view.findViewById(R.id.txtCancelUpProduct);

        txtFillProductTypeUp.setText(productType.getNameProductType());
        edtProductNameUp.setText(product.getProductName());
        edtDonGiaUp.setText(Integer.toString(product.getUnitPrice()));

        String status = product.getStatus() == 0 ? "Kinh Doanh" : "Ngừng kinh doanh";
        txtFillStatusUp.setText(status);

        if (product.getStatus() == 0) {
            txtKinhDoanh.setVisibility(View.GONE);
        } else if (product.getStatus() == 1) {
            txtNgungKinhDoanh.setVisibility(View.GONE);
        }

        txtKinhDoanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Lưu ý");
                builder.setMessage("Xác nhận thay đổi này!");

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        product.setStatus(0);
                        productDAO.updateProduct(product);

                        Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
                        dialogDialogUp.dismiss();
                        ((Activity) context).recreate();
                    }
                });

                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialogDisable = builder.create();
                dialogDisable.show();
            }
        });

        txtNgungKinhDoanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Lưu ý");
                builder.setMessage("Xác nhận thay đổi này!");

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        product.setStatus(1);
                        productDAO.updateProduct(product);

                        Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
                        dialogDialogUp.dismiss();
                        ((Activity) context).recreate();
                    }
                });

                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialogDisable = builder.create();
                dialogDisable.show();
            }
        });

        txtCancelUpProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDialogUp.dismiss();
            }
        });

        txtUpdateProductSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtProductNameUp.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Không được để trống tên sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtDonGiaUp.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Không được để trống đơn giá", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = edtProductNameUp.getText().toString();
                int donGia;
                try {
                    donGia = Integer.parseInt(edtDonGiaUp.getText().toString());
                    if (donGia <= 0) {
                        Toast.makeText(context, "Đơn giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException ex) {
                    Toast.makeText(context, "Giá phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                product.setProductName(name);
                product.setUnitPrice(donGia);

                productDAO.updateProduct(product);
                dialogDialogUp.dismiss();
                ((Activity) context).recreate();
            }
        });
    }
}
