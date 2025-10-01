package com.example.urtailorog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TailorPrivacy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tailor_privacy);

        ImageView homenev = findViewById(R.id.homenev);
        ImageView ordernev = findViewById(R.id.ordernev);
        ImageView contectnev = findViewById(R.id.contectnev);


        homenev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorPrivacy.this,TailorDashboard.class);
                startActivity(i);
            }
        });

        ordernev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorPrivacy.this,TailorAllorder.class);
                startActivity(i);
            }
        });

        contectnev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(TailorPrivacy.this, TailorAppointment.class);
                startActivity(i);
            }
        });
    }
}