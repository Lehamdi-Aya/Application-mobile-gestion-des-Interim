package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InscriptionNewUtilisateur extends AppCompatActivity {
    Button envoyee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription_new_utilisateur);
        envoyee = findViewById(R.id.buttonEnvoyer);
        envoyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InscriptionNewUtilisateur.this, Bienvenue.class);
                startActivity(intent);
            }
        });


    }
}