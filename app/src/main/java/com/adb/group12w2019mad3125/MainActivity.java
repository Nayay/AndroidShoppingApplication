package com.adb.group12w2019mad3125;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onLoginClick(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);


    }
    public void onRegisterClick(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }
}
