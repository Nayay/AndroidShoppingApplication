package com.adb.group12w2019mad3125;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPhone;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = findViewById(R.id.edtUserL);
        edtPassword = findViewById(R.id.edtPasswordL);
        edtPhone = findViewById(R.id.edtPhoneNo);
        loadingBar = new ProgressDialog(this);
    }
    public void onClickRegisterUser(View view){
        String email = edtEmail.getText().toString();
        email = encodeUserEmail(email);
        String password = edtPassword.getText().toString();
        String phone = edtPhone.getText().toString();
        if(email.isEmpty()||password.isEmpty()||phone.isEmpty()){
            Toast.makeText(this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateUserAccount(email,phone,password);

        }


    }

    private void validateUserAccount(final String email, final String phone, final String password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child("email").exists())){
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("email",email);
                    userDataMap.put("password",password);
                    userDataMap.put("phone",phone);
                    RootRef.child("Users").child(email).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Congratulations, your account has been created",Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,"Network Error: Please try again",Toast.LENGTH_LONG).show();
                            }
                        }
                        });
                    }
                else{
                    Toast.makeText(RegisterActivity.this,"This "+email+" user already exist",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                       Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                      startActivity(intent);
                }
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
