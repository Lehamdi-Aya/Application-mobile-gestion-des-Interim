package com.example.interim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import BD.AppDatabase;
import entity.Candidature;

public class CandidatDetails extends AppCompatActivity {

    private static final String TAG = "CandidatDetails";
    private int candidatureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_candidat_details);

        // Récupérer les données de l'intention
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("Nom");
            String prenom = extras.getString("Prenom");
            String dateNaissance = extras.getString("Datedenaissance");
            candidatureId = extras.getInt("CandidatureId", -1); // Récupérer l'identifiant de la candidature

            Log.d(TAG, "Nom: " + nom);
            Log.d(TAG, "Prenom: " + prenom);
            Log.d(TAG, "Candidature ID: " + candidatureId);

            // Afficher les données dans les TextView correspondants
            TextView fullNameTextView = findViewById(R.id.candidate_fullname);
            fullNameTextView.setText(nom + " " + prenom);

            TextView dateTextView = findViewById(R.id.application_date);
            dateTextView.setText(dateNaissance);
        } else {
            Log.e(TAG, "No extras found in intent");
        }

        // Gestion du clic sur le bouton Accepter
        TextView acceptButton = findViewById(R.id.text_view_accept_candidacy);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Candidature acceptée");
                updateCandidacyState(true);
                // Vous pouvez également ajouter d'autres actions ici, comme mettre à jour l'état de la candidature dans la base de données
            }
        });

        // Gestion du clic sur le bouton Refuser
        TextView refuseButton = findViewById(R.id.text_view_refuse_candidacy);
        refuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Candidature refusée");
                updateCandidacyState(false);
                // Vous pouvez également ajouter d'autres actions ici, comme mettre à jour l'état de la candidature dans la base de données
            }
        });

        // Gestion du clic sur le bouton CV
        ImageView cvButton = findViewById(R.id.image_cv_more_arrow);
        cvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCandidatureDetails("CV");
            }
        });

        // Gestion du clic sur le bouton Lettre de motivation
        ImageView coverLetterButton = findViewById(R.id.image_cover_letter_more_arrow);
        coverLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCandidatureDetails("Lettre de motivation");
            }
        });

        // Gestion du clic sur le bouton Appeler
        findViewById(R.id.call_constraint_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer();
            }
        });

        // Gestion du clic sur le bouton Message
        findViewById(R.id.send_msg_constraint_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMessagingApp();
            }
        });

        // Gestion du clic sur le bouton Gmail
        findViewById(R.id.send_email_constraint_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGmailApp();
            }
        });
    }

    private void fetchCandidatureDetails(String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                Candidature candidature = db.candidatureDao().getCandidatureById(candidatureId);
                if (candidature != null) {
                    String uri = type.equals("CV") ? candidature.cvUri : candidature.coverLetterUri;
                    Log.d(TAG, type + " URI: " + uri);
                    if (uri != null && !uri.isEmpty()) {
                        openPdfInBrowser(uri);
                    } else {
                        Log.d(TAG, type + " URI is empty or null");
                    }
                } else {
                    Log.d(TAG, "Candidature not found");
                }
            }
        }).start();
    }

    private void openPdfInBrowser(String uriString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(intent, "Open PDF with"));
                } catch (Exception e) {
                    Log.e(TAG, "Error opening PDF in browser: " + e.getMessage());
                    Toast.makeText(CandidatDetails.this, "Erreur lors de l'ouverture du PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"));
        startActivity(intent);
    }

    private void openMessagingApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"));
        startActivity(intent);
    }


    private void openGmailApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"lehamdiaya425@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmation de réception de votre candidature - Entretien à venir");
        intent.putExtra(Intent.EXTRA_TEXT, "Nous avons bien reçu votre candidature pour le poste de Alternance JAVA/KOTLIN et nous tenons à vous remercier pour l'intérêt que vous portez à notre entreprise.\n" +
                "\n" +
                "Après une première évaluation de votre dossier, nous sommes heureux de vous informer que votre candidature a été retenue pour la prochaine étape du processus de recrutement. Vous serez contacté(e) prochainement pour convenir d'un entretien afin de mieux discuter de votre profil et de vos compétences.\n" +
                "\n" +
                "Nous vous prions de bien vouloir vérifier régulièrement vos emails et votre téléphone, car nous vous enverrons les détails de cet entretien dans les jours à venir.\n" +
                "\n" +
                "En attendant, si vous avez des questions ou besoin de plus amples informations, n'hésitez pas à nous contacter à cette adresse ou au [numéro de téléphone].\n" +
                "\n" +
                "Nous vous remercions encore une fois pour l'intérêt que vous portez à notre entreprise et nous nous réjouissons de pouvoir échanger avec vous prochainement.\n" +
                "\n" +
                "Bien cordialement,\n" +
                "\n" +
                "Mdme.LEHAMDI Aya\n" +
                "Votre équipe de recrutement\n" +
                "AEDL\n" +
                "04-33-15-90-14 \n" +
                "finderinterim@gmail.com");

        try {
            startActivity(Intent.createChooser(intent, "Send email with"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Aucune application de messagerie installée.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCandidacyState(boolean isAccepted) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView candidacyStateTextView = findViewById(R.id.candidacy_state_text);
                if (isAccepted) {
                    candidacyStateTextView.setText("Acceptée");
                    candidacyStateTextView.setTextColor(getResources().getColor(R.color.green_accepted));
                } else {
                    candidacyStateTextView.setText("Refusée");
                    candidacyStateTextView.setTextColor(getResources().getColor(R.color.red_refused));
                }
            }
        });
    }
}
