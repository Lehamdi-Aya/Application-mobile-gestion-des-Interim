package com.example.interim;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import entity.User;
import DAO.UserDao;
import BD.AppDatabase;

public class NewUtilisateur extends AppCompatActivity {

    EditText nomEditText, prenomEditText, datDeNaissanceEdit, editTextPhone, paysEdtiText, emailEditText, mdp, cnfmdp;
    Button buttonEnvoyer;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nomEditText = findViewById(R.id.nomEditText);
        prenomEditText = findViewById(R.id.prenomEditText);
        datDeNaissanceEdit = findViewById(R.id.datDeNaissanceEdit);
        editTextPhone = findViewById(R.id.editTextPhone);
        paysEdtiText = findViewById(R.id.paysEdtiText);
        emailEditText = findViewById(R.id.mail); // Ajout du champ email
        mdp = findViewById(R.id.mdp);
        cnfmdp = findViewById(R.id.cnfmdp);
        buttonEnvoyer = findViewById(R.id.buttonEnvoyer);

        db = AppDatabase.getInstance(this); // Initialiser la base de données

        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomEditText.getText().toString().trim().isEmpty() ||
                        prenomEditText.getText().toString().trim().isEmpty() ||
                        datDeNaissanceEdit.getText().toString().trim().isEmpty() ||
                        editTextPhone.getText().toString().trim().isEmpty() ||
                        paysEdtiText.getText().toString().trim().isEmpty() ||
                        emailEditText.getText().toString().trim().isEmpty() ||  // Validation du champ email
                        mdp.getText().toString().trim().isEmpty() ||
                        cnfmdp.getText().toString().trim().isEmpty()) {

                    showSnackbar(view, "Veuillez remplir tous les champs", R.color.red);
                } else if (!mdp.getText().toString().equals(cnfmdp.getText().toString())) {
                    showSnackbar(view, "Les mots de passe ne correspondent pas", R.color.red);
                } else {
                    // Insérer l'utilisateur dans la base de données
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("NewUtilisateur", "Attempting to insert user");
                            User user = new User(
                                    nomEditText.getText().toString(),
                                    prenomEditText.getText().toString(),
                                    datDeNaissanceEdit.getText().toString(),
                                    editTextPhone.getText().toString(),
                                    paysEdtiText.getText().toString(),
                                    emailEditText.getText().toString(),  // Passage de l'email
                                    mdp.getText().toString()
                            );
                            try {
                                db.userDao().insert(user);
                                Log.d("NewUtilisateur", "User inserted successfully");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSnackbar(view, "Utilisateur enregistré avec succès", R.color.green_800);
                                        finish(); // Fermer l'activité après inscription réussie
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("NewUtilisateur", "Error inserting user", e);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSnackbar(view, "Erreur lors de l'enregistrement de l'utilisateur", R.color.red);
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void showSnackbar(View view, String message, int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(color));
        snackbar.show();
    }
}
