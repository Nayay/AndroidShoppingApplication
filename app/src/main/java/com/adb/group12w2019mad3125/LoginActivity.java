package com.adb.group12w2019mad3125;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adb.group12w2019mad3125.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private ProgressDialog loadingBar;
    private String parentDBName ="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtUserL);
        edtPassword = findViewById(R.id.edtPasswordL);
        loadingBar = new ProgressDialog(this);
    }

    public void onClickLoginUser(View view) {
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
                if(dataSnapshot.child(parentDBName).child(email).exists()){
                    Users userData = dataSnapshot.child(parentDBName).child(email).getValue(Users.class);
                    if(userData.getEmail().equals(email)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this,"Invalid password",Toast.LENGTH_LONG).show();
                        }
                    }

                }else{
                    Toast.makeText(LoginActivity.this,"User does not exit please Register.",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public  void onClickAdmin(View view){
    Intent intent = new Intent(LoginActivity.this,AdminAddProductActivity.class);
    startActivity(intent);

    }


    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}