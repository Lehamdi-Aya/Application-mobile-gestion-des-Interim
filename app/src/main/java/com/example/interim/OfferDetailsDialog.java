package com.example.interim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log; // Importer la classe Log
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import BD.AppDatabase;
import entity.Employer;

public class OfferDetailsDialog extends AppCompatActivity {

    private TextView titreTextView;
    private TextView localisationTextView;
    private TextView descriptionTextView;
    private TextView salaireTextView;
    private TextView dateDebutTextView;
    private TextView employerTextView;
    private Button postulerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_offer_details);

        titreTextView = findViewById(R.id.titreTextView);
        localisationTextView = findViewById(R.id.localisationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        salaireTextView = findViewById(R.id.salaireTextView);
        dateDebutTextView = findViewById(R.id.dateDebutTextView);
        employerTextView = findViewById(R.id.employerTextView);
        postulerButton = findViewById(R.id.postulerButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String localisation = intent.getStringExtra("localisation");
        String description = intent.getStringExtra("description");
        double minSalary = intent.getDoubleExtra("minSalary", 0);
        double maxSalary = intent.getDoubleExtra("maxSalary", 0);
        String startDate = intent.getStringExtra("startDate");
        int employerId = intent.getIntExtra("employerId", 0);
        int offreId = intent.getIntExtra("offreId", 0); // Récupération de l'offreId

        Log.d("OfferDetailsDialog", "offreId: " + offreId); // Log pour vérifier l'offreId

        titreTextView.setText(title);
        localisationTextView.setText(localisation);
        descriptionTextView.setText(description);
        salaireTextView.setText("Salaire: " + minSalary + " - " + maxSalary);
        dateDebutTextView.setText("Date de début: " + startDate);

        // Charger le nom de l'entreprise de l'employeur
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                Employer employer = db.employerDao().getEmployerById(employerId);
                Log.d("OfferDetailsDialog", "Employer ID: " + employerId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (employer != null) {
                            employerTextView.setText("Employeur: " + employer.getCompanyName());
                        } else {

                            employerTextView.setText("AEDL");
                            Log.d("OfferDetailsDialog", "Employer not found for ID: " + employerId);
                        }
                    }
                });
            }
        }).start();


        // Gestion du clic sur le bouton Postuler
        postulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupérer l'ID de l'utilisateur à partir des préférences partagées
                int userId = getUserId();

                if (userId == -1) {
                    // L'ID de l'utilisateur n'a pas été trouvé
                    Toast.makeText(OfferDetailsDialog.this, "Erreur: utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent postulerIntent = new Intent(OfferDetailsDialog.this, Postuller_a_unOffre.class);
                postulerIntent.putExtra("userId", userId);  // Assurez-vous que userId est correctement défini

                // Passer les données nécessaires à l'activité Postuller_a_unOffre
                postulerIntent.putExtra("offreId", offreId); // Assurez-vous que l'offreId est passé correctement
                postulerIntent.putExtra("title", title);
                postulerIntent.putExtra("localisation", localisation);
                postulerIntent.putExtra("description", description);
                postulerIntent.putExtra("minSalary", minSalary);
                postulerIntent.putExtra("maxSalary", maxSalary);
                postulerIntent.putExtra("startDate", startDate);
                postulerIntent.putExtra("employerId", employerId);

                Log.d("OfferDetailsDialog", "Sending offreId: " + offreId + " to Postuller_a_unOffre"); // Log pour vérifier l'offreId envoyé
                startActivity(postulerIntent);
            }
        });
    }

    private int getUserId() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.getInt("userId", -1); // Retourne -1 si l'ID de l'utilisateur n'est pas trouvé
    }
}
