package com.example.spotlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotlight.Model.Events;
import com.example.spotlight.Prevalent.Prevalent;
import com.example.spotlight.ViewHolder.EventViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeNoLoginActivity extends AppCompatActivity
{

    private DatabaseReference EventsRef;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdmin;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_no_login);

        EventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        fabAdmin = findViewById(R.id.button_admin);
        recyclerView = findViewById(R.id.recycler_no_login);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        fabAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(Prevalent.currentOnlineUser != null)
        {
            Intent loginIntent = new Intent(HomeNoLoginActivity.this,HomeActivity.class);
            startActivity(loginIntent);
        }

        FirebaseRecyclerOptions<Events> options =
                new FirebaseRecyclerOptions.Builder<Events>()
                        .setQuery(EventsRef,Events.class)
                        .build();

        FirebaseRecyclerAdapter<Events, EventViewHolder> adapter =
                new FirebaseRecyclerAdapter<Events, EventViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i, @NonNull final Events events)
                    {
                        eventViewHolder.txtEventName.setText(events.getEname());
                        eventViewHolder.txtEventDescription.setText(events.getDescription());
                        eventViewHolder.txtEventPrice.setText("Price: " + events.getPrice() + " $");
                        Picasso.get().load(events.getImage()).into(eventViewHolder.ivEventImage);

                        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent intent = new Intent(HomeNoLoginActivity.this,EventDetailsNoLoginActivity.class);
                                intent.putExtra("pid",events.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_layout,parent,false);
                        EventViewHolder holder = new EventViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
