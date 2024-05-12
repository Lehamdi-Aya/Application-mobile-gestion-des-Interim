package com.example.interim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CandidaturesAdapter extends RecyclerView.Adapter<CandidaturesAdapter.CandidaturesViewHolder> {

    private List<Candidature> CandidatureList;
    private Context context;

    public CandidaturesAdapter(List<Candidature> CandidatureList, Context context) {
        this.CandidatureList = CandidatureList;
        this.context = context;
    }

    @NonNull
    @Override
    public CandidaturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidatures, parent, false);
        return new CandidaturesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidaturesViewHolder holder, int position) {
        Candidature candidature = CandidatureList.get(position);
        holder.bind(candidature);
    }

    @Override
    public int getItemCount() {
        return CandidatureList.size();
    }

    public class CandidaturesViewHolder extends RecyclerView.ViewHolder {
        private TextView titreTextView;
private TextView datedenaissance;


        public CandidaturesViewHolder(@NonNull View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.nomPrenom);
            datedenaissance=itemView.findViewById(R.id.datedenaissance);

        }

        public void bind(Candidature candidature) {
            titreTextView.setText(candidature.getNom());
            datedenaissance.setText(candidature.getDatedenaissance());
            titreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CandidatDetails.class);
                    intent.putExtra("Nom", candidature.getNom());
                    intent.putExtra("Datedenaissance", candidature.getDatedenaissance());
                    context.startActivity(intent);
                }
            });
        }
    }
}
