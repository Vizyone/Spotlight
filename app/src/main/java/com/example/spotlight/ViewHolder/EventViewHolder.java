package com.example.spotlight.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotlight.Interface.ItemClickListener;
import com.example.spotlight.R;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtEventName, txtEventDescription, txtEventPrice;
    public ImageView ivEventImage;
    public ItemClickListener itemClickListener;

    public EventViewHolder(View itemView)
    {
        super(itemView);

        ivEventImage = itemView.findViewById(R.id.event_feed_image);
        txtEventName = itemView.findViewById(R.id.event_feed_name);
        txtEventDescription = itemView.findViewById(R.id.event_feed_description);
        txtEventPrice = itemView.findViewById(R.id.event_feed_price);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        itemClickListener = listener;
    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }


}
