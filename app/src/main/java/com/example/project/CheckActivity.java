package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {
    private Context context = this;
    EditText et;
    public void startActivity(Class<? extends Activity> T){
        Intent intent = new Intent(this,T.getClass());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        List<String> barcodes = getIntent().getStringArrayListExtra("barcodes");
        List<String> name = getIntent().getStringArrayListExtra("name");
        List<String> amount = getIntent().getStringArrayListExtra("amount");
        StringBuilder barcodesOutputBuilder = new StringBuilder("Barcode: \n");
        StringBuilder nameOutputBuilder = new StringBuilder("Name: \n");
        StringBuilder amountOutputBuilder = new StringBuilder("Amount: \n");

        for(int i=0;i<barcodes.size();i++) {
            barcodesOutputBuilder.append(barcodes.get(i) + "\n");
            nameOutputBuilder.append(name.get(i) + "\n");
            amountOutputBuilder.append(amount.get(i) +"\n");
        }

        EditText idText = findViewById(R.id.id);
        EditText nameText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);
        idText.setText(barcodesOutputBuilder.toString());
        nameText.setText(nameOutputBuilder.toString());
        amountText.setText(amountOutputBuilder.toString());

        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = findViewById(R.id.idToDelete);
                if(et.getText().toString().equals(null)){
                    Toast.makeText(context, "Give important data", Toast.LENGTH_LONG);
                }else{
                    et = findViewById(R.id.idToDelete);
                    Thread deleteData = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL("https://www.example.com/resource");
                                String urlParameters = "id=" + et.getText().toString();
                                byte[] deleteData = urlParameters.getBytes(StandardCharsets.UTF_8);
                                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                                httpCon.setDoOutput(true);
                                httpCon.setRequestMethod("DELETE");
                                httpCon.setRequestProperty("User-Agent", "Java client");
                                httpCon.setRequestProperty(
                                        "Content-Type", "application/x-www-form-urlencoded");
                                DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
                                wr.write(deleteData);
                                new InputStreamReader(httpCon.getInputStream());
                            }catch (Exception e){
                                Toast.makeText(context,"Error: " + e.toString(), Toast.LENGTH_LONG);
                            }
                        }
                    });
                    deleteData.start();
                    startActivity(MainActivity.class);
                }
            }
        });

    }
}
