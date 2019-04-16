package com.adb.group12w2019mad3125;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adb.group12w2019mad3125.Model.Cart;
import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.adb.group12w2019mad3125.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {
    private static final String TAG ="Cart List Details" ;
    private RecyclerView rcCartView;
    private RecyclerView.LayoutManager rcCartLayoutManager;
    private TextView txtPrice;
    private Button btnCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        rcCartView = findViewById(R.id.rcCart);
        rcCartView.setHasFixedSize(true);
        rcCartLayoutManager =new LinearLayoutManager(this);
        rcCartView.setLayoutManager(rcCartLayoutManager);

        btnCheckout = findViewById(R.id.btnCheckout);
        txtPrice = findViewById(R.id.txtPrice);
    }

    @Override
    protected void onStart(){
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        Log.e(TAG,Prevalent.currentOnlineUser.getEmail());
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getEmail())
                        .child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
            holder.txtPName.setText(model.getPname());
            holder.txtPPrice.setText("Price = "+model.getPrice() + "$");
            holder.txtPQuantity.setText("Quantity = "+model.getQuantity());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items_layout,viewGroup,false);
                CartViewHolder holder= new CartViewHolder(view);
                return holder;
            }
        };
        rcCartView.setAdapter(adapter);
        adapter.startListening();
    }
}
