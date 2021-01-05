package com.example.buysell.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.buysell.R;
import com.example.buysell.auth.Login;
import com.example.buysell.auth.UserModel;
import com.example.buysell.home.BuyActivity;
import com.example.buysell.home.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private GridView gv;
    private DatabaseReference product_db_reference;
    private DatabaseReference user_db_reference;
    private DatabaseReference reference;
    private List<ProductModel> productList;
    private Spinner spinner;
    String current_user;
    String spinnercity="";
    String userCity="" ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view=inflater.inflate(R.layout.fragment_home, container, false);

        gv=  view.findViewById(R.id.grid_layout);




        //gridview click listner

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Position", "onItemClick: "+getList(productList, spinnercity).get(i).getProduct_id()+" i: "+i+" l: "+l);

                Intent intent=new Intent(getContext(), BuyActivity.class);
                intent.putExtra("pid",getList(productList, spinnercity).get(i).getProduct_id());
                intent.putExtra("user_city",userCity);
                startActivity(intent);

            }
        });


        //spinner

        spinner = (Spinner) view.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);






        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.i("city", "onCreateView: providerId: "+auth.getCurrentUser().getProviderId()+" ;;; UId: "+ auth.getUid());

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference("user_info/"+auth.getUid());

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){


                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.city_list, android.R.layout.simple_spinner_item);
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    String city = snapshot.getValue(UserModel.class).getCity();
                    userCity=city;
                    Log.i("city", "onCreateView: "+userCity);
                    spinner.setAdapter(adapter);
                    int spinnerposition = adapter.getPosition(userCity);
                    spinner.setSelection(spinnerposition);

//        spinnercity = userCity;
                    product_db_reference=FirebaseDatabase.getInstance().getReference("product_detail");

                    product_db_reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue()!=null){
                                productList = new ArrayList<>();

                                for(DataSnapshot s: snapshot.getChildren()){

                                    ProductModel model=s.getValue(ProductModel.class);

                                        Log.i("model_value", "onDataChange: " + s.getValue(ProductModel.class));
                                        productList.add(model);

                                }
                                Log.i("city", "onDataChange: "+productList+" Spinner city "+spinnercity);
                                if(getList(productList, userCity)!=null){
                                    CustomAdapter adp=new CustomAdapter(getList(productList, userCity));
                                    gv.setAdapter(adp);
                                }

                            }
//                            if(getList(productList, userCity)!=null){
//                                CustomAdapter adp=new CustomAdapter(getList(productList, userCity));
//                                gv.setAdapter(adp);
//                            }



                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });



       // Action bar
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.actionbar_layout);
//        View v =((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        spinnercity=spinner.getSelectedItem().toString();
        Log.i("city", "onItemSelected: "+spinnercity);
        if(productList!=null){
            List<ProductModel> listUpdated = getList(productList, spinnercity);
            CustomAdapter adp=new CustomAdapter(listUpdated);
            gv.setAdapter(adp);

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class CustomAdapter extends BaseAdapter{

        private List<ProductModel> list;

        public CustomAdapter(List<ProductModel> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public ProductModel getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.card_layout,viewGroup,false);

            //hooks
            ImageView iv_image=v.findViewById(R.id.imageView11);
            TextView tv_price=v.findViewById(R.id.textView12);
            TextView tv_title=v.findViewById(R.id.textView14);
            TextView tv_city=v.findViewById(R.id.textView15);

            ProductModel productModel=list.get(i);
            tv_city.setText("City "+productModel.getCity());
            tv_price.setText("Price Rs. "+productModel.getSelling_price());
            tv_title.setText(productModel.getTitle());
            Glide.with(getActivity().getApplicationContext()).load(productModel.getProduct_photo()).into(iv_image);
            return v;
        }
    }

   private List<ProductModel> getList(@NotNull List<ProductModel > list, @NotNull String city){
        List<ProductModel> l = new ArrayList<>();

      if(list!=null  && city!=null )
      {
          for (ProductModel p: list
          ) {
              if(city!=null && p.getCity()!=null){
                  Log.i("shweta", "list_city: "+ city + " " +p.getCity());
                  if(p.getCity().equals(city)){
                      l.add(p);
                  }
              }
          }
      }
       return l;
   }


}


