package com.adb.group12w2019mad3125;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adb.group12w2019mad3125.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText fullNameEditText, userPhoneEditText, addressEditText,txtCvv,txtCardNumber;
    private TextView txtEmail;
    private Button btnCheckout;
    EditText date;
    DatePickerDialog datePickerDialog;
    String[] cardName={"Visa","Master"};
    private String orderId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        //   Toast.makeText(this,getIntent().getStringExtra("products"),Toast.LENGTH_SHORT).show();
        // initiate the date picker and a button
        date = findViewById(R.id.txtCardDate);

        TextView cancelTextButton = findViewById(R.id.cancel_btn);
        cancelTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CheckoutActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CheckoutActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.CradType);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cardName);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        txtEmail = findViewById(R.id.txtCheckoutEmail);
        fullNameEditText =  findViewById(R.id.checkoutName);
        userPhoneEditText =  findViewById(R.id.checkoutPhone);
        addressEditText =  findViewById(R.id.checkoutAddress);
        btnCheckout = findViewById(R.id.btnCheckoutP);
        txtCvv = findViewById(R.id.txtCvv);
        txtCardNumber = findViewById(R.id.txtCardNumber);
        txtEmail.setText(Prevalent.currentOnlineUser.getEmail().replace(",","."));
        userInfoDisplay( fullNameEditText, userPhoneEditText, addressEditText);
       // Toast.makeText(CheckoutActivity.this,getIntent().getStringExtra("price"),Toast.LENGTH_SHORT).show();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkOrderDetails();
            }
        });


    }
    private void checkOrderDetails() {

        if(fullNameEditText.getText().toString().isEmpty()||userPhoneEditText.getText().toString().isEmpty()||addressEditText.getText().toString().isEmpty()){
            Toast.makeText(CheckoutActivity.this,"Required fields cannot be left empty",Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }
    }

    private void confirmOrder() {
        orderId = getAlphaNumericString(10);
        final String saveCurrentDate,saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        // .child(Prevalent.currentOnlineUser.getEmail());
        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount",getIntent().getStringExtra("totalPrice"));
        orderMap.put("name",fullNameEditText.getText().toString());
        orderMap.put("phone",userPhoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("state","shipped");
        orderMap.put("card","Visa");
        orderMap.put("cardNumber",txtCardNumber.getText().toString());
        orderMap.put("cvv",txtCvv.getText().toString());
        orderMap.put("expiryDate",date.getText().toString());
        orderMap.put("products",getIntent().getStringExtra("products"));
        orderMap.put("price",getIntent().getStringExtra("price"));
        orderMap.put("quantity",getIntent().getStringExtra("qunatity"));
        orderMap.put("images",getIntent().getStringExtra("images"));
        orderMap.put("orderId",orderId);


//        orderRef.child("User View").child(Prevalent.currentOnlineUser.getEmail())
//                .child("Orders").child(currentTime.toString()).updateChildren(orderMap)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                           @Override
//                                           public void onComplete(@NonNull Task<Void> task) {
//                                               if(task.isSuccessful()){
//                                                   Toast.makeText(CheckoutActivity.this,"Your final order has been paced successfully",Toast.LENGTH_SHORT).show();
//                                                   Intent intent = new Intent(CheckoutActivity.this,OrdersActivity.class);
//                                                 //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                   startActivity(intent);
//                                                   finish();
//                                               }
//                                           }
//                                       });


        orderRef.child(Prevalent.currentOnlineUser.getEmail().replace(".",",")).child("Order").child(orderId).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View").child(Prevalent.currentOnlineUser.getEmail())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CheckoutActivity.this,"Your final order has been paced successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CheckoutActivity.this,OrdersActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }

            }
        });

    }
    public  String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    private void userInfoDisplay( final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getEmail());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("name").exists()) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();

                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getApplicationContext(), cardName[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
