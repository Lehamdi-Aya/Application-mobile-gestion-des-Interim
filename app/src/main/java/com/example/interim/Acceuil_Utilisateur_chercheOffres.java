package com.example.interim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Acceuil_Utilisateur_chercheOffres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Acceuil_Utilisateur_chercheOffres extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private OffreAdapter adapter;
    private List<Offre> offreList;
    public Acceuil_Utilisateur_chercheOffres() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Acceuil_Utilisateur_chercheOffres.
     */
    // TODO: Rename and change types and number of parameters
    public static Acceuil_Utilisateur_chercheOffres newInstance(String param1, String param2) {
        Acceuil_Utilisateur_chercheOffres fragment = new Acceuil_Utilisateur_chercheOffres();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceuil__utilisateur_cherche_offres, container, false);

        // 1. Récupérer la référence de la RecyclerView depuis le layout
        recyclerView = view.findViewById(R.id.offersRecyclerView);

        // 2. Initialiser la liste des offres et l'adaptateur
        offreList = new ArrayList<>();
        adapter = new OffreAdapter(offreList, getContext());


        // 3. Configurer la RecyclerView avec un LayoutManager et l'adaptateur
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // 4. Ajouter des offres à la liste
        offreList.add(new Offre("Data Analyst Alternance / stage", "Capgimini", "Montpellier,34090"));
        offreList.add(new Offre("Dévellopeur web CDI/CDD", "Sopra Steria", "Paris,75000"));
        offreList.add(new Offre("Concepteur logiciel Stage", "IBM", "Nice ,06000"));


        // 5. Notifier à l'adaptateur que les données ont changé
        adapter.notifyDataSetChanged();

        return view;
    }

}