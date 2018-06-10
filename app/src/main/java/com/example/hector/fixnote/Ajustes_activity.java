package com.example.hector.fixnote;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Ajustes_activity extends AppCompatActivity {

    EditText email_d;
    EditText asunto;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email_d = (EditText)findViewById(R.id.email_d);
        asunto = (EditText)findViewById(R.id.asunto_e);
        guardar =(Button)findViewById(R.id.guardar);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        email_d.setText(preferences.getString("mail",""));
        asunto.setText(preferences.getString("asunto",""));

    }
    //Botón Guardar
    public void Guardar(View g){
        if(!validateEmail(email_d.getText().toString())) {
            email_d.setError("Invalid Email");
            email_d.requestFocus();
        }else {
            SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor Obj_editor = preferencias.edit();
            Obj_editor.putString("mail", email_d.getText().toString());
            Obj_editor.putString("asunto",asunto.getText().toString());
            Obj_editor.commit();
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
        }

    }

    //Return true if email is valid and false if email is invalid
    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }

}
