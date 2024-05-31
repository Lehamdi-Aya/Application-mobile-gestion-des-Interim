package com.example.interim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import entity.Offer;

public class OfferAdapterAnonyme extends RecyclerView.Adapter<OfferAdapterAnonyme.OfferViewHolder> {

    private List<Offer> offers;

    public OfferAdapterAnonyme(List<Offer> offers) {
        this.offers = offers;
    }

    public void updateOffers(List<Offer> newOffers) {
        offers.clear();
        offers.addAll(newOffers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_anonyme, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.bind(offer);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder {

        TextView offerTitle;
        TextView offerDescription;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            offerTitle = itemView.findViewById(R.id.offer_title);
            offerDescription = itemView.findViewById(R.id.offer_description);
        }

        public void bind(Offer offer) {
            offerTitle.setText(offer.getTitle());
            offerDescription.setText(offer.getDescription());
        }
    }
}
