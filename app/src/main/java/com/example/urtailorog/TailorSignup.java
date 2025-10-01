package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TailorSignup extends AppCompatActivity {

    EditText username, email, password, rePassword;
    Button signupBtn, signinBtn;
    CheckBox showPasswordCheckBox;

    FirebaseAuth tailorAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_signup);

        tailorAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        username = findViewById(R.id.tailor_editTextUsername);
        email = findViewById(R.id.tailor_editTextEmail);
        password = findViewById(R.id.tailor_editTextPassword);
        rePassword = findViewById(R.id.tailor_editTextRePassword);
        signupBtn = findViewById(R.id.tailor_Signup);
        signinBtn = findViewById(R.id.signin);
        showPasswordCheckBox = findViewById(R.id.showPassword);

        // Show/hide password
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                rePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                rePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        signupBtn.setOnClickListener(v -> {
            String uname = username.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String rePass = rePassword.getText().toString().trim();

            if (uname.isEmpty() || !mail.contains("@") || pass.length() < 6 || !pass.equals(rePass)) {
                Toast.makeText(this, "Check your inputs", Toast.LENGTH_SHORT).show();
                return;
            }

            tailorAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener(a -> {
                        String uid = tailorAuth.getCurrentUser().getUid();

                        Map<String, Object> tailor = new HashMap<>();
                        tailor.put("username", uname);
                        tailor.put("email", mail);

                        db.collection("tailors").document(uid).set(tailor)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Tailor registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, TailorDetails.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Auth failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        signinBtn.setOnClickListener(v -> {
            startActivity(new Intent(TailorSignup.this, TailorSignin.class));
            finish();
        });
    }
}
