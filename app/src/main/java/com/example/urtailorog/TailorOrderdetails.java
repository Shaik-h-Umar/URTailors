package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class TailorOrderdetails extends AppCompatActivity {

    TextView customerName, customerPhone, customerAddress, ProductView;
    TextView arm, fullChest, fullShoulder, neck, sleeve, bicep, wrist,
            waistStomach, hipsSeat, frontLength, frontChest, backWidth, fullSleeve;
    String orderId, productName;
    TextView tvMeasurementMessage;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_orderdetails);

        // Customer details
        customerName = findViewById(R.id.customerName);
        customerPhone = findViewById(R.id.customerPhone);
        customerAddress = findViewById(R.id.customerAddress);
        ProductView = findViewById(R.id.pro);

        // Measurement TextViews from GridLayout
        arm = findViewById(R.id.arm);
        fullChest = findViewById(R.id.fullChest);
        fullShoulder = findViewById(R.id.fullShoulder);
        neck = findViewById(R.id.neck);
        sleeve = findViewById(R.id.sleeve);
        bicep = findViewById(R.id.bicep);
        wrist = findViewById(R.id.wrist);
        waistStomach = findViewById(R.id.waistStomach);
        hipsSeat = findViewById(R.id.hipsSeat);
        frontLength = findViewById(R.id.frontLength);
        frontChest = findViewById(R.id.frontChest);
        backWidth = findViewById(R.id.backWidth);
        fullSleeve = findViewById(R.id.fullSleeve);

        orderId = getIntent().getStringExtra("orderId");
        tvMeasurementMessage = findViewById(R.id.tvMeasurementMessage);
        btn = findViewById(R.id.updstatus);


        if (orderId != null) {
            loadOrderDetails(orderId);
        }

        // Bottom navigation
        ImageView homenev = findViewById(R.id.homenev);
        ImageView contectnev = findViewById(R.id.contectnev);
        ImageView profilenv = findViewById(R.id.profilenev);

        homenev.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        contectnev.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));
        profilenv.setOnClickListener(v -> startActivity(new Intent(this, TailorProfile.class)));
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackOrdertailor.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("productName", productName);
            startActivity(intent);
        });

    }

    private void loadOrderDetails(String orderId) {
        FirebaseFirestore.getInstance().collection("Orders")
                .document(orderId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Log.d("OrderData", "Snapshot: " + snapshot.getData());
                        customerName.setText("Name: " + snapshot.getString("customerName"));
                        customerPhone.setText("Phone: " + snapshot.getString("customerPhone"));
                        customerAddress.setText("Address: " + snapshot.getString("customerAddress"));
                        ProductView.setText("Product: " + snapshot.getString("productName"));

                        productName = snapshot.getString("productName");
                        String type = snapshot.getString("measurementType");

                        if ("self".equals(type)) {
                            arm.setText(snapshot.getString("arm"));
                            fullChest.setText(snapshot.getString("fullChest"));
                            fullShoulder.setText(snapshot.getString("fullShoulder"));
                            neck.setText(snapshot.getString("neck"));
                            sleeve.setText(snapshot.getString("sleeve"));
                            bicep.setText(snapshot.getString("bicep"));
                            wrist.setText(snapshot.getString("wrist"));
                            waistStomach.setText(snapshot.getString("waistStomach"));
                            hipsSeat.setText(snapshot.getString("hipsSeat"));
                            frontLength.setText(snapshot.getString("frontLength"));
                            frontChest.setText(snapshot.getString("frontChest"));
                            backWidth.setText(snapshot.getString("backWidth"));
                            fullSleeve.setText(snapshot.getString("fullSleeve"));
                        } else {
                            arm.setText("To be measured");
                            fullChest.setText("To be measured");
                            fullShoulder.setText("To be measured");
                            neck.setText("To be measured");
                            sleeve.setText("To be measured");
                            bicep.setText("To be measured");
                            wrist.setText("To be measured");
                            waistStomach.setText("To be measured");
                            hipsSeat.setText("To be measured");
                            frontLength.setText("To be measured");
                            frontChest.setText("To be measured");
                            backWidth.setText("To be measured");
                            fullSleeve.setText("To be measured");
                            tvMeasurementMessage.setVisibility(View.VISIBLE);
                            tvMeasurementMessage.setText("Tailor will visit the customer's address for measurements.");
                        }
                    }
                });
    }
}
