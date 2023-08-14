package com.example.project_lv1_mobile.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.R;
import com.example.project_lv1_mobile.model.Member;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class FragmentTaiKhoan extends Fragment {

    private Context context;

    public FragmentTaiKhoan() {
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
        View rootView = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        Bundle bundle = getArguments();
        String idMember = bundle.getString("idMember");

        ImageView ivAvatarTaiKhoan = rootView.findViewById(R.id.ivAvatarTaiKhoan);
        TextView txtTenTaiKhoan = rootView.findViewById(R.id.txtTenTaiKhoan);
        TextView txtFullNameTaiKhoan = rootView.findViewById(R.id.txtFullNameTaiKhoan);
        TextView txtGioiTinhTaiKhoan = rootView.findViewById(R.id.txtGioiTinhTaiKhoan);
        TextView txtEmailTaiKhoan = rootView.findViewById(R.id.txtEmailTaiKhoan);

        LinearLayout llUpdateTaiKhoan = rootView.findViewById(R.id.llUpdateTaiKhoan);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference reference = firestore.collection("MEMBER").document(idMember);
        reference.get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();
            Member member = snapshot.toObject(Member.class);

            if (member.getRank() == 0) {
                ivAvatarTaiKhoan.setImageResource(R.drawable.addmin_icon);
            } else {
                Glide.with(context).load(member.getImageMember()).into(ivAvatarTaiKhoan);
            }
            txtTenTaiKhoan.setText(member.getFirtName());
            txtFullNameTaiKhoan.setText(member.getLastName() + " " + member.getFirtName());
            txtGioiTinhTaiKhoan.setText(member.getGender());
            txtEmailTaiKhoan.setText(member.getEmail());
        });

        llUpdateTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }
}