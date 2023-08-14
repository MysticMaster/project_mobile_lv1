package com.example.project_lv1_mobile.adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.IntroProductActivity;
import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.firebase.FirebaseCRUD;
import com.example.project_lv1_mobile.model.Member;
import com.example.project_lv1_mobile.model.Product;
import com.example.project_lv1_mobile.model.ProductType;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private List<Product> productList;

    private Bundle bundle;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public ProductAdapter(Context context, List<Product> productList, Bundle bundle) {
        this.context = context;
        this.productList = productList;
        this.bundle = bundle;
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

        ImageView ivImageProduct,iBtnAddSPChon;
        TextView txtTenSP, txtGiaSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImageProduct = itemView.findViewById(R.id.ivImageProduct);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            iBtnAddSPChon = itemView.findViewById(R.id.iBtnAddSPChon);

            iBtnAddSPChon.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(((Activity) context), IntroProductActivity.class);
                    bundle.putString("idProduct", productList.get(getAdapterPosition()).getIdProduct());
                    intent.putExtras(bundle);
                    ((Activity) context).startActivity(intent);
                }
            });

        }
    }
}
