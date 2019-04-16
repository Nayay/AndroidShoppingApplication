package com.adb.group12w2019mad3125;

import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adb.group12w2019mad3125.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    int minteger = 1;
    private FloatingActionButton addToCartButton;
    private ImageView productImage;
    private TextView productPrice,productDesc,productName,quantity;
    private  String productID ="";
    private Button btnAdd,btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productID = getIntent().getStringExtra("pid");

        //addToCartButton = findViewById(R.id.fltAddToCart);
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

                if(minteger>0&&minteger<9){
                minteger = ++minteger;
                    //Toast.makeText(ProductDetailsActivity.this,minteger+"Add",Toast.LENGTH_LONG).show();
                quantity.setText(minteger+"");
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(minteger>1&&minteger<=9){
                    minteger = --minteger;
                    //Toast.makeText(ProductDetailsActivity.this,minteger+"Sub",Toast.LENGTH_LONG).show();
                    quantity.setText(minteger+"");
                }
            }
        });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDesc.setText(products.getDescription());

                    Picasso.get().load(products.getImage()).into(productImage);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
