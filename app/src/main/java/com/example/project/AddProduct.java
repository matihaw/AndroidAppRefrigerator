package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    Context context = this;
    protected ImageButton productPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        EditText barcode = findViewById(R.id.editText);
        Bundle extras = getIntent().getExtras();    ///get bundle of extras
        if (extras == null) {   ///if ther is no extras, app wont crash
            return;
        }
        final String barcodeFromExtras = extras.getString("barcode");
        if (barcodeFromExtras != null) {
            barcode.setText("Kod: " + barcodeFromExtras); /// set barcode from extras to text field
        }

        productPhoto = findViewById(R.id.imageButton);
        productPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent takePhotoOfProduct = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                setResult(Activity.RESULT_OK,takePhotoOfProduct);
                startActivityForResult(takePhotoOfProduct, 1);  //start activity for result to get an photo of product

            }
        });
        Button submitButton = findViewById(R.id.button4);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText productName = findViewById(R.id.editText2);
                String productNameText = productName.getText().toString();
                EditText productAmount = findViewById(R.id.editText3);
                String productAmountText = productAmount.getText().toString();
                System.out.println(productAmountText);
                System.out.println(productNameText);
                if(!productAmountText.equals("") && !productNameText.equals("")){
                    Toast.makeText(context, "Kod kreskowy: " + barcodeFromExtras  + "\n nazwa produtu: "  + productNameText + "\n ilość: " + productAmountText, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,"Podaj wszystkie dane",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode ==  RESULT_OK){
                Bitmap productImage = (Bitmap) data.getExtras().get("data");    ///camera gives bitmap photo file
                if(productPhoto!=null){
                    productPhoto.setImageBitmap(productImage);      ///put taken photo to image field
                }
            }
        }
    }
}
