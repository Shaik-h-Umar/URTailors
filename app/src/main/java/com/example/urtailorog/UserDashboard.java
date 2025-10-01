package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.*;

public class UserDashboard extends AppCompatActivity {

    FirebaseFirestore db;
    LinearLayout productContainer;
    String customerName, customerAddress, customerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        ImageView order = findViewById(R.id.ordernev);
        ImageView profile = findViewById(R.id.profilenev);
        ImageView setting = findViewById(R.id.settingnev);

        productContainer = findViewById(R.id.product_container); // Add ID to parent layout

        // Firebase instance
        db = FirebaseFirestore.getInstance();

        // Bottom nav click listeners
        order.setOnClickListener(v -> startActivity(new Intent(this, UserCurrentPage.class)));
        profile.setOnClickListener(v -> startActivity(new Intent(this, UserProfile.class)));
        setting.setOnClickListener(v -> startActivity(new Intent(this, UserSetting.class)));

        Tasks.whenAllSuccess(loadCustomerDetails(), loadProductDetails())
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Customer and products loaded");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to load one or both datasets", e);
                });
    }

    private Task<Void> loadCustomerDetails() {
        TaskCompletionSource<Void> taskSource = new TaskCompletionSource<>();

        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        customerName = doc.getString("name");
                        customerAddress = doc.getString("address");
                        customerPhone = doc.getString("phone");
                    }
                    taskSource.setResult(null); // Mark task as complete
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStoreError", "Error Loading User", e);
                    taskSource.setException(e); // Mark task as failed
                });

        return taskSource.getTask();
    }

    private Task<Void> loadProductDetails() {
        TaskCompletionSource<Void> taskSource = new TaskCompletionSource<>();

        db.collection("product")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String name = doc.getString("name");
                            String imageUrl = doc.getString("imgURL");
                            String price = doc.getString("price");
                            addProductView(name, imageUrl, price);
                        }
                    }
                    taskSource.setResult(null);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error loading products", e);
                    taskSource.setException(e);
                });

        return taskSource.getTask();
    }


    private void loadProductsAndCustomer() {
        Task<Void> loadCustomerTask = loadCustomerDetails();  // This could return a Task
        Task<Void> loadProductTask = loadProductDetails();    // This could return a Task

        Tasks.whenAllSuccess(loadCustomerTask, loadProductTask)
                .addOnSuccessListener(aVoid -> {
                    // All data loaded, proceed further
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }



    private void addProductView(String name, String imageUrl, String price) {
        View productView = getLayoutInflater().inflate(R.layout.product_items, productContainer, false);

        ImageView imageView = productView.findViewById(R.id.product_image);
        TextView nameView = productView.findViewById(R.id.product_name);
        TextView priceView = productView.findViewById(R.id.product_price);

        nameView.setText(name);
        priceView.setText("₹" + price);
        Glide.with(this).load(imageUrl).into(imageView);

        productView.setOnClickListener(v -> {
            Intent i = new Intent(UserDashboard.this, TailorSelection.class);
            i.putExtra("productName", name);
            i.putExtra("productImage", imageUrl);
            i.putExtra("productPrice", price);
            i.putExtra("customerName", customerName);
            i.putExtra("customerPhone", customerPhone);
            i.putExtra("customerAddress", customerAddress);
            Log.d("Debug", "Customer Name: " + customerName); // Check if customer name is passed correctly
            Log.d("Debug", "Customer Phone: " + customerPhone); // Check if customer phone is passed correctly
            Log.d("Debug", "Customer Address: " + customerAddress); // Check if customer address is passed correctly

            startActivity(i);
        });

        productContainer.addView(productView);
    }
}