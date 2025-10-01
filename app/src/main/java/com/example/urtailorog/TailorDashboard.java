package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TailorDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_dashboard);

        ImageView im1 = findViewById(R.id.trollyeimg);
        ImageView im2 = findViewById(R.id.postim);
        ImageView im3 = findViewById(R.id.heartim);
        ImageView im4 = findViewById(R.id.starim);

        TextView orderCount = findViewById(R.id.orderCount);
        TextView appointmentCount = findViewById(R.id.appointmentCount);

        Button b = findViewById(R.id.dashbtn);
        ImageView ordernev = findViewById(R.id.ordernev);
        ImageView contectnev = findViewById(R.id.contectnev);
        ImageView profilenv = findViewById(R.id.profilenev);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String tailorId = auth.getCurrentUser().getUid();

        // Fetch pending orders
        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("status", "Pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();
                    orderCount.setText("Total Orders : " + count);
                });

        // Fetch pending appointments
        db.collection("Orders")
                .whereEqualTo("tailorId", tailorId)
                .whereEqualTo("appointmentStatus", "Pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int count = queryDocumentSnapshots.size();
                    appointmentCount.setText("Pending Appointment : " + count);
                });

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this,TailorAllorder.class);
                startActivity(i);
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this, TailorAppointment.class);
                startActivity(i);
            }
        });

        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this, TailorCompletedorder.class);
                startActivity(i);
            }
        });

        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this, TailorEarnings.class);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this,TailorAllorder.class);
                startActivity(i);
            }
        });



        ordernev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this,TailorAllorder.class);
                startActivity(i);
            }
        });

        contectnev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this, TailorAppointment.class);
                startActivity(i);
            }
        });

        profilenv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorDashboard.this, TailorProfile.class);
                startActivity(i);
            }
        });
    }
}