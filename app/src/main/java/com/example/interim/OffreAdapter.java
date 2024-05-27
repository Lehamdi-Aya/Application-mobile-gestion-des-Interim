package com.example.interim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.OfferViewHolder> {

    private List<Offre> offreList;
    private Context context;

    public OffreAdapter(List<Offre> offreList, Context context) {
        this.offreList = offreList;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offre offre = offreList.get(position);
        holder.bind(offre);
    }

    @Override
    public int getItemCount() {
        return offreList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        private TextView titreTextView;
        private TextView localisationTextView;
        private TextView descriptionTextView;
        private Button voirDetailsButton;
        private ImageView imageViewFavorite;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.titreTextView);
            localisationTextView = itemView.findViewById(R.id.localisationTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            voirDetailsButton = itemView.findViewById(R.id.buttonDetails);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
        }

        public void bind(Offre offre) {
            titreTextView.setText(offre.getTitre());
            localisationTextView.setText(offre.getLocalisation());
            descriptionTextView.setText(offre.getDescription());

            // Définir l'état initial de l'ImageView pour les favoris
            if (offre.isFavorite()) {
                imageViewFavorite.setImageResource(R.drawable.starr);
            } else {
                imageViewFavorite.setImageResource(R.drawable.favorite);
            }

            // Gestion du clic sur l'ImageView pour changer l'état des favoris
            imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (offre.isFavorite()) {
                        imageViewFavorite.setImageResource(R.drawable.favorite);
                        offre.setFavorite(false);
                    } else {
                        imageViewFavorite.setImageResource(R.drawable.starr);
                        offre.setFavorite(true);
                    }
                }
            });

            // Gestion du clic sur le bouton pour voir les détails
            voirDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OfferDetailsDialog.class);
                    intent.putExtra("titre", offre.getTitre());
                    intent.putExtra("localisation", offre.getLocalisation());
                    intent.putExtra("description", offre.getDescription());
                    intent.putExtra("profileRecherche", offre.getProfileRecherche());
                    context.startActivity(intent);
                }
            });
        }
    }
}
