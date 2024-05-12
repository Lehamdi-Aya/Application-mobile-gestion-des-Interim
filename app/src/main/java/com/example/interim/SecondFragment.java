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

public class SecondFragment extends Fragment {

    private RecyclerView recyclerView;
    private CandidaturesAdapter adapter;
    private List<Candidature> candidatureList;

    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Récupérer la référence de la RecyclerView depuis le layout
        recyclerView = view.findViewById(R.id.candidatures);

        // Initialiser la liste des candidatures et l'adaptateur
        candidatureList = new ArrayList<>();
        adapter = new CandidaturesAdapter(candidatureList, getContext());

        // Configurer la RecyclerView avec un LayoutManager et l'adaptateur
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Ajouter des candidatures à la liste
        candidatureList.add(new Candidature("Aya lehamdi","29/04/1998"));
        candidatureList.add(new Candidature("Lina lehamdi","04/03/2000"));
        candidatureList.add(new Candidature("Louay lehamdi" ,"15/01/1996"));

        // Notifier à l'adaptateur que les données ont changé
        adapter.notifyDataSetChanged();

        return view;
    }
}
