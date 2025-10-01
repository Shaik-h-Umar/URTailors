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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserHistoryPage extends AppCompatActivity {

    LinearLayout orderContainer;
    FirebaseFirestore db;
    String userId;
    ImageView home, profile, setting;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_history_page);

        btn = findViewById(R.id.btnCurrent);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        setting = findViewById(R.id.setting);

        btn.setOnClickListener(v-> startActivity(new Intent(this, UserCurrentPage.class)));
        home.setOnClickListener(v -> startActivity(new Intent(this, UserDashboard.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));

        orderContainer = findViewById(R.id.orderContainer);
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadCompletedOrders();
    }

    private void loadCompletedOrders() {
        db.collection("Orders")
                .whereEqualTo("userId", userId)
                .whereEqualTo("status", "completed")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String product = doc.getString("productName");

                        View orderView = getLayoutInflater().inflate(R.layout.order_item, null);
                        TextView nameText = orderView.findViewById(R.id.orderName);
                        nameText.setText(product);

                        orderContainer.addView(orderView);
                    }
                });
    }
}
