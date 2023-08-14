package com.example.project_lv1_mobile.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lv1_mobile.ChonSPNhapXuatActivity;
import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.TaoPhieuNhapActivity;
import com.example.project_lv1_mobile.adapter.ChonSPAdapter;
import com.example.project_lv1_mobile.model.Member;
import com.example.project_lv1_mobile.model.PhieuNhapChiTiet;
import com.example.project_lv1_mobile.tempDAO.PhieuNhapChiTietDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class FragmentNhap extends Fragment {

    private Context context;
    private List<PhieuNhapChiTiet> nhapChiTietList;
    private ChonSPAdapter chonSPAdapter;
    private PhieuNhapChiTietDAO phieuNhapChiTietDAO;
    private RecyclerView recyclerNhap;

    public FragmentNhap() {
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
        View rootView = inflater.inflate(R.layout.fragment_nhap, container, false);
        nhapChiTietList = new ArrayList<>();
        phieuNhapChiTietDAO = new PhieuNhapChiTietDAO(context);

        Bundle bundle = getArguments();
        String idMember = bundle.getString("idMember");

        ImageButton iBtnThemSPNhap = rootView.findViewById(R.id.iBtnThemSPNhap);
        recyclerNhap = rootView.findViewById(R.id.recyclerNhap);
        TextView txtNguoiNhap = rootView.findViewById(R.id.txtNguoiNhap);
        TextView txtNgayNhap = rootView.findViewById(R.id.txtNgayNhap);
        TextView txtNhapSub = rootView.findViewById(R.id.txtNhapSub);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("MEMBER")
                .document(idMember);
        reference.get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();

            Member member = snapshot.toObject(Member.class);
            txtNguoiNhap.setText("Người nhập: " + member.getLastName() + " " + member.getFirtName());
        });

        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);

        txtNgayNhap.setText("Ngày: " + String.format("%d/%d/%d", day, month, year));


        nhapChiTietList.addAll(phieuNhapChiTietDAO.selectAllPNCT(idMember));

        chonSPAdapter = new ChonSPAdapter(context, nhapChiTietList, phieuNhapChiTietDAO, idMember);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerNhap.setLayoutManager(manager);
        recyclerNhap.setAdapter(chonSPAdapter);


        iBtnThemSPNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChonSP = new Intent(getActivity(), ChonSPNhapXuatActivity.class);
                bundle.putInt("keyNhap", 0);
                bundle.putString("idMember", idMember);
                toChonSP.putExtras(bundle);
                startActivity(toChonSP);
                getActivity().finish();
            }
        });

        txtNhapSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = 0;
                for (int i = 0; i< nhapChiTietList.size(); i++){
                    size++;
                }
                if (size == 0){
                    Toast.makeText(context, "Vui lòng chọn sản phẩm nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), TaoPhieuNhapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idMember", idMember);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return rootView;
    }
}