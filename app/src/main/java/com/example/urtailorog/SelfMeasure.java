package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelfMeasure extends AppCompatActivity {

    ImageView home, profile, setting;
    Button b1;

    EditText edArm, edFullChest, edFullShoulder, edNeck, edSleeve, edBicep, edWrist,
            edWaistStomach, edHipsSeat, edFrontLength, edFrontChest, edBackWidth, edFullSleeve;
    String productName, productPrice, productImage;
    String customerName, customerAddress, customerPhone;
    String tailorName, tailorPhone, tailorId, tailorCity, tailorAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_measure);

        b1 = findViewById(R.id.self_measure_continue);
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

        edArm = findViewById(R.id.ed_arm);
        edFullChest = findViewById(R.id.ed_full_chest);
        edFullShoulder = findViewById(R.id.ed_full_shoulder);
        edNeck = findViewById(R.id.ed_neck);
        edSleeve = findViewById(R.id.ed_sleeve);
        edBicep = findViewById(R.id.ed_bicep);
        edWrist = findViewById(R.id.ed_wrist);
        edWaistStomach = findViewById(R.id.ed_waist_stomach);
        edHipsSeat = findViewById(R.id.ed_hips_seat);
        edFrontLength = findViewById(R.id.ed_front_length);
        edFrontChest = findViewById(R.id.ed_front_chest);
        edBackWidth = findViewById(R.id.ed_back_width);
        edFullSleeve = findViewById(R.id.ed_full_sleeve);

        b1.setOnClickListener(v -> {
            Intent i = new Intent(SelfMeasure.this, OrderConfirmation.class);

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
            i.putExtra("arm", edArm.getText().toString());
            i.putExtra("fullChest", edFullChest.getText().toString());
            i.putExtra("fullShoulder", edFullShoulder.getText().toString());
            i.putExtra("neck", edNeck.getText().toString());
            i.putExtra("sleeve", edSleeve.getText().toString());
            i.putExtra("bicep", edBicep.getText().toString());
            i.putExtra("wrist", edWrist.getText().toString());
            i.putExtra("waistStomach", edWaistStomach.getText().toString());
            i.putExtra("hipsSeat", edHipsSeat.getText().toString());
            i.putExtra("frontLength", edFrontLength.getText().toString());
            i.putExtra("frontChest", edFrontChest.getText().toString());
            i.putExtra("backWidth", edBackWidth.getText().toString());
            i.putExtra("fullSleeve", edFullSleeve.getText().toString());
            i.putExtra("measurementType", "self");
            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly

            startActivity(i);
            finish();
        });


        home.setOnClickListener(v -> startActivity(new Intent(SelfMeasure.this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(SelfMeasure.this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(SelfMeasure.this, UserSetting.class)));
    }
}
