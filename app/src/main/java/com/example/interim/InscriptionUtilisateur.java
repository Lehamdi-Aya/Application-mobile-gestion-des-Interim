package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class InscriptionUtilisateur extends AppCompatActivity {
    Button envoyee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inscription_new_utili);

        envoyee = findViewById(R.id.buttonEnvoyer);
        envoyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité Login lorsque l'utilisateur clique sur "envoyer"
                Intent intent = new Intent(InscriptionUtilisateur.this, Bienvenue.class);
                startActivity(intent);
            }
        });

    }
}