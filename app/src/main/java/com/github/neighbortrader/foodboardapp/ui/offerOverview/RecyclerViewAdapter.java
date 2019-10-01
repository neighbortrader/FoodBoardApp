package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import lombok.Setter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.OfferViewHolder> {
    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        TextView groceryAndCreationDate;
        TextView description;
        TextView price;

        OfferViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            groceryAndCreationDate = itemView.findViewById(R.id.txtGroceryCategoryAndCreationDate);
            description = itemView.findViewById(R.id.txtDescription);
            price = itemView.findViewById(R.id.txtPrice);
        }
    }

    @Setter
    private ArrayList<Offer> offerList;

    public void clearOfferList(){
        offerList.clear();
    }

    public RecyclerViewAdapter(ArrayList<Offer> offerList) {
        this.offerList = offerList;
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offercardview, viewGroup, false);
        OfferViewHolder offerViewHolder = new OfferViewHolder(v);
        return offerViewHolder;
    }

    @Override
    public void onBindViewHolder(OfferViewHolder offerViewHolder, int i) {
        LocalDateTime creationDate = offerList.get(i).getCreationDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        String groceryCategoryAndCreationDateText = String.format("%s (%s)",
                offerList.get(i).getGroceryCategory().getGroceryName(),
                creationDate.format(dateTimeFormatter));

        offerViewHolder.groceryAndCreationDate.setText(groceryCategoryAndCreationDateText);
        offerViewHolder.description.setText(offerList.get(i).getDescription());
        offerViewHolder.price.setText(offerList.get(i).getPrice().getFormattedPrice());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
