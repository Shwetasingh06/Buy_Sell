package com.example.buysell.home.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buysell.R;
import com.example.buysell.home.Fill_product_details;


public class SellFragment extends Fragment {

    public SellFragment() {
        // Required empty public constructor
    }

    private ConstraintLayout btn_car;
    private ConstraintLayout btn_mobile;
    private ConstraintLayout btn_book;
    private ConstraintLayout btn_electronics;
    private ConstraintLayout btn_fashion;
    private ConstraintLayout btn_furniture;
    private ConstraintLayout btn_property;
    private ConstraintLayout btn_sports;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_sell, container, false);

        btn_car=view.findViewById(R.id.car);
        btn_mobile=view.findViewById(R.id.phone);
        btn_book=view.findViewById(R.id.books);
        btn_electronics=view.findViewById(R.id.electronics);
        btn_fashion=view.findViewById(R.id.desktop_laptop);
        btn_furniture=view.findViewById(R.id.furniture);
        btn_property=view.findViewById(R.id.property);
        btn_sports=view.findViewById(R.id.sports);

        btn_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Car");
            }
        });

        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Mobile");
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Book");
            }
        });

        btn_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Electronics");
            }
        });

        btn_fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Fashion");
            }
        });

        btn_furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Furniture");
            }
        });

        btn_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Property");
            }
        });

        btn_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCategory("Sports");
            }
        });





        return view;



    }

    private void getCategory(String s) {
        Intent intent=new Intent(getActivity().getApplicationContext(), Fill_product_details.class);
        intent.putExtra("category",s);
        startActivity(intent);

    }



}