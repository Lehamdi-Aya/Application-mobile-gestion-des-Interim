    package com.example.interim;


    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    public class OfferDetailsDialog extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_offer_details);
            Button postulerButton = findViewById(R.id.postulerButton);

            // Récupérer les données de l'intention
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String titre = extras.getString("titre");
                String localisation = extras.getString("localisation");
                String description = extras.getString("description");


                // Afficher les données dans les TextView correspondants
                TextView titreTextView = findViewById(R.id.titreTextView);
                titreTextView.setText(titre);

                TextView localisationTextView = findViewById(R.id.localisationTextView);
                localisationTextView.setText(localisation);

                TextView descriptionTextView = findViewById(R.id.descriptionTextView);
                descriptionTextView.setText(description);

            }
            postulerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OfferDetailsDialog.this, Postuller_a_unOffre.class);

                    // Démarrer l'activité
                    startActivity(intent);

                }
            });




        }
    }

