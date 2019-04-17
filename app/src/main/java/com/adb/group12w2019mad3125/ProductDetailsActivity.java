package com.adb.group12w2019mad3125;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.adb.group12w2019mad3125.Model.Products;
import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    int minteger = 1;
    private Button addToCartButton;
    private ImageView productImage;
    private TextView productPrice, productDesc, productName, quantity;
    private String productID = "";
    private Button btnAdd, btnSub;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID = getIntent().getStringExtra("pid");

        addToCartButton = findViewById(R.id.btnAddToCart);
        productImage = findViewById(R.id.imageProduct);
        productPrice = findViewById(R.id.txtPPrice);
        productDesc = findViewById(R.id.txtPDesc);
        productName = findViewById(R.id.txtPName);
        getProductDetails(productID);
        btnAdd = findViewById(R.id.btnSub);
        btnSub = findViewById(R.id.btnAdd);

        quantity = findViewById(R.id.txtQuantity);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (minteger > 0 && minteger < 9) {
                    minteger = ++minteger;
                    //Toast.makeText(ProductDetailsActivity.this,minteger+"Add",Toast.LENGTH_LONG).show();
                    quantity.setText(minteger + "");
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (minteger > 1 && minteger <= 9) {
                    minteger = --minteger;
                    //Toast.makeText(ProductDetailsActivity.this,minteger+"Sub",Toast.LENGTH_LONG).show();
                    quantity.setText(minteger + "");
                }
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }


        });
        navigation = findViewById(R.id.navigation_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_cart:
                        Intent intentCart = new Intent(ProductDetailsActivity.this,CartActivity.class);
                        startActivity(intentCart);
                        return true;
                    case R.id.action_settings:
                        Intent intentSettings = new Intent(ProductDetailsActivity.this,SettingsActivity.class);
                        startActivity(intentSettings);
                        return true;
                    case R.id.action_orders:
                        Intent intentOrders = new Intent(ProductDetailsActivity.this,OrdersActivity.class);
                        startActivity(intentOrders);
                        return true;
                }
                return false;
            }
        });


    }

    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", minteger + "");
        cartMap.put("image", imagePath);
        cartMap.put("discount", "");
        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getEmail())
                .child("Products").child(productID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                            startActivity(intent);

                        }
                    }
                });

    }

    String imagePath;

    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice()+"$");
                    productDesc.setText(products.getDescription());
                    imagePath = products.getImage();
                    Picasso.get().load(products.getImage()).into(productImage);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}



