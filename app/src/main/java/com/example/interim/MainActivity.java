package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button signUp;
    Button login;
    AlertDialog dialog;
    Button anonyme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher la boîte de dialogue lorsque l'utilisateur clique sur "Sign Up"
                showTypeUtilisateurDialog();
            }

        });

        login = findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité Login lorsque l'utilisateur clique sur "Login"
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


        anonyme = findViewById(R.id.anonyme);
        anonyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité Login lorsque l'utilisateur clique sur "Login"
                Intent intent = new Intent(MainActivity.this, Utilisateur_Anonyme.class);
                startActivity(intent);
            }
        });


    }

    private void showTypeUtilisateurDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.activity_type_utilisateur, null);
            builder.setView(dialogView);
            dialog = builder.create();
            dialog.show();

            Button chercheurEmploiButton = dialogView.findViewById(R.id.chercheurEmploi);
            chercheurEmploiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, NewUtilisateur.class);
                    startActivity(intent);
                }
            });

            Button employeur = dialogView.findViewById(R.id.employeur);
            employeur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    // Remplacer le fragment par le démarrage d'une nouvelle activité
                    Intent intent = new Intent(MainActivity.this, inscription_employeur_etape01.class);
                    startActivity(intent);
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
