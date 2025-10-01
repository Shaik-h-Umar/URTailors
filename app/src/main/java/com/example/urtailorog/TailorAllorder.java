package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TailorAllorder extends AppCompatActivity {

    ImageView home, appointment, profile;
    LinearLayout orderContainer;
    FirebaseFirestore db;
    String tailorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_allorder);

        home = findViewById(R.id.homenev);
        appointment = findViewById(R.id.contectnev);
        profile = findViewById(R.id.profilenev);
        orderContainer = findViewById(R.id.order_container); // Add this LinearLayout in XML

        db = FirebaseFirestore.getInstance();
        tailorId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadOrders();

        home.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        appointment.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, TailorProfile.class)));
    }

    private void loadOrders() {
        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("status", "Pending")
                .get()
                .addOnSuccessListener(query -> {
                    orderContainer.removeAllViews();
                    for (QueryDocumentSnapshot doc : query) {
                        String orderId = doc.getId();
                        String productName = doc.getString("productName");
                        String CustomerName  = doc.getString("customerName");
                        String CustomerPhone = doc.getString("customerPhone");

                        View orderView = getLayoutInflater().inflate(R.layout.tailor_order_item, orderContainer, false);

                        TextView orderIdView = orderView.findViewById(R.id.order_id);
                        TextView productView = orderView.findViewById(R.id.product_name);
                        TextView userView = orderView.findViewById(R.id.user_name);

                        orderIdView.setText("Phone: " + CustomerPhone);
                        productView.setText("Product: " + productName);
                        userView.setText("Customer: " + CustomerName);

                        orderView.setOnClickListener(v -> {
                            Intent i = new Intent(TailorAllorder.this, TailorOrderdetails.class);
                            i.putExtra("orderId", orderId);
                            startActivity(i);
                        });

                        orderContainer.addView(orderView);
                    }
                })
                .addOnFailureListener(e -> Log.e("LoadOrders", "Error fetching orders", e));
    }
}
