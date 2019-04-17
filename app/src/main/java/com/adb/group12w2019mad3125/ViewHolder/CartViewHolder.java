package com.adb.group12w2019mad3125.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adb.group12w2019mad3125.ItemClickListner;
import com.adb.group12w2019mad3125.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPName,txtPPrice,txtPQuantity;
    public ImageView imageView;

    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtPName = itemView.findViewById(R.id.userNameOrder);
        txtPPrice = itemView.findViewById(R.id.cartPrice);
        txtPQuantity = itemView.findViewById(R.id.phoneOrder);
        imageView = itemView.findViewById(R.id.imgProductCart);
    }

    @Override
    public void onClick(View v) {
    itemClickListner.onClick(v,getAdapterPosition(),false);

    }
//    public void setItemClickListner(ItemClickListner itemClickListner){
//    this.itemClickListner = itemClickListner;
//    }
}
