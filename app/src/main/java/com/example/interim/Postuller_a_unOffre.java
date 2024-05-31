package com.example.interim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import BD.AppDatabase;
import entity.Candidature;

public class Postuller_a_unOffre extends AppCompatActivity {
    private Button buttonSend;
    private ActivityResultLauncher<Intent> cvLauncher;
    private ActivityResultLauncher<Intent> letterLauncher;
    private TextView textViewCvChooserHint;
    private TextView textViewCoverLetterHint;
    private Uri cvUri;
    private Uri coverLetterUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postuller_aun_offre);

        // Initialisation des vues
        textViewCvChooserHint = findViewById(R.id.text_view_cv_chooser_hint_candidacy_new);
        textViewCoverLetterHint = findViewById(R.id.text_view_cover_letter_hint_candidacy_new);
        buttonSend = findViewById(R.id.button_send);

        // Initialisation des lanceurs d'activités
        cvLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        cvUri = result.getData().getData();
                        textViewCvChooserHint.setText(cvUri.getPath());
                        getContentResolver().takePersistableUriPermission(cvUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
        );

        letterLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        coverLetterUri = result.getData().getData();
                        textViewCoverLetterHint.setText(coverLetterUri.getPath());
                        getContentResolver().takePersistableUriPermission(coverLetterUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }
        );

        findViewById(R.id.linear_layout_cv_choose).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            cvLauncher.launch(intent);
        });

        findViewById(R.id.linear_layout_cover_letter_choose).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            letterLauncher.launch(intent);
        });

        buttonSend.setOnClickListener(view -> {
            if (cvUri != null && coverLetterUri != null) {
                Intent intent = getIntent();
                int offreId = intent.getIntExtra("offreId", -1);

                // Récupérer userId depuis les préférences partagées
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);

                Log.d("Postuller_a_unOffre", "Received offreId: " + offreId); // Log pour vérifier l'offreId reçu


                Log.d("Postuller_a_unOffre", "offreId: " + offreId);
                Log.d("Postuller_a_unOffre", "userId: " + userId);

                if (userId != -1) {
                    new Thread(() -> {
                        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                        boolean isValidOffer = db.candidatureDao().isValidOffer(offreId);
                        boolean isValidUser = db.candidatureDao().isValidUser(userId);

                        Log.d("Postuller_a_unOffre", "isValidOffer: " + isValidOffer);
                        Log.d("Postuller_a_unOffre", "isValidUser: " + isValidUser);

                        // Vérifiez que les clés étrangères existent
                        if (isValidOffer && isValidUser) {
                            Candidature candidature = new Candidature(offreId, userId, cvUri.toString(), coverLetterUri.toString());
                            db.candidatureDao().insert(candidature);

                            runOnUiThread(() -> {
                                Toast.makeText(Postuller_a_unOffre.this, "Votre candidature a été bien envoyée", Toast.LENGTH_SHORT).show();
                                Intent navigationIntent = new Intent(Postuller_a_unOffre.this, ActivityNavigation.class);
                                startActivity(navigationIntent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(Postuller_a_unOffre.this, "Erreur: offre ou utilisateur invalide", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(Postuller_a_unOffre.this, "Erreur: utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Postuller_a_unOffre.this, "Veuillez ajouter un CV et une lettre de motivation", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
