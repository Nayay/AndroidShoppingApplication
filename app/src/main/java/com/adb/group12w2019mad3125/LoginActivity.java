package com.adb.group12w2019mad3125;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.adb.group12w2019mad3125.Model.Users;
import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView adminLink;
    private TextView userLink;
    private Button btnLogin;
    private Switch rememberMe;
    private ProgressDialog loadingBar;
    private String parentDBName ="Users";
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor mEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);
        mEditor = sharedPreferences.edit();

        edtEmail = findViewById(R.id.edtUserL);
        edtPassword = findViewById(R.id.edtPasswordL);
        adminLink = findViewById(R.id.txtAdmin);
        userLink = findViewById(R.id.txtUser);
        btnLogin = findViewById(R.id.btnLogin);
        rememberMe = findViewById(R.id.switchRememberMe);
        loadingBar = new ProgressDialog(this);
        userLink.setVisibility(View.INVISIBLE);
        //retrieve
        if(sharedPreferences.contains("email")){
            rememberMe.setChecked(true);
            String emailUser = sharedPreferences.getString("email",null);
            String passsUser = sharedPreferences.getString("password",null);
            edtEmail.setText(emailUser);
            edtPassword.setText(passsUser);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onClickLoginUser();
            }
        });

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnLogin.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                userLink.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        userLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnLogin.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                userLink.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
    }

    public void onClickLoginUser() {
        String email = edtEmail.getText().toString();
        email = encodeUserEmail(email);
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Required fields cannot be empty", Toast.LENGTH_LONG).show();
        } else {

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            checkLogin(email, password);


        }

    }
    private void checkLogin(final String email, final String password){

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (parentDBName.equals("Users")) {
                    if (dataSnapshot.child(parentDBName).child(email).exists()) {
                        Users userData = dataSnapshot.child(parentDBName).child(email).getValue(Users.class);
                        if (userData.getEmail().equals(email)) {
                            if (userData.getPassword().equals(password)) {


                                //Rem me is on
                                if(rememberMe.isChecked()){
                                    mEditor.putString("email",edtEmail.getText().toString());
                                    mEditor.putString("password",edtPassword.getText().toString());
                                    mEditor.apply();
                                }
                                else {
                                    mEditor.clear().apply();
                                }
                                Prevalent.currentOnlineUser = userData;
                               // Prevalent.UserEmailKey = userData.getEmail().replace(".",",");
                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                            }

                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "User does not exit please Register.", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                }
                if (parentDBName.equals("Admins")) {
                    if (dataSnapshot.child(parentDBName).child(email).exists()) {
                        Intent intent = new Intent(LoginActivity.this, AdminAddProductActivity.class);
                        startActivity(intent);
                    }
                }
//                    if (dataSnapshot.child(parentDBName).child(email).exists()) {


//                if (parentDBName.equals("Admins")) {
//                    if (dataSnapshot.child(parentDBName).child(email).exists()) {
//
//                        Users userData = dataSnapshot.child(parentDBName).child(email).getValue(Users.class);
//                        if (userData.getEmail().equals(email)) {
//                            if (userData.getPassword().equals(password)) {
//                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_LONG).show();
//                                loadingBar.dismiss();
//                                Intent intent = new Intent(LoginActivity.this, AdminAddProductActivity.class);
//                                startActivity(intent);
//                            } else {
//                                loadingBar.dismiss();
//                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Admin does not exit please Register.", Toast.LENGTH_LONG).show();
//                        loadingBar.dismiss();
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}