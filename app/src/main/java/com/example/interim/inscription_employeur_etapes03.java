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

public class inscription_employeur_etapes03 extends AppCompatActivity {
Button terminer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription_employeur_etapes03);
        terminer =findViewById(R.id.button_terminer);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Remplacer le fragment par le démarrage d'une nouvelle activité
                Intent intent = new Intent(inscription_employeur_etapes03.this, Login.class);
                startActivity(intent);
            }
        });

    }
}