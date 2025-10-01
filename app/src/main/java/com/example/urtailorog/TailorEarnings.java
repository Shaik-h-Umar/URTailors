package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TailorEarnings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_earnings);

        ImageView homenev = findViewById(R.id.homenev);
        ImageView ordernev = findViewById(R.id.ordernev);
        ImageView contectnev = findViewById(R.id.contectnev);
        ImageView profilenv = findViewById(R.id.profilenev);

        TextView tvTotalOrders = findViewById(R.id.tvTotalOrders);
        TextView tvTotalRevenue = findViewById(R.id.tvTotalRevenue); // Add this in XML
        LinearLayout earningsContainer = findViewById(R.id.earningsContainer);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String tailorId = auth.getCurrentUser().getUid();

        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("status", "completed")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalOrders = queryDocumentSnapshots.size();
                    int totalRevenue = 0;

                    tvTotalOrders.setText("Total Completed Orders: " + totalOrders);

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("productName");
                        String priceStr = doc.getString("productPrice");
                        int price = 0;
                        if (priceStr != null && !priceStr.isEmpty()) {
                            try {
                                price = Integer.parseInt(priceStr);
                            } catch (NumberFormatException e) {
                                price = 0;
                            }
                        }

                        totalRevenue += price;

                        View card = getLayoutInflater().inflate(R.layout.earnings_card_layout, null);
                        ((TextView) card.findViewById(R.id.productName)).setText("Product: " + name);
                        ((TextView) card.findViewById(R.id.productPrice)).setText("Price: ₹" + price);

                        earningsContainer.addView(card);
                    }

                    tvTotalRevenue.setText("Total Revenue: ₹" + totalRevenue);
                });

        homenev.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        ordernev.setOnClickListener(v -> startActivity(new Intent(this, TailorAllorder.class)));
        contectnev.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));
        profilenv.setOnClickListener(v -> startActivity(new Intent(this, TailorProfile.class)));
    }
}
