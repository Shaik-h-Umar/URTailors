package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button signInBtn, signUpBtn;
    CheckBox showPassword;
    TextView tailorRedirect;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signInBtn = findViewById(R.id.signIn_user_Btn);
        signUpBtn = findViewById(R.id.signUp_user_Btn);
        tailorRedirect = findViewById(R.id.signup_tailor);
        showPassword = findViewById(R.id.showPasswordCheckbox);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // User already logged in
            startActivity(new Intent(this, UserDashboard.class)); // change to your dashboard
            finish();
        }


        showPassword.setOnCheckedChangeListener((buttonView, isChecked) ->
                password.setInputType(isChecked ?
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                        android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
        );

        signInBtn.setOnClickListener(v -> {
            String emailStr = email.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();

            if (emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(emailStr, passwordStr)
                    .addOnSuccessListener(a -> startActivity(new Intent(this, UserDashboard.class)))
                    .addOnFailureListener(e -> Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show());
        });

        signUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, UserSignup.class));
        });

        tailorRedirect.setOnClickListener(v -> {
           startActivity(new Intent(this, TailorSignin.class));
        });
    }
}
