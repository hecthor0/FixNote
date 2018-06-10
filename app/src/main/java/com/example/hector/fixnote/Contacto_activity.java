package com.example.hector.fixnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Contacto_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
