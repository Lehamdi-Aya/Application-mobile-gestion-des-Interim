package com.example.interim;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FragmentContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);


        // Remplacez le contenu de la mise en page par votre fragment
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new Acceuil_Utilisateur_chercheOffres())
                .commit();
    }
}
