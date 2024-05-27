package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import BD.AppDatabase;
import entity.User;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextInputLayout txtInLayoutUsername, txtInLayoutPassword;
    CheckBox rememberMe;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        txtInLayoutUsername = findViewById(R.id.txtInLayoutUsername);
        txtInLayoutPassword = findViewById(R.id.txtInLayoutPassword);
        rememberMe = findViewById(R.id.rememberMe);

        db = AppDatabase.getInstance(this); // Initialiser la base de données

        ClickLogin();
    }

    //This is method for doing operation of check login
    private void ClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty()) {
                    showSnackbar(view, "Please fill out these fields", R.color.red);
                    txtInLayoutUsername.setError("Username should not be empty");
                } else if (password.getText().toString().trim().isEmpty()) {
                    showSnackbar(view, "Please fill out these fields", R.color.red);
                    txtInLayoutPassword.setError("Password should not be empty");
                } else {
                    // Vérifier les informations d'identification de l'utilisateur
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = db.userDao().login(username.getText().toString(), password.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (user != null) {
                                        // Connexion réussie
                                        Intent intent = new Intent(Login.this, ActivityNavigation.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Échec de la connexion
                                        showSnackbar(view, "Invalid email or password", R.color.red);
                                    }
                                }
                            });
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
