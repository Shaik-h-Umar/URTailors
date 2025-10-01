package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrackOrdertailor extends AppCompatActivity {
    CheckBox cb1, cb2, cb3, cb4, cb5;
    DocumentReference orderRef;
    String orderId, productName;
    Button btnCompleteOrder;
    TextView orderDetail;
    ImageView homenev,contectnev, profilenv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_ordertailor);

        homenev = findViewById(R.id.homenev);
        contectnev = findViewById(R.id.contectnev);
        profilenv = findViewById(R.id.profilenev);


        cb1 = findViewById(R.id.cb1); // Order Placed
        cb2 = findViewById(R.id.cb2); // Confirmed by Tailor
        cb3 = findViewById(R.id.cb3); // In Progress
        cb4 = findViewById(R.id.cb4); // Ready for Trial
        cb5 = findViewById(R.id.cb5); // Delivered
        orderDetail = findViewById(R.id.orderDetail);
        btnCompleteOrder = findViewById(R.id.btnCompleteOrder);

        orderId = getIntent().getStringExtra("orderId");
        productName = getIntent().getStringExtra("productName");

        orderDetail.setText("Order: "+productName);

        if (orderId == null) {
            Toast.makeText(this, "Order ID missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        orderRef = FirebaseFirestore.getInstance().collection("Orders").document(orderId);

        loadStatus();

        setCheckboxListener(cb1, "orderPlaced");
        setCheckboxListener(cb2, "confirmedByTailor");
        setCheckboxListener(cb3, "inProgress");
        setCheckboxListener(cb4, "readyForTrial");
        setCheckboxListener(cb5, "delivered");

        homenev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TrackOrdertailor.this,TailorDashboard.class);
                startActivity(i);
            }
        });

        btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderRef.update("status", "completed")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(TrackOrdertailor.this, "Order marked as completed!", Toast.LENGTH_SHORT).show();
                            finish(); // Optional: close this screen
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(TrackOrdertailor.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                        });
            }
        });


        contectnev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TrackOrdertailor.this, TailorAppointment.class);
                startActivity(i);
            }
        });

        profilenv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TrackOrdertailor.this, TailorProfile.class);
                startActivity(i);
            }
        });
    }

    private void loadStatus() {
        orderRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                cb1.setChecked(Boolean.TRUE.equals(snapshot.getBoolean("orderPlaced")));
                cb2.setChecked(Boolean.TRUE.equals(snapshot.getBoolean("confirmedByTailor")));
                cb3.setChecked(Boolean.TRUE.equals(snapshot.getBoolean("inProgress")));
                cb4.setChecked(Boolean.TRUE.equals(snapshot.getBoolean("readyForTrial")));
                cb5.setChecked(Boolean.TRUE.equals(snapshot.getBoolean("delivered")));
            }
        });
    }

    private void setCheckboxListener(CheckBox checkBox, String field) {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            orderRef.update(field, isChecked);
        });
    }
}
