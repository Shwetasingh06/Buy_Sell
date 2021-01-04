package com.example.buysell.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.buysell.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fill_product_details extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private ImageView iv_camara;
    private ImageView iv_gallary;
    private ImageView iv_photo;
    private EditText et_date_of_purchase;
    private EditText et_product_description;
    private EditText et_MRP;
    private EditText et_selling_price;
    private EditText et_title;
    private Button btn_sell;
    private Spinner spinner;
    String city;
    Uri uri;
    FirebaseAuth auth ;
    String image = "";
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_product_details);

        //hooks
        iv_camara=findViewById(R.id.camara);
        iv_gallary=findViewById(R.id.gallary);
        iv_photo=findViewById(R.id.photo);
        et_date_of_purchase=findViewById(R.id.dop);
        et_product_description=findViewById(R.id.product_desc);
        et_MRP=findViewById(R.id.editTextNumber);
        et_title=findViewById(R.id.title);
        et_selling_price=findViewById(R.id.selling_price);
        btn_sell=findViewById(R.id.button);
        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        //get data from intent

        final String category=getIntent().getStringExtra("category");
       // Toast.makeText(this, "Category "+category, Toast.LENGTH_SHORT).show();

        //spinner

            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(this);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.city_list, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);


        //pic image from gallary
        iv_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2000);
            }
        });


        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String date_of_purchase=et_date_of_purchase.getText().toString();
                final String product_descrption=et_product_description.getText().toString();
                final String mrp=et_MRP.getText().toString();
                final String title=et_title.getText().toString();
                final String selling_price=et_selling_price.getText().toString();
                String product_id = null;

                if( !date_of_purchase.equals("") && !product_descrption.equals("") && !mrp.equals("") && !title.equals("") && !selling_price.equals("")  && uri !=null){
                    pd.setTitle("loading..!");
                    pd.show();
                    pd.setCancelable(false);

                    final String userid = auth.getCurrentUser().getEmail().split("@")[0];

                    final String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    final String time = new SimpleDateFormat(("hh:mm")).format(new Date());

                    final String generatedName = new SimpleDateFormat("ddmmyyyyHHmmss").format(new Date());

                    if(uri != null){
                        product_id=generatedName;
                         final StorageReference storageReference= FirebaseStorage.getInstance().getReference("product_pics").child(generatedName+".jpg");
                        final String finalProduct_id = product_id;
                        storageReference.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (task.isSuccessful()) {
                                    return storageReference.getDownloadUrl();
                                } else {
                                    throw task.getException();
                                }
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull final Task<Uri> task) {

                                image = task.getResult().toString();

                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("product_detail").child(generatedName);
                                reference.child("product_photo").setValue(image);
                                pd.dismiss();
                                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("product_detail").child(generatedName);
                                String userId = auth.getUid();
                                ProductModel productModel=new ProductModel(finalProduct_id,image,date_of_purchase,product_descrption,mrp,selling_price,category,title,city,date,time,userId);
                                databaseReference.setValue(productModel);
                                finish();

                            }
                        });
                    } else {
                        image="";
                        pd.dismiss();
                    }



                    
                }else{
                    Toast.makeText(Fill_product_details.this, "fill all details...! ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2000 && resultCode == RESULT_OK){
            iv_photo.setVisibility(View.VISIBLE);
            iv_photo.setImageURI(data.getData());
            uri = data.getData();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city=spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}