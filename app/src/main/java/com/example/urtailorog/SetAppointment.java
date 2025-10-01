package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class SetAppointment extends AppCompatActivity {
    Button b1;
    ImageView home, profile, setting;
    DatePicker datePicker;
    TimePicker timePicker;
    String productName, productPrice, productImage;
    String tailorName, tailorPhone, tailorId, tailorAddress, tailorCity;
    String customerName, customerAddress, customerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);

        // Initialize Views
        b1 = findViewById(R.id.book_now);
        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);

        // Get data from Intent
        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getStringExtra("productPrice");
        productImage = getIntent().getStringExtra("productImage");

        tailorName = getIntent().getStringExtra("tailorName");
        tailorPhone = getIntent().getStringExtra("tailorPhone");
        tailorId = getIntent().getStringExtra("tailorId");
        tailorAddress = getIntent().getStringExtra("tailorAddress");
        tailorCity = getIntent().getStringExtra("tailorCity");
        customerAddress = getIntent().getStringExtra("customerAddress");
        customerName = getIntent().getStringExtra("customerName");
        customerPhone = getIntent().getStringExtra("customerPhone");

        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);

        // Set Button Click Listener
        b1.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String date = day + "/" + month + "/" + year;
            String time = String.format("%02d:%02d", hour, minute);

            Intent i = new Intent(SetAppointment.this, OrderConfirmation.class);
            i.putExtra("appointmentDate", date);
            i.putExtra("appointmentTime", time);
            i.putExtra("appointmentStatus", "Pending");
            i.putExtra("productName", productName);
            i.putExtra("productPrice", productPrice);
            i.putExtra("productImage", productImage);
            i.putExtra("tailorName", tailorName);
            i.putExtra("tailorPhone", tailorPhone);
            i.putExtra("tailorId", tailorId);
            i.putExtra("tailorCity", tailorCity);
            i.putExtra("tailorAddress", tailorAddress);
            i.putExtra("customerAddress", customerAddress);
            i.putExtra("customerName", customerName);
            i.putExtra("customerPhone", customerPhone);
            i.putExtra("measurementType","appointment");
            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly

            startActivity(i);
        });


        // Navigation
        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));
    }
}
