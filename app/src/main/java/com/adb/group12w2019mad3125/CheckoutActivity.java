package com.adb.group12w2019mad3125;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CheckoutActivity extends AppCompatActivity {
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView txtEmail,txtPrice;

    private Button btnCheckout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        txtEmail = findViewById(R.id.txtCheckoutEmail);
        fullNameEditText =  findViewById(R.id.checkoutName);
        userPhoneEditText =  findViewById(R.id.checkoutPhone);
        addressEditText =  findViewById(R.id.checkoutAddress);
        btnCheckout = findViewById(R.id.btnCheckoutP);
        txtEmail.setText(Prevalent.currentOnlineUser.getEmail().replace(",","."));
        userInfoDisplay( fullNameEditText, userPhoneEditText, addressEditText);
        Toast.makeText(CheckoutActivity.this,getIntent().getStringExtra("totalPrice"),Toast.LENGTH_SHORT).show();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     txtPrice.setText(getIntent().getStringExtra("totalPrice"));

                check();
            }


        });
       //


    }
    private void check() {

        if(fullNameEditText.getText().toString().isEmpty()||userPhoneEditText.getText().toString().isEmpty()||addressEditText.getText().toString().isEmpty()){
            Toast.makeText(CheckoutActivity.this,"Required fields cannot be left empty",Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        final String saveCurrentDate,saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        final  DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getEmail());
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount",getIntent().getStringExtra("totalPrice"));
        orderMap.put("name",fullNameEditText.getText().toString());
        orderMap.put("phone",userPhoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state","shipped");
        orderMap.put("card","Visa");
        orderMap.put("cardNumber","378734493671000"+"");
        orderMap.put("cvv","123");
        orderMap.put("expiryDate","10/07/2019");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View").child(Prevalent.currentOnlineUser.getEmail())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CheckoutActivity.this,"Your final order has been paced successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    private void userInfoDisplay( final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getEmail());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("name").exists()) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
