package com.example.interim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import entity.Offer;
import entity.Employer;
import BD.AppDatabase;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.OfferViewHolder> {
    private List<Offer> offreList;
    private Context context;

    public OffreAdapter(List<Offer> offreList, Context context) {
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
        Offer offer = offreList.get(position);
        holder.bind(offer);
    }

    @Override
    public int getItemCount() {
        return offreList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        private TextView titreTextView;
        private TextView localisationTextView;
        private TextView entrepriseTextView;
        private TextView descriptionTextView;
        private Button voirDetailsButton;
        private ImageView imageViewFavorite;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            titreTextView = itemView.findViewById(R.id.titreTextView);
            localisationTextView = itemView.findViewById(R.id.localisationTextView);
            entrepriseTextView = itemView.findViewById(R.id.entrepriseTextView);
            voirDetailsButton = itemView.findViewById(R.id.buttonDetails);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
        }

        public void bind(Offer offer) {
            titreTextView.setText(offer.getTitle());
            localisationTextView.setText(offer.getCity() + ", " + offer.getPostalCode());
            entrepriseTextView.setText(offer.getProfession());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(context);
                    Employer employer = db.employerDao().getEmployerById(offer.getEmployerId());

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            entrepriseTextView.setText(employer != null ? employer.getCompanyName() : "AEDL");
                        }
                    });
                }
            }).start();

            // Définir l'état initial de l'ImageView pour les favoris
            if (offer.isFavorite()) {
                imageViewFavorite.setImageResource(R.drawable.starr);
            } else {
                imageViewFavorite.setImageResource(R.drawable.favorite);
            }

            // Gestion du clic sur l'ImageView pour changer l'état des favoris
            imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (offer.isFavorite()) {
                        imageViewFavorite.setImageResource(R.drawable.favorite);
                        offer.setFavorite(false);
                    } else {
                        imageViewFavorite.setImageResource(R.drawable.starr);
                        offer.setFavorite(true);
                    }
                }
            });

            // Gestion du clic sur le bouton pour voir les détails
            voirDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveOfferId(offer.getId());

                    Intent intent = new Intent(context, OfferDetailsDialog.class);
                    intent.putExtra("offreId", offer.getId()); // Ajouter l'ID de l'offre ici
                    intent.putExtra("title", offer.getTitle());
                    intent.putExtra("localisation", offer.getCity() + ", " + offer.getPostalCode());
                    intent.putExtra("description", offer.getDescription());
                    intent.putExtra("minSalary", offer.getMinSalary());
                    intent.putExtra("maxSalary", offer.getMaxSalary());
                    intent.putExtra("startDate", offer.getStartDate());
                    intent.putExtra("employerId", offer.getEmployerId());
                    context.startActivity(intent);
                }
            });
        }

        private void saveOfferId(int offerId) {
            SharedPreferences prefs = context.getSharedPreferences("offer_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("offerId", offerId);
            editor.apply();
        }
    }
}
