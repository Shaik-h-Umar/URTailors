package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TailorSelection extends AppCompatActivity {
    LinearLayout tailorContainer;
    FirebaseFirestore db;
    String productName, productPrice, productImage;
    String customerName, customerAddress, customerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_selection);

        tailorContainer = findViewById(R.id.tailor_container);
        db = FirebaseFirestore.getInstance();

        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getStringExtra("productPrice");
        productImage = getIntent().getStringExtra("productImage");
        customerAddress = getIntent().getStringExtra("customerAddress");
        customerName = getIntent().getStringExtra("customerName");
        customerPhone = getIntent().getStringExtra("customerPhone");

        loadTailors();
    }

    private void loadTailors() {
        db.collection("tailors")
                .get()
                .addOnSuccessListener(querySnapshots -> {
                    for (DocumentSnapshot doc : querySnapshots) {
                        String name = doc.getString("business");
                        String address = doc.getString("address");
                        String city = doc.getString("city");
                        String phone = doc.getString("phone");
                        String email = doc.getString("email");
                        String state = doc.getString("state");
                        String username = doc.getString("username");
                        String tailorId = doc.getId();


                        View tailorView = getLayoutInflater().inflate(R.layout.tailor_item, tailorContainer, false);

                        TextView nameView = tailorView.findViewById(R.id.tailor_name);
                        TextView contactView = tailorView.findViewById(R.id.tailor_contact);
                        TextView addressView = tailorView.findViewById(R.id.tailor_address);
                        TextView cityView = tailorView.findViewById(R.id.tailor_city);

                        nameView.setText(name);
                        contactView.setText("Contact: " + phone);
                        addressView.setText("Address: " + address);
                        cityView.setText("City: " + city);

                        tailorView.setOnClickListener(v -> {
                            Intent i = new Intent(TailorSelection.this, SelectedTailorDetail.class);
                            i.putExtra("productName", productName);
                            i.putExtra("productPrice", productPrice);
                            i.putExtra("productImage", productImage);
                            i.putExtra("tailorName", name);
                            i.putExtra("tailorPhone", phone);
                            i.putExtra("tailorAddress", address);
                            i.putExtra("tailorCity", city);
                            i.putExtra("tailorEmail", email);
                            i.putExtra("tailorState", state);
                            i.putExtra("tailorUsername", username);
                            i.putExtra("tailorId", tailorId);
                            i.putExtra("customerAddress", customerAddress);
                            i.putExtra("customerName", customerName);
                            i.putExtra("customerPhone", customerPhone);
                            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
                            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
                            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly

                            startActivity(i);
                        });

                        tailorContainer.addView(tailorView);
                    }
                })
                .addOnFailureListener(e -> Log.e("TailorLoad", "Error loading tailors", e));
    }
}
