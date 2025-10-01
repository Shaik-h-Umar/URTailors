package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TailorAppointment extends AppCompatActivity {

    ImageView homenev, ordernev, profilnev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_appointment);

        homenev = findViewById(R.id.homenev);
        ordernev = findViewById(R.id.ordernev);
        profilnev = findViewById(R.id.profilnev);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String tailorId = auth.getCurrentUser().getUid();

        LinearLayout appointmentContainer = findViewById(R.id.appointmentContainer);

        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("measurementType", "appointment")
                .whereEqualTo("appointmentStatus","Pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("customerName");
                        String phone = doc.getString("customerPhone");
                        String address = doc.getString("customerAddress");
                        String docId = doc.getId(); // To delete later

                        View card = getLayoutInflater().inflate(R.layout.appointment_card_layout, null);
                        ((TextView) card.findViewById(R.id.customerName)).setText("Name: " + name);
                        ((TextView) card.findViewById(R.id.customerPhone)).setText("Phone: " + phone);
                        ((TextView) card.findViewById(R.id.customerAddress)).setText("Address: " + address);

                        Button btnRemove = card.findViewById(R.id.btnRemove);
                        btnRemove.setOnClickListener(v -> {
                            db.collection("Orders").document(docId)
                                    .update("appointmentStatus", "completed")
                                    .addOnSuccessListener(unused -> appointmentContainer.removeView(card));
                        });


                        appointmentContainer.addView(card);
                    }

                });


        homenev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorAppointment.this,TailorDashboard.class);
                startActivity(i);
            }
        });

        ordernev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorAppointment.this,TailorAllorder.class);
                startActivity(i);
            }
        });


        profilnev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorAppointment.this, TailorProfile.class);
                startActivity(i);
            }
        });
    }
}