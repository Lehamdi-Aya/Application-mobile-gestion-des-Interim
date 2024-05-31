package com.example.interim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import entity.CandidatureWithUserName;

public class CandidaturesAdapter extends RecyclerView.Adapter<CandidaturesAdapter.CandidaturesViewHolder> {

    private List<CandidatureWithUserName> candidatureList;
    private Context context;

    public CandidaturesAdapter(List<CandidatureWithUserName> candidatureList, Context context) {
        this.candidatureList = candidatureList;
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
        CandidatureWithUserName candidature = candidatureList.get(position);
        holder.bind(candidature);
    }

    @Override
    public int getItemCount() {
        return candidatureList.size();
    }

    public class CandidaturesViewHolder extends RecyclerView.ViewHolder {
        private TextView titreTextView;

        public CandidaturesViewHolder(@NonNull View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.nomPrenom);
        }

        public void bind(CandidatureWithUserName candidature) {
            String fullName = candidature.userName + " " + candidature.userPrenom;
            titreTextView.setText(fullName);
            titreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CandidatDetails.class);
                    intent.putExtra("Nom", candidature.userName);
                    intent.putExtra("Prenom", candidature.userPrenom);
                    intent.putExtra("CandidatureId", candidature.getId()); // Ajoutez cet extra
                    context.startActivity(intent);
                }
            });
        }
    }
}
