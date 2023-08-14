package com.example.project_lv1_mobile.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.adapter.PhieuNhapAdapter;
import com.example.project_lv1_mobile.model.PhieuNhap;
import com.example.project_lv1_mobile.model.Product;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentLichSuNhap extends Fragment {

    private Context context;
    private List<PhieuNhap> phieuNhapList;
    private PhieuNhapAdapter adapter;

    public FragmentLichSuNhap() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_su_nhap, container, false);

        Bundle bundle = getArguments();
        int rank = bundle.getInt("rank");
        String idMember = bundle.getString("idMember");

        phieuNhapList = new ArrayList<>();
        adapter = new PhieuNhapAdapter(context, phieuNhapList);

        listenFirebaseProduct(rank, idMember);

        RecyclerView recyclerPhieuNhap = view.findViewById(R.id.recyclerPhieuNhap);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerPhieuNhap.setLayoutManager(manager);
        recyclerPhieuNhap.setAdapter(adapter);

        return view;
    }

    private void listenFirebaseProduct(int rank, String idMember) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (rank == 1) {
            firestore.collection("PHIEUNHAP").whereEqualTo("idMember", idMember)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                            phieuNhapList.clear();
                                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                PhieuNhap phieuNhap = snapshot.toObject(PhieuNhap.class);
                                                phieuNhapList.add(phieuNhap);
                                            }
                                            adapter.notifyDataSetChanged();
                                            break;
                                        case MODIFIED:  //  Khi có 1 document được cập nhật
                                            adapter.notifyDataSetChanged();
                                            break;
                                        case REMOVED:   // Khi có 1 document bị xóa khỏi collection
                                            documentChange.getDocument().toObject(Product.class);
                                            phieuNhapList.remove(documentChange.getOldIndex());
                                            adapter.notifyDataSetChanged();
                                            break;
                                    }
                                }
                            }
                        }
                    });
        } else if (rank == 0) {
            firestore.collection("PHIEUNHAP").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                    phieuNhapList.clear();
                                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                                        PhieuNhap phieuNhap = snapshot.toObject(PhieuNhap.class);
                                        phieuNhapList.add(phieuNhap);
                                    }
                                    adapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:  //  Khi có 1 document được cập nhật
                                    adapter.notifyDataSetChanged();
                                    break;
                                case REMOVED:   // Khi có 1 document bị xóa khỏi collection
                                    documentChange.getDocument().toObject(Product.class);
                                    phieuNhapList.remove(documentChange.getOldIndex());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    }
                }
            });
        }
    }

}