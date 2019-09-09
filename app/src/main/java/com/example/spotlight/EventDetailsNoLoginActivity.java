package com.example.spotlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.spotlight.Model.Events;
import com.example.spotlight.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventDetailsNoLoginActivity extends AppCompatActivity
{

    private FloatingActionButton addToCardBtn;
    private ImageView eventImage;
    private ElegantNumberButton numberButton;
    private TextView eventPrice, eventDescription, eventName;
    private String eventsId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_no_login);

        eventsId = getIntent().getStringExtra("pid");

        addToCardBtn = findViewById(R.id.add_product);
        eventImage = findViewById(R.id.image_details);
        numberButton = findViewById(R.id.switch_number_btn);
        eventPrice = findViewById(R.id.price_details);
        eventDescription = findViewById(R.id.description_details);
        eventName = findViewById(R.id.name_details);

        getEventDetails(eventsId);

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Prevalent.currentOnlineUser == null)
                {
                    Toast.makeText(EventDetailsNoLoginActivity.this, "You must create an account first", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EventDetailsNoLoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(EventDetailsNoLoginActivity.this, "You already have an account connected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EventDetailsNoLoginActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    private void getEventDetails(String eventsId)
    {
        DatabaseReference eventsReference = FirebaseDatabase.getInstance().getReference().child("Events");

        eventsReference.child(eventsId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Events events = dataSnapshot.getValue(Events.class);

                    eventName.setText(events.getEname());
                    eventDescription.setText(events.getDescription());
                    eventPrice.setText(events.getPrice() + " $");

                    Picasso.get().load(events.getImage()).into(eventImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
