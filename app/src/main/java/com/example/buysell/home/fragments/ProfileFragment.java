package com.example.buysell.home.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.buysell.R;
import com.example.buysell.auth.Login;
import com.example.buysell.auth.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    private TextView tv_name;
    private TextView tv_email;
    private  TextView tv_name2;
    private TextView tv_email2;
    private TextView tv_city;
    private TextView tv_mob;
    private RelativeLayout relativeLayout;

    private List<String> list;
    private DatabaseReference dref;
    String fname, lname;
    private String currentUser;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        //hooks
        tv_name=view.findViewById(R.id.profile_name);
        tv_name2=view.findViewById(R.id.profile_name2);
        tv_email=view.findViewById(R.id.profile_email);
        tv_email2=view.findViewById(R.id.profile_email2);
        tv_city=view.findViewById(R.id.profile_city);
        tv_mob=view.findViewById(R.id.profile_mob);
        relativeLayout=view.findViewById(R.id.profile_logout);

        list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser().getEmail();
        String[] temp = currentUser.split("@");
        dref = FirebaseDatabase.getInstance().getReference("user_info").child(auth.getUid());
        Log.i("snapshot", "onDataChange: "+temp[0]);// shweta and Shweta are different !!!!!!!!!! tumhari awaj aa rhi ahi...meri awaj nhi aa rhi kya nhi tumhara enable nhi h mic
        //kha se kru

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("snapshot", "onDataChange: "+snapshot.getValue());
                UserModel userModel = snapshot.getValue(UserModel.class);
                tv_name.setText(userModel.getFname()+" "+userModel.getLname());
                tv_name2.setText(userModel.getFname()+" "+userModel.getLname());
                tv_email.setText(userModel.getEmail());
                tv_email2.setText(userModel.getEmail());
                tv_city.setText(userModel.getCity());
                tv_mob.setText(userModel.getPhone());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    signout();



                }


        });



        return view;
    }

    private void signout() {
        sharedPreferences= this.getActivity().getSharedPreferences("User Information",MODE_PRIVATE);
                //getSharedPreferences("UserInformation",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor = sharedPreferences.edit();
        editor.putString("userId", "Guest");
        editor.putString("password", "Guest");
        editor.apply();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signOut();
        Intent intent = new Intent(getContext(), Login.class);
        startActivity(intent);
        getActivity().finish();


    }


}
