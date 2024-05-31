package com.example.interim;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import BD.AppDatabase;
import entity.Offer;

public class Acceuil_Utilisateur_chercheOffres extends Fragment {
    private RecyclerView recyclerView;
    private OffreAdapter adapter;
    private List<Offer> offreList;

    public Acceuil_Utilisateur_chercheOffres() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceuil__utilisateur_cherche_offres, container, false);

        recyclerView = view.findViewById(R.id.offersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        offreList = new ArrayList<>();
        adapter = new OffreAdapter(offreList, getContext());
        recyclerView.setAdapter(adapter);

        // Récupérer les offres depuis la base de données
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(getContext());
                List<Offer> offersFromDB = db.offerDao().getAllOffers();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        offreList.clear();
                        offreList.addAll(offersFromDB);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        // Gérer le clic sur le bouton de déconnexion
        TextView logoutTxt = view.findViewById(R.id.logoutTxt);
        logoutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancer l'Activity de connexion
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                // Optionnel : Finir l'activité actuelle si nécessaire
                getActivity().finish();
            }
        });

        return view;
    }
}
