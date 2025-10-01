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

public class TailorSignin extends AppCompatActivity {

    EditText email, password;
    Button signInBtn, signUpBtn;
    CheckBox showPassword;
    FirebaseAuth tailorAuth;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_signin);

        tailorAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.tailor_email);
        password = findViewById(R.id.tailor_password);
        signInBtn = findViewById(R.id.signin_btn);
        signUpBtn = findViewById(R.id.signup_btn);
        showPassword = findViewById(R.id.showPassword);
        txt = findViewById(R.id.user);

        showPassword.setOnCheckedChangeListener((buttonView, isChecked) ->
                password.setInputType(isChecked ?
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                        android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)
        );

        txt.setOnClickListener(view -> {
            startActivity(new Intent(this,MainActivity.class));
        });

        signInBtn.setOnClickListener(v -> {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            tailorAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnSuccessListener(a -> startActivity(new Intent(this, TailorDashboard.class)))
                    .addOnFailureListener(e -> Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show());
        });

        signUpBtn.setOnClickListener(v -> startActivity(new Intent(this, TailorSignup.class)));
    }
}
