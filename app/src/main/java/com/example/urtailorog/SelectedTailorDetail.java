package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SelectedTailorDetail extends AppCompatActivity {
    ImageView home, profile, setting;
    TextView name, phone, address, city, email, state, username;
    Button btn;
    String customerName, customerAddress, customerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_tailor_detail);

        // Get data
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        String productPrice = intent.getStringExtra("productPrice");
        String productImage = intent.getStringExtra("productImage");
        String tailorName = intent.getStringExtra("tailorName");
        String tailorPhone = intent.getStringExtra("tailorPhone");
        String tailorAddress = intent.getStringExtra("tailorAddress");
        String tailorCity = intent.getStringExtra("tailorCity");
        String tailorEmail = intent.getStringExtra("tailorEmail");
        String tailorState = intent.getStringExtra("tailorState");
        String tailorUsername = intent.getStringExtra("tailorUsername");
        String tailorId = intent.getStringExtra("tailorId");
        customerAddress = getIntent().getStringExtra("customerAddress");
        customerName = getIntent().getStringExtra("customerName");
        customerPhone = getIntent().getStringExtra("customerPhone");

        // Bind views
        name = findViewById(R.id.tailor_name);
        phone = findViewById(R.id.tailor_phone);
        address = findViewById(R.id.tailor_address);
        city = findViewById(R.id.tailor_city);
        email = findViewById(R.id.tailor_email);
        state = findViewById(R.id.tailor_state);
        username = findViewById(R.id.tailor_username);
        btn = findViewById(R.id.btn);

        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);

        // Set values
        name.setText(tailorName);
        phone.setText("Contact: " + tailorPhone);
        address.setText("Address: " + tailorAddress);
        city.setText("City: " + tailorCity);
        email.setText("Email: " + tailorEmail);
        state.setText("State: " + tailorState);
        username.setText("Username: " + tailorUsername);

        // Navigation
        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));

        // Continue to MeasurementSelection
        btn.setOnClickListener(v -> {
            Intent i = new Intent(this, MeasurementSelection.class);
            i.putExtra("productName", productName);
            i.putExtra("productImage", productImage);
            i.putExtra("productPrice", productPrice);
            i.putExtra("tailorName", tailorName);
            i.putExtra("tailorPhone", tailorPhone);
            i.putExtra("tailorAddress", tailorAddress);
            i.putExtra("tailorCity", tailorCity);
            i.putExtra("tailorId",tailorId);
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
