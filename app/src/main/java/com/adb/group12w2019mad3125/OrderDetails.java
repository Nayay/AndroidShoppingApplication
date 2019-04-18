package com.adb.group12w2019mad3125;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


    private TextView txtOrderID,txtUserName;
    private String orderID;
    private String[] pNameArray,pPriceArray,pQunatityArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderID = getIntent().getStringExtra("oid");

        TextView homeTextBtn = findViewById(R.id.home_settings_btn);
        TextView logoutTextButton = findViewById(R.id.logout_account_settings_btn);
        homeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(OrderDetails.this,OrdersActivity.class);
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
        getOrderDetails(orderID);


     }

    private void getOrderDetails(final String orderID) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getEmail()).child("Order");
        orderRef.child(orderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Orders orders = dataSnapshot.getValue(Orders.class);
                    txtOrderID.setText("Order Id: "+orders.getOrderId());
                    txtUserName.setText("User: " +orders.getName());
                    pNameArray = getData(orders.getProducts());
                    pPriceArray = getData(orders.getPrice());
                    pQunatityArray = getData((orders.getQuantity()));

                    ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(OrderDetails.this, android.R.layout.test_list_item,pNameArray);
                    ListView listViewName =  findViewById(R.id.detailPname);

                    listViewName.setAdapter(nameAdapter);
                    ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(OrderDetails.this, android.R.layout.test_list_item,pPriceArray);
                    ListView listViewPrice = findViewById(R.id.detailPrice);
                    listViewPrice.setAdapter(priceAdapter);
                    ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(OrderDetails.this, android.R.layout.test_list_item,pQunatityArray);
                    ListView listViewQuantity = findViewById(R.id.detailQuantity);
                    listViewQuantity.setAdapter(quantityAdapter);

                  }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public  String[]  getData(String name) {

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
