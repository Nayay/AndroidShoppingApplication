package com.adb.group12w2019mad3125;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminAddProductActivity extends AppCompatActivity {
    private ImageView imgMobile,imgWatch,imgLaptop,imgHeadphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);
//        imgWatch = findViewById(R.id.imgWatch);
//        imgLaptop = findViewById(R.id.imgLaptop);
//        imgHeadphone = findViewById(R.id.imgHeadphone);
        imgMobile = findViewById(R.id.imgMobile);



        imgMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAddProductActivity.this,AdminCategoryActivity.class);
                intent.putExtra("category","Mobile");
                startActivity(intent);
            }
        });
//        imgHeadphone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminAddProductActivity.this,AdminCategoryActivity.class);
//                intent.putExtra("category","Headphone");
//                startActivity(intent);
//            }
//        });
//        imgLaptop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminAddProductActivity.this,AdminCategoryActivity.class);
//                intent.putExtra("category","Laptop");
//                startActivity(intent);
//            }
//        });
//        imgWatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminAddProductActivity.this,AdminCategoryActivity.class);
//                intent.putExtra("category","Watch");
//                startActivity(intent);
//            }
//        });
    }
}
