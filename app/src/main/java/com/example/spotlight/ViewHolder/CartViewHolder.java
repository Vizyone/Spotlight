package com.example.spotlight.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotlight.Interface.ItemClickListener;
import com.example.spotlight.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtEventName, txtEventPrice, txtNumberOfTickets;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtEventName = itemView.findViewById(R.id.cart_event_name);
        txtEventPrice = itemView.findViewById(R.id.cart_event_price);
        txtNumberOfTickets = itemView.findViewById(R.id.cart_number_of_tickets);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
