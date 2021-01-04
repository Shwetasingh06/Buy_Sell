package com.example.buysell.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buysell.R;
import com.example.buysell.home.Dashboard;
import com.example.buysell.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;


import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //register
    private EditText et_reg_email;
    private EditText et_reg_fname;
    private EditText et_reg_lname;
    private EditText et_reg_phone;
    private EditText et_reg_pass;
    private Spinner spinner;
    private Button btn_reg_sub;
    private TextView tv_reg_signin;
    private FirebaseAuth Auth;
    ProgressDialog progressDialog;
    private List<String> list;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    String city;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //hooks
        et_reg_email=findViewById(R.id.register_email);
        et_reg_fname=findViewById(R.id.register_fname);
        et_reg_lname=findViewById(R.id.register_lname);
        et_reg_pass=findViewById(R.id.register_password);
        et_reg_phone=findViewById(R.id.register_mob);
        //et_reg_city=findViewById(R.id.register_city);
        btn_reg_sub=findViewById(R.id.register_regbtn);
        tv_reg_signin=findViewById(R.id.register_redirect_signin);
        list = new ArrayList<>();

        sharedPreferences=getSharedPreferences("User Information",MODE_PRIVATE);

        //spinner
        spinner = (Spinner) findViewById(R.id.register_city);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        btn_reg_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=et_reg_email.getText().toString();
                final String fname=et_reg_fname.getText().toString();
                final String lname=et_reg_lname.getText().toString();
                final String password=et_reg_pass.getText().toString();
//                final String city=et_reg_city.getText().toString();
                final String mobile=et_reg_phone.getText().toString();





                if(!(fname.equals("") && lname.equals("") && fname.equals("") && email.equals("") && mobile.equals("") && password.equals("") && city.equals(""))){
                    if(isUnique(email))
                    {

                        if(password.length() >=6){

                            progressDialog = new ProgressDialog(Register.this);
                            progressDialog.setTitle("Registering You...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();


                            Auth = FirebaseAuth.getInstance();
//                            Auth.getCurrentUser().ge


                            Auth.createUserWithEmailAndPassword(email,password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Toast.makeText(Register.this, "Registered Successfully...!", Toast.LENGTH_SHORT).show();
                                            editor = sharedPreferences.edit();
                                            editor.putString("email", email.toLowerCase());
                                            editor.putString("password", password);
                                            editor.commit();

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_info").child(task.getResult().getUser().getUid());


                                            UserModel userModel = new UserModel(fname, lname, email, mobile, city,password);
                                            databaseReference.setValue(userModel);
                                            Intent i = new Intent(Register.this, Dashboard.class);
                                            startActivity(i);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "fail:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(Register.this, "Password is not same Or too short", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(Register.this, "This userId is not available Please try different email", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(Register.this, "One or more Field(s) Are Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tv_reg_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });

    }

    private boolean isUnique(String id){


        String[] temp = id.split("@");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user_info").child(temp[0]);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    list.add(s.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return !list.contains(id);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        city =spinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}