package com.adb.group12w2019mad3125;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adb.group12w2019mad3125.Model.Details;
import com.adb.group12w2019mad3125.Model.Orders;
import com.adb.group12w2019mad3125.Model.Products;
import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    private TextView homeTextBtn, logoutTextButton,txtOrderID,txtUserName;
    private String orderID;
    private ArrayList<Details> orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        orderID = getIntent().getStringExtra("oid");
        super.onCreate(savedInstanceState);
        orderDetails =new  ArrayList<Details>();
        setContentView(R.layout.activity_order_details);
        homeTextBtn = findViewById(R.id.home_settings_btn);
        logoutTextButton = findViewById(R.id.logout_account_settings_btn);
        getOrderDetails(orderID);



        homeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(OrderDetails.this,HomeActivity.class);
                startActivity(intent);



            }
        });


        logoutTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(OrderDetails.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtOrderID = findViewById(R.id.txtDetailsOrderID);
        txtUserName = findViewById(R.id.txtDetailsName);


    }

    private void getOrderDetails(final String orderID) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("  ").child(Prevalent.currentOnlineUser.getEmail()).child("Order");
        orderRef.child(orderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Orders orders = dataSnapshot.getValue(Orders.class);
                    txtOrderID.setText("Order Id: "+orders.getOrderId());
                    txtUserName.setText("User: " +orders.getName());





//                   pnames = orders.getProducts();
//                    prices  = orders.getPrice();
//                    quantitya = orders.getQuantity();


                    //Picasso.get().load(products.getImage()).into(productImage);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  String[]  getImages(String images) {

        /* String to split. */
        String stringToSplit = images;
        String[] tempArray;
        /* delimiter */
        String delimiter = "~~~~";
        /* given string will be split by the argument delimiter provided. */
        tempArray = stringToSplit.split(delimiter);
        return tempArray;
    }
    public  String[]  getPName(String name) {

        /* String to split. */
        String stringToSplit = name;
        String[] tempArray;
        /* delimiter */
        String delimiter = "_";
        /* given string will be split by the argument delimiter provided. */
        tempArray = stringToSplit.split(delimiter);
        return tempArray;
    }

}
