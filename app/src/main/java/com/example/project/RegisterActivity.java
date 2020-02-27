package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {
    private Context context = this;
    /*
        Start new Activity
     */
    public void startNewActivity(Class<? extends Activity> T){
        Intent intent = new Intent(context,T);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*
           Start register process
         */
        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = findViewById(R.id.editText44);
                final String username = et1.getText().toString();
                EditText et2 = findViewById(R.id.editText55);
                final String password = et2.getText().toString();
                /*
                    Check if important data is given
                 */
                if(username.equals("") || password.equals("")){
                    Toast.makeText(context,"Give all data",Toast.LENGTH_LONG).show();
                }else{
                    /*
                        Insert data via api
                     */
                    Thread registerThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Register");
                                String urlParameters = "username=" + username + "&password=" + password;
                                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setDoOutput(true);
                                con.setRequestMethod("POST");
                                con.setRequestProperty("User-Agent", "Java client");
                                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                                wr.write(postData);
                                new InputStreamReader(con.getInputStream());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    registerThread.start();
                    try {
                        registerThread.join();
                        /*
                            Start login Acitivity
                         */
                        startNewActivity(LoginActivity.class);
                    }catch (Exception e){
                        Toast.makeText(context,"Error: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
