package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TailorCompletedorder extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    LinearLayout orderContainer;

    ImageView homenev, contectnev, profilenv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_completedorder);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        orderContainer = findViewById(R.id.orderContainer); // ID from XML

        homenev = findViewById(R.id.homenev);
        contectnev = findViewById(R.id.contectnev);
        profilenv = findViewById(R.id.profilenev);

        fetchCompletedOrders();

        homenev.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        contectnev.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));
        profilenv.setOnClickListener(v -> startActivity(new Intent(this, TailorProfile.class)));
    }

    private void fetchCompletedOrders() {
        String tailorId = auth.getCurrentUser().getUid();

        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("status", "completed")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String product = doc.getString("productName");
                        String customerName = doc.getString("customerName");
                        String price = doc.getString("productPrice");

                        View card = LayoutInflater.from(this).inflate(R.layout.order_card_layout, null);

                        TextView productName = card.findViewById(R.id.productName);
                        TextView customer = card.findViewById(R.id.customerName);
                        TextView orderPrice = card.findViewById(R.id.orderPrice);

                        productName.setText(product);
                        customer.setText("Customer: " + customerName);
                        orderPrice.setText("₹" + price);

                        orderContainer.addView(card);
                    }
                });
    }
}
