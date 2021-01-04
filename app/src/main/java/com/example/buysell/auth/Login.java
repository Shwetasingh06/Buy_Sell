package com.example.buysell.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buysell.R;
import com.example.buysell.home.Dashboard;
import com.example.buysell.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    public Login(){

    }

    //login
    private EditText et_login_email;
    private EditText et_login_pass;
    private Button btn_login_submit;
    private TextView tv_login_signup;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hooks
        et_login_email=findViewById(R.id.login_email);
        et_login_pass=findViewById(R.id.login_pass);
        btn_login_submit=findViewById(R.id.login_submitbtn);
        tv_login_signup=findViewById(R.id.login_redirect_signup);

        progressDialog=new ProgressDialog(Login.this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);

        sharedPreferences=getSharedPreferences("User Information",MODE_PRIVATE);
        String userIdInfo = sharedPreferences.getString("email", "Guest");
        String userPassInfo = sharedPreferences.getString("password", "Guest");

        if(!(userIdInfo.equals("Guest") && userPassInfo.equals("Guest"))){
            progressDialog.show();
            signIn(userIdInfo, userPassInfo);
        }
        
        btn_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=et_login_email.getText().toString();
                final String password=et_login_pass.getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(Login.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    signIn(email,password);
                }

            }
        });

        tv_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });

    }

    private void signIn( final String email, final String password) {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "hiiiii"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.commit();

//                        Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Dashboard.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                });

    }
}