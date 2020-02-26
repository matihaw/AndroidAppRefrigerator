package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    protected Context context = this; ///context
    char response;
    public void startActivity(Class<? extends Activity> T){
        Intent intent = new Intent(this,T);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response = ' ';
                EditText et1 = findViewById(R.id.editText4);
                final String username = et1.getText().toString();
                EditText et2 = findViewById(R.id.editText5);
                final String password = et2.getText().toString();
                if(username.equals(null) || password.equals(null)){
                    Toast.makeText(context, "Give all data", Toast.LENGTH_LONG).show();
                }else {
                    Thread thread = new Thread(new Runnable() {
                        /*
                        Insert data to api

                         */
                        @Override
                        public void run() {
                            try  {
                                URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Login");
                                String urlParameters ="username="+username+"&password="+password;
                                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setDoOutput(true);
                                con.setRequestMethod("POST");
                                con.setRequestProperty("User-Agent", "Java client");
                                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                                wr.write(postData);
                                InputStream stream = con.getInputStream();
                                int i;
                                while ((i = stream.read()) != -1) {
                                    response = (char) i;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                        if(response==' '){
                            Toast.makeText(context,"Podales bledne dane logowania" , Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"Logowanie poprawne", Toast.LENGTH_LONG).show();
                            startActivity(MainActivity.class);
                        }
                    }catch (Exception e){
                           Toast.makeText(context,"Error: " + e, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
