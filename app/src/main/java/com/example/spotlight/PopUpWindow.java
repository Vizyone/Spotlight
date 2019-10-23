package com.example.spotlight;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopUpWindow extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirmation_popup);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.99),(int) (height*.70));
    }
}
