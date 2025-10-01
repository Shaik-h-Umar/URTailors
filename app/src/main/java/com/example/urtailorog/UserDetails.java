package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserDetails extends AppCompatActivity {

    EditText etName, etPhone, etAddress;
    Button btnSubmit;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the user's UID
            String uid = auth.getCurrentUser().getUid();

            // Create a HashMap for user details
            HashMap<String, String> userDetails = new HashMap<>();
            userDetails.put("name", name);
            userDetails.put("phone", phone);
            userDetails.put("address", address);

            // Save the user details to Firestore under "users" collection with the UID as document ID
            db.collection("users").document(uid).set(userDetails)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Details saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserDetails.this, UserDashboard.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
