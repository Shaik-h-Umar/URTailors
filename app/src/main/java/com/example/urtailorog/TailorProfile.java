package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TailorProfile extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView username, useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        username = findViewById(R.id.textViewUsername);
        useremail = findViewById(R.id.textViewUserEmail);

        if (user != null && user.getEmail() != null) {
            useremail.setText(user.getEmail());

            FirebaseFirestore.getInstance()
                    .collection("tailors")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            String name = snapshot.getString("business");
                            if (name != null) {
                                username.setText(name);
                            }
                        }
                    });
        }


        // Check if logged in
        if (user == null) {
            startActivity(new Intent(this, TailorSignin.class));
            finish();
            return;
        }

        username = findViewById(R.id.textViewUsername); // use correct ID
        useremail = findViewById(R.id.textViewUserEmail); // use correct ID

        if (user.getEmail() != null) {
            useremail.setText(user.getEmail());
        }

        // IDs from layout
        TextView about = findViewById(R.id.abouttv);
        TextView privacy = findViewById(R.id.privecytv);
        TextView service = findViewById(R.id.servicetv);
        TextView logout = findViewById(R.id.logouttv);

        ImageView homenev = findViewById(R.id.homenev);
        ImageView ordernev = findViewById(R.id.ordernev);
        ImageView contectnev = findViewById(R.id.contectnev);

        privacy.setOnClickListener(v -> startActivity(new Intent(this, TailorPrivacy.class)));
        service.setOnClickListener(v -> startActivity(new Intent(this, TailorService.class)));
        about.setOnClickListener(v -> startActivity(new Intent(this, TailorService.class )));

        logout.setOnClickListener(v -> {
            auth.signOut();
            Intent i = new Intent(this, TailorSignin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        homenev.setOnClickListener(v -> startActivity(new Intent(this, TailorDashboard.class)));
        ordernev.setOnClickListener(v -> startActivity(new Intent(this, TailorAllorder.class)));
        contectnev.setOnClickListener(v -> startActivity(new Intent(this, TailorAppointment.class)));

    }
}
