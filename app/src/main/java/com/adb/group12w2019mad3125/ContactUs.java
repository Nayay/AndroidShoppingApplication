package com.adb.group12w2019mad3125;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {
    private TextView txtNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        txtNumber = findViewById(R.id.textView10);
        txtNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+16478075105"));
                startActivity(intent);

            }
        });

        TextView homeTextBtn = findViewById(R.id.home_settings_btn);
        TextView logoutTextButton = findViewById(R.id.logout_account_settings_btn);
        homeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ContactUs.this,OrdersActivity.class);
                startActivity(intent);
            }
        });

        logoutTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ContactUs.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
