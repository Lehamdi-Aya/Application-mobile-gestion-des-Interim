package com.example.interim;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import BD.AppDatabase;
import entity.CandidatureWithUserName;

public class SecondFragment extends Fragment {

    private RecyclerView candidaturesRecyclerView;
    private CandidaturesAdapter candidaturesAdapter;
    private AppDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        candidaturesRecyclerView = view.findViewById(R.id.candidatures);
        candidaturesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int offerId = getOfferId(); // Récupérez l'ID de l'offre
        Log.d("SecondFragment", "Offer ID: " + offerId); // Ajoutez ce log pour vérifier l'ID de l'offre
        loadCandidatures(offerId);

        return view;
    }

    private void loadCandidatures(int offerId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CandidatureWithUserName> candidatures = db.candidatureDao().getCandidaturesWithUserNameByOfferId(offerId);
                Log.d("SecondFragment", "Candidatures: " + candidatures); // Ajoutez ce log pour vérifier les candidatures récupérées

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (candidatures != null && !candidatures.isEmpty()) {
                            candidaturesAdapter = new CandidaturesAdapter(candidatures, getContext());
                            candidaturesRecyclerView.setAdapter(candidaturesAdapter);
                        } else {
                            Toast.makeText(getContext(), "Aucune candidature trouvée", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    private int getOfferId() {
        // Récupérez l'ID de l'offre à partir des préférences partagées ou d'une autre source
        SharedPreferences prefs = getActivity().getSharedPreferences("offer_prefs", Context.MODE_PRIVATE);
        int offerId = prefs.getInt("offerId", -1);
        Log.d("SecondFragment", "Offer ID: " + offerId); // Ajoutez ce log pour vérifier l'ID de l'offre
        return offerId; // Retourne -1 si l'ID de l'offre n'est pas trouvé
    }
}
