package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import DAO.EmployerDao;
import entity.Employer;
import BD.AppDatabase;

public class inscription_employeur_etapes03 extends AppCompatActivity {
    Button terminer;
    TextInputEditText editTextPassword;
    TextInputEditText editTextPasswordConfirm;
    TextInputLayout textFieldPassword;
    TextInputLayout textFieldConfirmPassword;
    AppDatabase db;
    EmployerDao employerDao;
    String email;
    String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_employeur_etapes03);

        terminer = findViewById(R.id.button_terminer);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextPasswordConfirm = findViewById(R.id.edit_text_password_confirm);
        textFieldPassword = findViewById(R.id.text_field_password);
        textFieldConfirmPassword = findViewById(R.id.text_field_confirm_password);

        db = AppDatabase.getInstance(this);
        employerDao = db.employerDao();

        // Récupération des informations transmises
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        companyName = intent.getStringExtra("companyName");

        // Vérifiez que les informations ne sont pas nulles
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Email manquant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (companyName == null || companyName.isEmpty()) {
            Toast.makeText(this, "Nom de l'entreprise manquant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextPasswordConfirm.getText().toString();

                if (validatePasswords(password, confirmPassword)) {
                    // Enregistrer le mot de passe et les autres informations
                    Employer employer = new Employer();
                    employer.setEmail(email);
                    employer.setCompanyName(companyName);
                    employer.setPassword(password);

                    // Enregistrer l'employeur dans la base de données
                    saveEmployer(employer);

                    // Afficher le message de confirmation et rediriger vers la page de connexion
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(inscription_employeur_etapes03.this, "Votre compte est enregistré", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(inscription_employeur_etapes03.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(inscription_employeur_etapes03.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatePasswords(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    private void saveEmployer(Employer employer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    employerDao.insert(employer);
                    Log.d("Database", "Employer inserted: " + employer.getEmail());
                } catch (Exception e) {
                    Log.e("Database", "Error inserting employer", e);
                }
            }
        }).start();
    }
}
