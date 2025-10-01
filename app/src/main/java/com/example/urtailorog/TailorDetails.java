package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TailorDetails extends AppCompatActivity {

    EditText buisness, phone, address, city, state;
    Button updateBtn;
    EditText description;
    FirebaseAuth tailorAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_details);

        // Initialize Firebase
        tailorAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Find Views
        description = findViewById(R.id.tailor_editTextDescription);
        buisness = findViewById(R.id.tailor_editBusinessName);
        phone = findViewById(R.id.tailor_editTextPhone);
        address = findViewById(R.id.tailor_editTextAddress);
        city = findViewById(R.id.tailor_editTextCity);
        state = findViewById(R.id.tailor_editTextState);
        updateBtn = findViewById(R.id.tailor_updateBtn);

        // Button Click Listener to update the tailor details
        updateBtn.setOnClickListener(v -> {
            String buisnessName = buisness.getText().toString();
            String phoneNumber = phone.getText().toString().trim();
            String addressText = address.getText().toString().trim();
            String cityText = city.getText().toString().trim();
            String stateText = state.getText().toString().trim();
            String descText = description.getText().toString().trim();

            // Validation
            if (phoneNumber.isEmpty() || addressText.isEmpty() || cityText.isEmpty() || stateText.isEmpty() || buisnessName.isEmpty() || descText.isEmpty()) {
                Toast.makeText(this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get UID from Firebase Authentication
            String uid = tailorAuth.getCurrentUser().getUid();

            // Map the details to be updated
            Map<String, Object> tailorDetails = new HashMap<>();
            tailorDetails.put("business", buisnessName);
            tailorDetails.put("phone", phoneNumber);
            tailorDetails.put("address", addressText);
            tailorDetails.put("city", cityText);
            tailorDetails.put("state", stateText);
            tailorDetails.put("description", descText);

            // Update the document in Firestore using the UID
            db.collection("tailors").document(uid)
                    .update(tailorDetails)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Details updated successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,TailorDashboard.class));  // Close the activity or navigate to another page
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error updating details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
