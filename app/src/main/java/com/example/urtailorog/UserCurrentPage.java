package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserCurrentPage extends AppCompatActivity {

    LinearLayout orderListLayout;
    ImageView home, profile, setting;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_current_page);

        orderListLayout = findViewById(R.id.order_list_layout);
        home = findViewById(R.id.home_nav);
        profile = findViewById(R.id.profile_nav);
        setting = findViewById(R.id.setting_nav);

        btn = findViewById(R.id.btnHistory);

        btn.setOnClickListener(v-> startActivity(new Intent(this, UserHistoryPage.class)));
        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));

        fetchPendingOrders();
    }

    private void fetchPendingOrders() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference ordersRef = FirebaseFirestore.getInstance().collection("Orders");
        Log.d("USER_ID", "Current user ID: " + userId);

        ordersRef.whereEqualTo("userId", userId)
                .whereEqualTo("status", "Pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("UserCurrentPage", "Fetched: " + queryDocumentSnapshots.size());

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String orderId = doc.getId();
                        String productName = doc.getString("productName");

                        View orderView = LayoutInflater.from(this).inflate(R.layout.item_user_order, null);
                        TextView txtProduct = orderView.findViewById(R.id.product_name_txt);
                        txtProduct.setText(productName);

                        orderView.setOnClickListener(v -> {
                            Intent intent = new Intent(this, TrackOrder.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("productName", productName);
                            startActivity(intent);
                        });

                        orderListLayout.addView(orderView);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE_ERROR", "Failed to fetch orders: ", e);
                    Toast.makeText(this, "Failed to fetch orders: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
