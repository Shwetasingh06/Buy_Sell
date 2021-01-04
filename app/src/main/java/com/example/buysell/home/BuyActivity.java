package com.example.buysell.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.buysell.R;
import com.example.buysell.auth.Login;
import com.example.buysell.home.fragments.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyActivity extends AppCompatActivity {

    private ImageView iv_image;
    private TextView tv_title;
    private TextView tv_dop;
    private TextView tv_selling_price;
    private TextView tv_mrp;
    private TextView tv_description;
    private TextView tv_city;
    private Button buy;
    private TextView warnning;
    private DatabaseReference product_db_reference;
    private String product_city;
    private String user_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        iv_image=findViewById(R.id.imageView9);
        tv_city=findViewById(R.id.buy_city);
        tv_description=findViewById(R.id.buy_product_desc);
        tv_dop=findViewById(R.id.buy_dop);
        tv_title=findViewById(R.id.buy_title);
        tv_mrp=findViewById(R.id.buy_mrp);
        tv_selling_price=findViewById(R.id.buy_price);
        warnning=findViewById(R.id.buy_warning);
        buy=findViewById(R.id.btn_buy);


        String pid=getIntent().getStringExtra("pid");
        Log.i("product_id", "onCreate: "+pid);
        product_db_reference= FirebaseDatabase.getInstance().getReference("product_detail").child(pid);
        product_db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {

                        Log.i("product_id", "onDataChange:1 " + snapshot.getValue());
                        ProductModel p = snapshot.getValue(ProductModel.class);

                        tv_description.setText(p.getProduct_description());
                        tv_dop.setText(p.getDate_of_purchase());
                        tv_title.setText(p.getTitle());
                        tv_mrp.setText(p.getMrp());
                        tv_selling_price.setText(p.getSelling_price());
                        tv_city.setText(p.getCity());
                        product_city=p.getCity();
                        Glide.with(getApplicationContext()).load(p.getProduct_photo()).into(iv_image);

                         user_city=getIntent().getStringExtra("user_city");
                         if(!user_city.equals(product_city)){
                             buy.setVisibility(View.GONE);
                             warnning.setText("Location out of reach");
                         }

                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_city=getIntent().getStringExtra("user_city");
        Log.i("city", "onCreate: "+user_city+" "+product_city);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
                Toast.makeText(BuyActivity.this, "Done..!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}