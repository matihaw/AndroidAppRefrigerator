package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected String[] permission = {Manifest.permission.CAMERA, Manifest.permission.INTERNET};
    protected Context context = this; ///context
    protected Activity activity = this;   ///activity
    public String barcode = "";    ///barcode from scanner

    private List<String> barcodes = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> amount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(permission, 1);
        setContentView(R.layout.activity_main);

        Thread urlConnection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Hello");
                    URLConnection ucon = url.openConnection();
                    InputStream stream = ucon.getInputStream();
                    int i;
                    int iterator = 1;
                    StringBuilder sb = new StringBuilder();
                    while ((i = stream.read()) != -1) {
                        if(((char) i) != ',') {
                            System.out.println(iterator);
                            sb.append((char) i);
                        } else {
                            switch (iterator) {
                                case 1:
                                    barcodes.add(sb.toString());
                                    sb.delete(0, sb.length());
                                    iterator++;
                                    break;
                                case 2:
                                    name.add(sb.toString());
                                    sb.delete(0, sb.length());
                                    iterator++;
                                    break;
                                case 3:
                                    amount.add(sb.toString());
                                    sb.delete(0, sb.length());
                                    iterator = 1;
                                    break;
                            }
                        }
                    }
                }catch (Exception e) {
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
        urlConnection.start();
        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   ///onclick button
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) { ///check for permission
                    IntentIntegrator barcodeScanner = new IntentIntegrator(activity); ///set new activity
                    barcodeScanner.setBeepEnabled(true); ///enable sound on scan
                    barcodeScanner.setOrientationLocked(false); ///unlock rotating
                    barcodeScanner.setCaptureActivity(CaptureActivityPortrait.class); ///rotated to portrait
                    barcodeScanner.initiateScan(); ///start scanning activity
                }
            }
        });
        Button button2 = findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showProduct = new Intent(context,CheckActivity.class);
                showProduct.putStringArrayListExtra("barcodes", (ArrayList<String>) barcodes);
                showProduct.putStringArrayListExtra("name" ,(ArrayList<String>) name);
                showProduct.putStringArrayListExtra("amount", (ArrayList<String>) amount);
                startActivity(showProduct);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {   ///isset result form scanning barcode
            if(result.getContents() != null) { ///resoult is not empty String
                barcode = result.getContents(); /// resoult as String to barcode variable
                Toast.makeText(context, "Skan kodu zakończony pomyślnie", Toast.LENGTH_LONG).show();
                Intent addProduct = new Intent(context, AddProduct.class);
                addProduct.putExtra("barcode", barcode);    ///scanned barcode as extras
                startActivity(addProduct);      ///start activity in AddProduct.class
            } else {
                Toast.makeText(context, "Skan kodu zakończony niepowodzeniem", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Skan kodu zakończony niepowodzeniem", Toast.LENGTH_SHORT).show();
        }
    }
}
