package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import BD.AppDatabase;
import DAO.EmployerDao;
import entity.Employer;

public class LoginEmployeur extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword;
    CheckBox rememberMe;
    AppDatabase db;
    EmployerDao employerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employeur);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.rememberMe);

        db = AppDatabase.getInstance(this);
        employerDao = db.employerDao();

        ClickLogin();
    }

    // This is method for doing operation of check login
    private void ClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty()) {
                    showSnackbar(view, "Please fill out these fields");
                    txtInLayoutUsername.setError("Username should not be empty");
                } else {
                    txtInLayoutUsername.setError(null);
                }
                if (password.getText().toString().trim().isEmpty()) {
                    showSnackbar(view, "Please fill out these fields");
                    txtInLayoutPassword.setError("Password should not be empty");
                } else {
                    txtInLayoutPassword.setError(null);
                }

                if (!username.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    // Vérifier les informations d'identification
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String emailInput = username.getText().toString().trim();
                            String passwordInput = password.getText().toString().trim();

                            Log.d("Login", "Email: " + emailInput);
                            Log.d("Login", "Password: " + passwordInput);

                            Employer employer = employerDao.findByEmailAndPassword(emailInput, passwordInput);

                            if (employer != null) {
                                Log.d("Login", "Employer found: " + employer.getEmail());
                            } else {
                                Log.d("Login", "Employer not found");
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (employer != null) {
                                        // Informations d'identification correctes, démarrer l'activité suivante
                                        Intent intent = new Intent(LoginEmployeur.this, ActivityNavigationEmployeur.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Informations d'identification incorrectes, afficher un message d'erreur
                                        showSnackbar(view, "Invalid username or password");
                                    }
                                }
                            });
                        }
                    }).start();
                }

                if (rememberMe.isChecked()) {
                    // Ici, vous pouvez écrire le code si la case est cochée
                }
            }
        });
    }

    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
        snackbar.show();
    }
}
