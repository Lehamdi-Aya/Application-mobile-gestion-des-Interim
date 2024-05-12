package com.example.interim;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CandidatDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_candidat_details);
        // Récupérer les données de l'intention
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("Nom");
            String date = extras.getString("Datedenaissance");

            // Afficher les données dans les TextView correspondants
            TextView titreTextView = findViewById(R.id.candidate_fullname);
            titreTextView.setText(titre);
            TextView dateTextView = findViewById(R.id.application_date);
            dateTextView.setText(date);


        }
    }
}