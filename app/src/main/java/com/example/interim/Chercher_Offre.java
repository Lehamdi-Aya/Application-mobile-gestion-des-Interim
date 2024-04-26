package com.example.interim;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Chercher_Offre extends AppCompatActivity {

    private RecyclerView offersRecyclerView;
    private OfferAdapter offerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher_offre);

        // Initialisation du RecyclerView
        offersRecyclerView = findViewById(R.id.offers_recycler_view);
        offersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Générer des offres aléatoires
        List<Offer> offers = generateRandomOffers(10);

        // Créer et définir l'adaptateur pour le RecyclerView
        offerAdapter = new OfferAdapter(offers, this);
        offersRecyclerView.setAdapter(offerAdapter);
    }

    private List<Offer> generateRandomOffers(int count) {
        List<Offer> offers = new ArrayList<>();
        // Générer vos offres aléatoires ici
        return offers;
    }
}
