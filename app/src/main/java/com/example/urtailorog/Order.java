package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;

public class Order extends AppCompatActivity {

    Button b1;
    ImageView productImage, home, profile, setting;
    TextView productName, productPrice, grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views
        b1 = findViewById(R.id.track_order);
        productImage = findViewById(R.id.product_image); // ImageView to display product
        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);
        productName = findViewById(R.id.product_name); // TextView to display product name
        productPrice = findViewById(R.id.product_price); // TextView to display price
        grandTotal = findViewById(R.id.grand_total); // TextView to display grand total

        // Get product details from the intent
        Intent intent = getIntent();
        String productImageUrl = intent.getStringExtra("productImage");
        String productPriceString = intent.getStringExtra("productPrice");
        String productNameString = intent.getStringExtra("productName");

        // Set the product details in the UI
        productName.setText(productNameString);
        productPrice.setText("Price: " + productPriceString);

        Log.d("OrderActivity", "Image URL: " + productImageUrl);
        Glide.with(this).load(productImageUrl).into(productImage);

        // Calculate grand total (product price + 200)
        double price = Double.parseDouble(productPriceString);
        double total = price + 100;
        grandTotal.setText("Grand Total: " + total);

        // Track order button action
        b1.setOnClickListener(v -> {
            Intent trackIntent = new Intent(Order.this, TrackOrder.class);
            trackIntent.putExtra("productName",productNameString);
            trackIntent.putExtra("orderId", getIntent().getStringExtra("orderId"));
            startActivity(trackIntent);
        });

        // Navigation buttons
        home.setOnClickListener(v -> {
            Intent homeIntent = new Intent(Order.this, UserDashboard.class);
            startActivity(homeIntent);
        });

        profile.setOnClickListener(v -> {
            Intent profileIntent = new Intent(Order.this, UserProfile.class);
            startActivity(profileIntent);
        });

        setting.setOnClickListener(v -> {
            Intent settingIntent = new Intent(Order.this, UserSetting.class);
            startActivity(settingIntent);
        });
    }
}
