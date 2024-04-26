
package com.example.interim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private List<Offer> offers;
    private Context context;

    public OfferAdapter(List<Offer> offers, Context context) {
        this.offers = offers;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer currentOffer = offers.get(position);
        holder.titleTextView.setText(currentOffer.getTitle());
        holder.companyTextView.setText(currentOffer.getCompany());
        holder.cityTextView.setText(currentOffer.getCity());
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView companyTextView;
        TextView cityTextView;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_offer_title);
            companyTextView = itemView.findViewById(R.id.tv_company_name);
            cityTextView = itemView.findViewById(R.id.tv_city_name);
        }
    }
}
