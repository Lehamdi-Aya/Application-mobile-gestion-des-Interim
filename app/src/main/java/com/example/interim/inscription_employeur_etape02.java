package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import métier.EmailUtils;

public class inscription_employeur_etape02 extends AppCompatActivity {

    TextInputEditText firstDigitEditText;
    TextInputEditText secondDigitEditText;
    TextInputEditText thirdDigitEditText;
    TextInputEditText fourthDigitEditText;
    Button nextButton;
    Button resendCodeButton;

    private int verificationCode;
    private static final String TAG = "InscriptionEmployeurEtape02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_employeur_etape02);

        firstDigitEditText = findViewById(R.id.edit_text_first_digit);
        secondDigitEditText = findViewById(R.id.edit_text_second_digit);
        thirdDigitEditText = findViewById(R.id.edit_text_third_digit);
        fourthDigitEditText = findViewById(R.id.edit_text_fourth_digit);
        nextButton = findViewById(R.id.button_next);
        resendCodeButton = findViewById(R.id.button_resend_code);

        // Recevoir l'email et envoyer le code
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        if (email == null || email.isEmpty()) {
            Log.e(TAG, "Email is null or empty");
            Toast.makeText(this, "Email non valide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Générer et envoyer le code
        verificationCode = EmailUtils.generateVerificationCode();
        EmailUtils.sendEmail(email, "Votre code de vérification", "Saisissez le code de vérification ci-dessous pour vérifier votre identité et confirmer votre inscription : " + verificationCode + ". \n\nCordialement, ,\n\nLEHAMDI Aya,");

        Log.d(TAG, "Generated verification code: " + verificationCode);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = getEnteredCode();

                if (enteredCode == null) {
                    Toast.makeText(inscription_employeur_etape02.this, "Veuillez entrer le code complet", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "Entered verification code: " + enteredCode);

                if (enteredCode.equals(String.valueOf(verificationCode))) {
                    Toast.makeText(inscription_employeur_etape02.this, "Code de vérification correct", Toast.LENGTH_SHORT).show();
                    // Continuer à l'étape suivante
                    Intent nextIntent = new Intent(inscription_employeur_etape02.this, inscription_employeur_etapes03.class);
                    nextIntent.putExtra("email", email);  // Ajoutez l'email à l'intent
                    nextIntent.putExtra("companyName", "Test Company");  // Ajoutez d'autres informations nécessaires
                    startActivity(nextIntent);
                } else {
                    Toast.makeText(inscription_employeur_etape02.this, "Code de vérification incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationCode = EmailUtils.generateVerificationCode();
                EmailUtils.sendEmail(email, "Votre code de vérification", "Votre nouveau code de vérification est : " + verificationCode);
                Log.d(TAG, "Resent verification code: " + verificationCode);
            }
        });
    }

    private String getEnteredCode() {
        String firstDigit = firstDigitEditText.getText().toString();
        String secondDigit = secondDigitEditText.getText().toString();
        String thirdDigit = thirdDigitEditText.getText().toString();
        String fourthDigit = fourthDigitEditText.getText().toString();

        if (firstDigit.isEmpty() || secondDigit.isEmpty() || thirdDigit.isEmpty() || fourthDigit.isEmpty()) {
            return null;
        }

        return firstDigit + secondDigit + thirdDigit + fourthDigit;
    }
}
