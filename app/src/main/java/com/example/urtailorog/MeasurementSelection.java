package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MeasurementSelection extends AppCompatActivity {

    ImageView home, profile, setting;
    Button b1, b2;
    String productName, productPrice, productImage;
    String tailorName, tailorPhone, tailorId, tailorAddress, tailorCity, customerName, customerAddress,customerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_measurement_selection);

        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);
        b1 = findViewById(R.id.btnself);
        b2 = findViewById(R.id.btnappoint);

        // Get data from Intent
        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getStringExtra("productPrice");
        productImage = getIntent().getStringExtra("productImage");

        tailorName = getIntent().getStringExtra("tailorName");
        tailorPhone = getIntent().getStringExtra("tailorPhone");
        tailorAddress = getIntent().getStringExtra("tailorAddress");
        tailorCity = getIntent().getStringExtra("tailorCity");
        tailorId = getIntent().getStringExtra("tailorId");

        customerAddress = getIntent().getStringExtra("customerAddress");
        customerName = getIntent().getStringExtra("customerName");
        customerPhone = getIntent().getStringExtra("customerPhone");

        home.setOnClickListener(v -> {
            Intent i = new Intent(MeasurementSelection.this, UserDashboard.class);
            startActivity(i);
        });

        profile.setOnClickListener(v -> {
            Intent i = new Intent(MeasurementSelection.this, UserProfile.class);
            startActivity(i);
        });

        setting.setOnClickListener(v -> {
            Intent i = new Intent(MeasurementSelection.this, UserSetting.class);
            startActivity(i);
        });

        b1.setOnClickListener(view -> {
            Intent i = new Intent(MeasurementSelection.this, SelfMeasure.class);
            i.putExtra("productName", productName);
            i.putExtra("productPrice", productPrice);
            i.putExtra("productImage", productImage);
            i.putExtra("tailorName", tailorName);
            i.putExtra("tailorPhone", tailorPhone);
            i.putExtra("tailorCity", tailorCity);
            i.putExtra("tailorAddress", tailorAddress);
            i.putExtra("tailorId", tailorId);
            i.putExtra("customerAddress", customerAddress);
            i.putExtra("customerName", customerName);
            i.putExtra("customerPhone", customerPhone);
            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly
            startActivity(i);
            finish();
        });

        b2.setOnClickListener(view -> {
            Intent i = new Intent(MeasurementSelection.this, SetAppointment.class);
            i.putExtra("productName", productName);
            i.putExtra("productPrice", productPrice);
            i.putExtra("productImage", productImage);
            i.putExtra("tailorName", tailorName);
            i.putExtra("tailorPhone", tailorPhone);
            i.putExtra("tailorCity", tailorCity);
            i.putExtra("tailorAddress", tailorAddress);
            i.putExtra("tailorId", tailorId);
            i.putExtra("customerAddress", customerAddress);
            i.putExtra("customerName", customerName);
            i.putExtra("customerPhone", customerPhone);
            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly

            startActivity(i);
            finish();
        });
    }
}
