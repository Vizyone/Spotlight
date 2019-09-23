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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EventDetailsActivity extends AppCompatActivity
{

    private FloatingActionButton addToCardBtn;
    private ImageView eventImage;
    private ElegantNumberButton numberButton;
    private TextView eventPrice, eventDescription, eventName;
    private String eventsId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventsId = getIntent().getStringExtra("pid");

        addToCardBtn = findViewById(R.id.add_product_to_credit_card_btn);
        eventImage = findViewById(R.id.event_image_details);
        numberButton = findViewById(R.id.number_btn);
        eventPrice = findViewById(R.id.event_price_details);
        eventDescription = findViewById(R.id.event_description_details);
        eventName = findViewById(R.id.event_name_details);

        getEventDetails(eventsId);

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addingToCartList();
            }
        });
    }

    private void addingToCartList()
    {
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",eventsId);
        cartMap.put("ename",eventName.getText().toString());
        cartMap.put("price",eventPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Events").child(eventsId)
                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Events").child(eventsId)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(EventDetailsActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EventDetailsActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
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
                    eventPrice.setText(events.getPrice());

                    Picasso.get().load(events.getImage()).into(eventImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
