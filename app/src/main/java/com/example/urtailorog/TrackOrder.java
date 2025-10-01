package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TrackOrder extends AppCompatActivity {

    ImageView home, profile, setting;
    ImageView step1, step2, step3, step4, step5;
    TextView Name;
    String orderId, productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        orderId = getIntent().getStringExtra("orderId");
        productName = getIntent().getStringExtra("productName");

        Name = findViewById(R.id.productName);
        step1 = findViewById(R.id.step1);
        step2 = findViewById(R.id.step2);
        step3 = findViewById(R.id.step3);
        step4 = findViewById(R.id.step4);
        step5 = findViewById(R.id.step5);

        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nev);

        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));

        Name.setText("Order: " + productName);

        loadOrderStatus();
    }

    private void loadOrderStatus() {
        FirebaseFirestore.getInstance().collection("Orders")
                .document(orderId)
                .addSnapshotListener((snapshot, e) -> {
                    if (snapshot != null && snapshot.exists()) {
                        step1.setImageResource(snapshot.getBoolean("orderPlaced") != null && snapshot.getBoolean("orderPlaced") ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                        step2.setImageResource(snapshot.getBoolean("confirmedByTailor") != null && snapshot.getBoolean("confirmedByTailor") ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                        step3.setImageResource(snapshot.getBoolean("inProgress") != null && snapshot.getBoolean("inProgress") ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                        step4.setImageResource(snapshot.getBoolean("readyForTrial") != null && snapshot.getBoolean("readyForTrial") ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                        step5.setImageResource(snapshot.getBoolean("delivered") != null && snapshot.getBoolean("delivered") ? R.drawable.ic_checked : R.drawable.ic_unchecked);
                    }
                });
    }
}
