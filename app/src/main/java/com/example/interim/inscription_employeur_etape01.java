package com.example.interim;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import BD.AppDatabase;
import entity.Employer;

public class inscription_employeur_etape01 extends AppCompatActivity {
    Button next;
    TextInputEditText companyNameEditText;
    TextInputEditText nationalUniqueNumberEditText;
    TextInputEditText phoneNumberEditText;
    TextInputEditText emailEditText;
    TextInputEditText postalAddressEditText;
    TextInputEditText cityEditText;
    TextInputEditText countryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_employeur_etape01);

        companyNameEditText = findViewById(R.id.edit_text_company_name);
        nationalUniqueNumberEditText = findViewById(R.id.edit_text_national_unique_number);
        phoneNumberEditText = findViewById(R.id.edit_text_phone_number);
        emailEditText = findViewById(R.id.edit_text_email);
        postalAddressEditText = findViewById(R.id.edit_text_postal_address);
        cityEditText = findViewById(R.id.edit_text_city);
        countryEditText = findViewById(R.id.edit_text_country);
        next = findViewById(R.id.button_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (areFieldsValid() && isProfessionalEmail(emailEditText.getText().toString().trim())) {
                    Employer employer = new Employer();
                    employer.companyName = companyNameEditText.getText().toString().trim();
                    employer.nationalUniqueNumber = nationalUniqueNumberEditText.getText().toString().trim();
                    employer.phoneNumber = phoneNumberEditText.getText().toString().trim();
                    employer.email = emailEditText.getText().toString().trim();
                    employer.postalAddress = postalAddressEditText.getText().toString().trim();
                    employer.city = cityEditText.getText().toString().trim();
                    employer.country = countryEditText.getText().toString().trim();

                    // Utiliser AsyncTask pour insérer l'employeur dans la base de données
                    new InsertEmployerTask().execute(employer);
                } else {
                    Toast.makeText(inscription_employeur_etape01.this, "Veuillez remplir tous les champs avec des informations valides.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean areFieldsValid() {
        return !companyNameEditText.getText().toString().trim().isEmpty() &&
                !nationalUniqueNumberEditText.getText().toString().trim().isEmpty() &&
                !phoneNumberEditText.getText().toString().trim().isEmpty() &&
                !emailEditText.getText().toString().trim().isEmpty() &&
                !postalAddressEditText.getText().toString().trim().isEmpty() &&
                !cityEditText.getText().toString().trim().isEmpty() &&
                !countryEditText.getText().toString().trim().isEmpty();
    }

    private boolean isProfessionalEmail(String email) {
        String professionalEmailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|org|net|gov|edu|co|biz)$";
        return Pattern.matches(professionalEmailPattern, email);
    }

    // AsyncTask pour insérer l'employeur dans la base de données
    private class InsertEmployerTask extends AsyncTask<Employer, Void, Void> {
        @Override
        protected Void doInBackground(Employer... employers) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            db.employerDao().insert(employers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(inscription_employeur_etape01.this, "Étape 01 : Employeur enregistré avec succès!", Toast.LENGTH_SHORT).show();
            Log.d("InscriptionEmployeur01", "Employeur enregistré avec succès!");

            // Démarrer l'activité suivante en envoyant l'adresse e-mail
            Intent intent = new Intent(inscription_employeur_etape01.this, inscription_employeur_etape02.class);
            intent.putExtra("email", emailEditText.getText().toString().trim());
            startActivity(intent);
        }
    }
}
