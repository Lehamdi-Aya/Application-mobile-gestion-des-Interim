package com.example.interim;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import DAO.UserSessionManager;
import entity.User;
import BD.AppDatabase;
import java.util.concurrent.Executors;

public class Profile_Utilisateur extends Fragment {

    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText nationalityEditText;
    private TextInputEditText dateOfBirthEditText;
    private TextInputEditText phoneNumberEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText cityEditText;
    private Button saveButton;

    private User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle arguments if any
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile__utilisateur, container, false);

        firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        lastNameEditText = view.findViewById(R.id.edit_text_field_last_name);
        nationalityEditText = view.findViewById(R.id.edit_text_field_nationality);
        dateOfBirthEditText = view.findViewById(R.id.edit_text_date_of_birth);
        phoneNumberEditText = view.findViewById(R.id.edit_text_phone_number);
        emailEditText = view.findViewById(R.id.edit_text_field_email);
        cityEditText = view.findViewById(R.id.edit_text_field_city);
        saveButton = view.findViewById(R.id.save_button);

        // Charger les informations utilisateur existantes
        loadUserData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Profile_Utilisateur", "Le bouton Enregistrer a été cliqué.");
                saveUserData();
            }
        });


        return view;
    }

    private void loadUserData() {
        UserSessionManager userSessionManager = new UserSessionManager(getContext());
        int userId = userSessionManager.getUserId();

        // Vérifiez si l'ID de l'utilisateur est valide
        if (userId != -1) {
            AppDatabase db = AppDatabase.getInstance(getContext());
            Executors.newSingleThreadExecutor().execute(() -> {
                currentUser = db.userDao().getUserById(userId);
                if (currentUser != null) {
                    // L'utilisateur existe dans la base de données
                    getActivity().runOnUiThread(() -> {
                        // Mettez à jour les champs d'édition avec les données de l'utilisateur
                        firstNameEditText.setText(currentUser.getNom());
                        lastNameEditText.setText(currentUser.getPrenom());
                        nationalityEditText.setText(currentUser.getPays());
                        dateOfBirthEditText.setText(currentUser.getDateDeNaissance());
                        phoneNumberEditText.setText(currentUser.getPhone());
                        emailEditText.setText(currentUser.getEmail());
                        cityEditText.setText(currentUser.getPays());
                    });
                } else {
                    // L'ID de l'utilisateur n'est pas valide ou aucun utilisateur correspondant n'a été trouvé
                    getActivity().runOnUiThread(() -> {
                        // Gérez le cas où l'ID de l'utilisateur n'est pas valide
                        Toast.makeText(getContext(), "ID d'utilisateur non valide", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            // Gérer le cas où l'ID de l'utilisateur n'est pas valide
            Toast.makeText(getContext(), "ID d'utilisateur non valide", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserData() {
        if (currentUser != null) {
            currentUser.setNom(firstNameEditText.getText().toString());
            currentUser.setPrenom(lastNameEditText.getText().toString());
            currentUser.setPays(nationalityEditText.getText().toString());
            currentUser.setDateDeNaissance(dateOfBirthEditText.getText().toString());
            currentUser.setPhone(phoneNumberEditText.getText().toString());
            currentUser.setEmail(emailEditText.getText().toString());
            currentUser.setPassword(cityEditText.getText().toString());

            AppDatabase db = AppDatabase.getInstance(getContext());
            Executors.newSingleThreadExecutor().execute(() -> {
                // Log avant la mise à jour de l'utilisateur
                Log.d("Profile_Utilisateur", "Avant la mise à jour : " + currentUser.toString());

                db.userDao().update(currentUser);

                // Log après la mise à jour de l'utilisateur
                Log.d("Profile_Utilisateur", "Après la mise à jour : " + currentUser.toString());

                // Afficher un toast pour indiquer que la modification est réussie
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Modification réussie", Toast.LENGTH_SHORT).show();
                });
            });
        }else {
            Log.d("Profile_Utilisateur", "Erreur : currentUser est null");
        }
    }
}


