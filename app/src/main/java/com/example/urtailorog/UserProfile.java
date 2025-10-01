package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfile extends AppCompatActivity {

    TextView usernameText, emailText, logoutTv;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView privacy, tailor, history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile); // Use your actual XML filename

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        history = findViewById(R.id.historytv);
        privacy = findViewById(R.id.privecytv);
        tailor = findViewById(R.id.tailortv);

        tailor.setOnClickListener(v-> startActivity(new Intent(this, TailorSignup.class)));
        privacy.setOnClickListener(v-> startActivity(new Intent(this, UserSetting.class)));
        history.setOnClickListener(v-> startActivity(new Intent(this, UserHistoryPage.class)));

        // Check if user is logged in
        if (user == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // Set up views
        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.user_email);
        logoutTv = findViewById(R.id.logouttv);

        // Show user data
        emailText.setText(user.getEmail());
        usernameText.setText("User"); // Replace with real username if stored separately

        // Logout logic
        logoutTv.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(UserProfile.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Add navigation button listeners if needed (example below)
        ImageView homeNav = findViewById(R.id.homenev);
        homeNav.setOnClickListener(v -> {
            startActivity(new Intent(this, UserDashboard.class));
        });

        ImageView orderNav = findViewById(R.id.ordernev);
        orderNav.setOnClickListener(v -> {
            startActivity(new Intent(this, UserCurrentPage.class));
        });

        ImageView settingsNav = findViewById(R.id.settingnev);
        settingsNav.setOnClickListener( v -> {
            startActivity(new Intent(this, UserSetting.class));
        });
    }
}
