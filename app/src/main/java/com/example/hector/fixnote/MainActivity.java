package com.example.hector.fixnote;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {


    String correo = "fixnote0@gmail.com";
    String contraseña = "Boulevard0";

    Session session;

    EditText nombre,apellido1,apellido2,
             email,celular,modelo,id_equipo,
             costo,garantia,ob;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nombre = (EditText)findViewById(R.id.nombre);
        apellido1 = (EditText)findViewById(R.id.apellido1);
        apellido2 = (EditText)findViewById(R.id.apellido2);
        email = (EditText)findViewById(R.id.email);
        celular = (EditText)findViewById(R.id.celular);
        modelo = (EditText)findViewById(R.id.modelo);
        id_equipo = (EditText)findViewById(R.id.id_equipo);
        costo = (EditText)findViewById(R.id.costo);
        garantia = (EditText)findViewById(R.id.garantia);
        ob = (EditText)findViewById(R.id.observaciones);
        enviar =(Button)findViewById(R.id.enviar);




        enviar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties p = new Properties();
                p.put("mail.smtp.host", "smtp.googlemail.com");
                p.put("mail.smtp.socketFactory.port", "465");
                p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                p.put("mail.smtp.auth", "true");
                p.put("mail.smtp.port", "465");

                String contenido =
                        "<h3>Cliente: </h3>" +
                        "<h4>" +
                        "Nombre: " +
                        nombre.getText().toString() + " " +
                        apellido1.getText().toString() + " " +
                        apellido2.getText().toString() + "<br>" +
                        "Celular: " +
                        celular.getText().toString() + "<br>" +
                        "</h4>" +
                        "<h3>Equipo: </h3>" +
                        "<h4>" +
                        "Modelo: " +
                        modelo.getText().toString() + "<br>" +
                        "IMEI/#Serie: " +
                        id_equipo.getText().toString() + "<br>" +
                        "</h4>" +
                        "<h3>Información de reparación: </h3>"+
                        "<h4>" +
                        "Costo de reparación : $"+
                        costo.getText().toString() +".00 MXN"+ "<br>"+
                        "Garantía: "+
                        garantia.getText().toString() + "<br>" +
                        "Observaciones: "+
                        ob.getText().toString() + "<br><br>" +
                        "Gracias por confiar en centurión, nuestros técnicos pondrán todo el empeño para reparar tu equipo." +
                        "</h4>";

                try {

                    session = Session.getDefaultInstance(p, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo, contraseña);
                        }
                    });

                    String mail = email.getText().toString();

                    if(!validateEmail(email.getText().toString())) {
                        email.setError("Invalid Email");
                        email.requestFocus();

                    }else{

                        if (session != null) {
                            InternetAddress[] internetAddresses = {
                                    new InternetAddress("hecthorgarcia0@gmail.com"),//ccarloscastillo96@gmail.com
                                    new InternetAddress(mail)};

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(correo));
                            message.setSubject("Nota Centurion");
                            message.setRecipients(Message.RecipientType.TO, internetAddresses);
                            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(et_correo.getText().toString() ));

                            message.setContent(contenido, "text/html; charset=utf-8");

                            Transport.send(message);

                            Toast.makeText(MainActivity.this, "Nota enviada", Toast.LENGTH_LONG).show();

                            nombre.setText("");
                            apellido1.setText("");
                            apellido2.setText("");
                            email.setText("");
                            celular.setText("");
                            modelo.setText("");
                            id_equipo.setText("");
                            costo.setText("");
                            garantia.setText("");
                            ob.setText("");

                        }
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.ajuste:
                    Intent ajustes = new Intent(this,Ajustes_activity.class);
                    startActivity(ajustes);
                    return true;
                case R.id.contacto:
                    Intent contacto = new Intent(this,Contacto_activity.class);
                    startActivity(contacto);
                    return true;
                default:
                    return false;
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