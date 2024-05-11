package com.example.interim;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Offres_Utilisateur#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Offres_Utilisateur extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Offres_Utilisateur() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Offres_Utilisateur.
     */
    // TODO: Rename and change types and number of parameters
    public static Offres_Utilisateur newInstance(String param1, String param2) {
        Offres_Utilisateur fragment = new Offres_Utilisateur();
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
        View view = inflater.inflate(R.layout.fragment_offres__utilisateur, container, false);
        MaterialCardView CandEnvoyeesCardView = view.findViewById(R.id.envoyés);
MaterialCardView CandFavoris=view.findViewById(R.id.material_card_0);
        MaterialCardView entretienCardView = view.findViewById(R.id.entretien);
        entretienCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer une nouvelle activité ici
                Intent intent = new Intent(getActivity(), Calendrier_Entretien.class);
                startActivity(intent);
            }
        });
        CandEnvoyeesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer une nouvelle activité ici
                Intent intent = new Intent(getActivity(), Cand_Envoyees.class);
                startActivity(intent);
            }
        });
        CandFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer une nouvelle activité ici
                Intent intent = new Intent(getActivity(), CandidaturesFavoris.class);
                startActivity(intent);
            }
        });




        return view;
    }


}