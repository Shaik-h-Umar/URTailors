package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserSignup extends AppCompatActivity {

    EditText username, email, password, rePassword;
    Button signupBtn,signinBtn;
    FirebaseAuth auth;
    CheckBox showPasswordCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        auth = FirebaseAuth.getInstance();
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        rePassword = findViewById(R.id.editTextRePassword);
        signupBtn = findViewById(R.id.Signup);
        signinBtn = findViewById(R.id.signin);
        showPasswordCheckbox = findViewById(R.id.showPasswordCheckbox);


        showPasswordCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                password.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                rePassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Hide password
                password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                rePassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

            auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener(a -> {
                        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, UserDetails.class));
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        signinBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
