package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class OrderConfirmation extends AppCompatActivity {
    FirebaseFirestore db;
    TextView tailorName, tailorPhone, productName;
    TextView arm, fullChest, fullShoulder, neck, sleeve, bicep, wrist, waist, hips, frontLength, frontChest, backWidth, fullSleeve;
    TextView measurementMessage;
    Button b2;
    ImageView home, profile, setting;
    String customerName, customerAddress, customerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        db = FirebaseFirestore.getInstance();

        // Initialize views
        tailorName = findViewById(R.id.tailorName);
        tailorPhone = findViewById(R.id.tailorPhone);
        productName = findViewById(R.id.productName);
        arm = findViewById(R.id.arm);
        fullChest = findViewById(R.id.fullChest);
        fullShoulder = findViewById(R.id.fullShoulder);
        neck = findViewById(R.id.neck);
        sleeve = findViewById(R.id.sleeve);
        bicep = findViewById(R.id.bicep);
        wrist = findViewById(R.id.wrist);
        waist = findViewById(R.id.waist);
        hips = findViewById(R.id.hips);
        frontLength = findViewById(R.id.frontLength);
        frontChest = findViewById(R.id.frontChest);
        backWidth = findViewById(R.id.backWidth);
        fullSleeve = findViewById(R.id.fullSleeve);
        b2 = findViewById(R.id.btnconfirm);
        measurementMessage = findViewById(R.id.measurementMessage);

        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);

        // Get data from Intent
        Intent intent = getIntent();
        tailorName.setText("Tailor Name: " + intent.getStringExtra("tailorName"));
        tailorPhone.setText("Tailor Contact: " + intent.getStringExtra("tailorPhone"));
        productName.setText("Product Name: " + intent.getStringExtra("productName"));

        String measurementType = intent.getStringExtra("measurementType");
        customerAddress = intent.getStringExtra("customerAddress");
        Log.d("Debug", "Sending userAddress: " + customerAddress);
        customerName = intent.getStringExtra("customerName");
        Log.d("Debug", "Sending userName: " + customerName);
        customerPhone = intent.getStringExtra("customerPhone");
        Log.d("Debug", "Sending userPhone: " + customerPhone);


        if ("self".equals(measurementType)) {
            arm.setText("Arm: " + intent.getStringExtra("arm"));
            fullChest.setText("Full Chest: " + intent.getStringExtra("fullChest"));
            fullShoulder.setText("Full Shoulder: " + intent.getStringExtra("fullShoulder"));
            neck.setText("Neck: " + intent.getStringExtra("neck"));
            sleeve.setText("Sleeve: " + intent.getStringExtra("sleeve"));
            bicep.setText("Bicep: " + intent.getStringExtra("bicep"));
            wrist.setText("Wrist: " + intent.getStringExtra("wrist"));
            waist.setText("Waist/Stomach: " + intent.getStringExtra("waistStomach"));
            hips.setText("Hips/Seat: " + intent.getStringExtra("hipsSeat"));
            frontLength.setText("Front Length: " + intent.getStringExtra("frontLength"));
            frontChest.setText("Front Chest: " + intent.getStringExtra("frontChest"));
            backWidth.setText("Back Width: " + intent.getStringExtra("backWidth"));
            fullSleeve.setText("Full Sleeve: " + intent.getStringExtra("fullSleeve"));
            measurementMessage.setVisibility(TextView.GONE);
        } else {
            arm.setText("Arm: N/A");
            fullChest.setText("Full Chest: N/A");
            fullShoulder.setText("Full Shoulder: N/A");
            neck.setText("Neck: N/A");
            sleeve.setText("Sleeve: N/A");
            bicep.setText("Bicep: N/A");
            wrist.setText("Wrist: N/A");
            waist.setText("Waist/Stomach: N/A");
            hips.setText("Hips/Seat: N/A");
            frontLength.setText("Front Length: N/A");
            frontChest.setText("Front Chest: N/A");
            backWidth.setText("Back Width: N/A");
            fullSleeve.setText("Full Sleeve: N/A");
            measurementMessage.setVisibility(TextView.VISIBLE);
            measurementMessage.setText("Tailor will arrive to take measurements.");
        }

        b2.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;

            String orderId = db.collection("Orders").document().getId();
            String userId = user.getUid();
            String tailorId = intent.getStringExtra("tailorId");

            if (tailorId == null || tailorId.isEmpty()) {
                Log.e("OrderConfirmation", "tailorId is null. Cannot proceed with order placement.");
                return;
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("userId", userId);
            map.put("tailorId", tailorId);

            // Customer Info
            Log.d("Debug", "Sending userName: " + customerName);
            map.put("customerName", customerName);
            Log.d("Debug", "Sending userPhone: " + customerPhone);
            map.put("customerPhone", customerPhone);
            Log.d("Debug", "Sending userAddress: " + customerAddress);
            map.put("customerAddress", customerAddress);


            // Tailor Info
            map.put("tailorName", intent.getStringExtra("tailorName"));
            map.put("tailorPhone", intent.getStringExtra("tailorPhone"));
            map.put("tailorAddress", intent.getStringExtra("tailorAddress"));
            map.put("tailorCity", intent.getStringExtra("tailorCity"));

            // Product Info
            map.put("productName", intent.getStringExtra("productName"));
            map.put("productPrice", intent.getStringExtra("productPrice"));
            map.put("productImage", intent.getStringExtra("productImage"));

            map.put("measurementType", measurementType);

            if ("self".equals(measurementType)) {
                map.put("arm", intent.getStringExtra("arm"));
                map.put("fullChest", intent.getStringExtra("fullChest"));
                map.put("fullShoulder", intent.getStringExtra("fullShoulder"));
                map.put("neck", intent.getStringExtra("neck"));
                map.put("sleeve", intent.getStringExtra("sleeve"));
                map.put("bicep", intent.getStringExtra("bicep"));
                map.put("wrist", intent.getStringExtra("wrist"));
                map.put("waist", intent.getStringExtra("waistStomach"));
                map.put("hips", intent.getStringExtra("hipsSeat"));
                map.put("frontLength", intent.getStringExtra("frontLength"));
                map.put("frontChest", intent.getStringExtra("frontChest"));
                map.put("backWidth", intent.getStringExtra("backWidth"));
                map.put("fullSleeve", intent.getStringExtra("fullSleeve"));
            }
            if ("appointment".equals(measurementType)){
                map.put("arm", null);
                map.put("fullChest", null);
                map.put("fullShoulder", null);
                map.put("neck", null);
                map.put("sleeve", null);
                map.put("bicep", null);
                map.put("wrist", null);
                map.put("waist", null);
                map.put("hips", null);
                map.put("frontLength", null);
                map.put("frontChest", null);
                map.put("backWidth", null);
                map.put("fullSleeve", null);
            }

            // Appointment
            map.put("appointmentDate", intent.getStringExtra("appointmentDate"));
            map.put("appointmentTime", intent.getStringExtra("appointmentTime"));
            map.put("appointmentStatus", intent.getStringExtra("appointmentStatus"));

            // Order tracking flags
            map.put("status", "Pending");
            map.put("orderPlaced", true);
            map.put("confirmedByTailor", false);
            map.put("inProgress", false);
            map.put("readyForTrial", false);
            map.put("delivered", false);

            db.collection("Orders").document(orderId).set(map)
                    .addOnSuccessListener(aVoid -> {
                        Intent i = new Intent(OrderConfirmation.this, Order.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("productName", intent.getStringExtra("productName"));
                        i.putExtra("productPrice", intent.getStringExtra("productPrice"));
                        i.putExtra("productImage", intent.getStringExtra("productImage"));
                        i.putExtra("orderId", orderId);
                        startActivity(i);
                    })
                    .addOnFailureListener(Throwable::printStackTrace);
        });


        // Navigation
        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));
    }
}
