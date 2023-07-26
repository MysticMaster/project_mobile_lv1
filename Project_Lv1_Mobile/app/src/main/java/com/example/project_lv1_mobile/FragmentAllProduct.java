package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_lv1_mobile.adapter.ProductAdapter;
import com.example.project_lv1_mobile.dao.ProductDAO;
import com.example.project_lv1_mobile.model.Product;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentAllProduct extends Fragment {

    public String idMember;
    private Context context;
    private List<Product> productList;
    private ProductDAO productDAO;

    private FirebaseFirestore firestore;
    private ProductAdapter adapter;
    private final String collectionProduct = "PRODUCT";

    public FragmentAllProduct() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_product, container, false);
        RecyclerView recyclerAllProduct = rootView.findViewById(R.id.recyclerAllProduct);

        productList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        productDAO = new ProductDAO(firestore, context);

        listenFirebaseProduct();

        adapter = new ProductAdapter(context, productList, productDAO, idMember);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerAllProduct.setLayoutManager(manager);

        recyclerAllProduct.setAdapter(adapter);

        return rootView;
    }

    private void listenFirebaseProduct() {
        firestore.collection(collectionProduct).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        switch (documentChange.getType()) {
                            case ADDED: //  Khi chỉ có document được thêm
                                productList.clear();
                                for (DocumentSnapshot snapshot : value.getDocuments()) {
                                    Product product = snapshot.toObject(Product.class);
                                    productList.add(product);
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:  //  Khi có 1 document được cập nhật
                                adapter.notifyDataSetChanged();
                                break;
                            case REMOVED:   // Khi có 1 document bị xóa khỏi collection
                                documentChange.getDocument().toObject(Product.class);
                                productList.remove(documentChange.getOldIndex());
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            }
        });
    }
}