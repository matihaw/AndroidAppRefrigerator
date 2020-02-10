package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {
    private Context context = this;

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

    }

}
