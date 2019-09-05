package com.example.spotlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView church, dance, guitar;
    private ImageView micro,art, speech;
    private ImageView knit, trophy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        church = findViewById(R.id.church);
        dance = findViewById(R.id.dancing);
        guitar =  findViewById(R.id.guitar);
        micro = findViewById(R.id.microphone);
        art = findViewById(R.id.art);
        speech = findViewById(R.id.speech);
        knit = findViewById(R.id.knit);
        trophy = findViewById(R.id.trophy);

        church.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","church");
                startActivity(intent);
            }
        });

        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","bal");
                startActivity(intent);
            }
        });

        guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","acoustic");
                startActivity(intent);
            }
        });

        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","concert");
                startActivity(intent);
            }
        });

        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","art");
                startActivity(intent);
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","seminar");
                startActivity(intent);
            }
        });

        knit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","artisanal");
                startActivity(intent);
            }
        });

        trophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminAddNewEventActivity.class);
                intent.putExtra("category","sports");
                startActivity(intent);
            }
        });
    }
}
