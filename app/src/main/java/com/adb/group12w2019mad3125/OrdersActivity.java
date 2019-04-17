package com.adb.group12w2019mad3125;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adb.group12w2019mad3125.Model.Orders;
import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class OrdersActivity extends AppCompatActivity   {

    private RecyclerView orderView;
    private DatabaseReference orderRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderView = findViewById(R.id.rcOrderView);
        orderView.setHasFixedSize(true);
        orderView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(orderRef.child(Prevalent.currentOnlineUser.getEmail()).child("Order"),Orders.class).build();

        FirebaseRecyclerAdapter<Orders,orderViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, orderViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull orderViewHolder holder, int position,Orders model) {


            holder.userName.setText("User Name: "+ model.getName());
            holder.phone.setText("Phone Number: "+model.getPhone());
            holder.price.setText("Total Price: "+model.getTotalAmount()+"$");
            holder.address.setText(model.getAddress());
            holder.cardName.setText("card Name: "+model.getCardNumber());
            holder.prices.setText(model.getPrice().replaceAll("_"," "));
            holder.products.setText(model.getProducts().replaceAll("_"," "));

            }
            @NonNull
            @Override
            public orderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item_layout,viewGroup,false);
                return new orderViewHolder(view);
            }

        };
        orderView.setAdapter(adapter);
        adapter.startListening();
    }

    public  static class orderViewHolder extends RecyclerView.ViewHolder{
        public TextView userName,phone,price,address,cardName,products,prices;

        public orderViewHolder(@NonNull View itemView)
        {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameOrder);
            phone = itemView.findViewById(R.id.phoneOrder);
            price = itemView.findViewById(R.id.totalPriceOrder);
            address = itemView.findViewById(R.id.AddressOrder);
            cardName = itemView.findViewById(R.id.CardNameOrder);
            products = itemView.findViewById(R.id.productsOrders);
            prices = itemView.findViewById(R.id.PriceQuantityOrder);
        }
    }

}
