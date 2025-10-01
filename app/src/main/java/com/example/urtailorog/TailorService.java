package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TailorService extends AppCompatActivity {

    ImageView homenev, ordernev, contectnev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_service);

        homenev = findViewById(R.id.homenev);
        ordernev = findViewById(R.id.ordernev);
        contectnev = findViewById(R.id.contectnev);

        // Fields from XML
        TextView shopName = findViewById(R.id.tailor_shop_name);
        TextView email = findViewById(R.id.tailor_email);
        TextView phone = findViewById(R.id.tailor_phone);
        TextView city = findViewById(R.id.tailor_city);
        TextView address = findViewById(R.id.tailor_address);
        TextView description = findViewById(R.id.tailor_description);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String tailorId = auth.getCurrentUser().getUid();

        db.collection("tailors").document(tailorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        shopName.setText(documentSnapshot.getString("business"));
                        email.setText(documentSnapshot.getString("email"));
                        phone.setText(documentSnapshot.getString("phone"));
                        city.setText(documentSnapshot.getString("city"));
                        address.setText(documentSnapshot.getString("address"));
                        description.setText(documentSnapshot.getString("description"));
                    }
                });

        homenev.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        ordernev.setOnClickListener(v -> startActivity(new Intent(this, TailorAllorder.class)));
        contectnev.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));
    }
}
