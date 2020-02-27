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
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
    Adding data to database via api
 */
public class AddProduct extends AppCompatActivity {
    private Context context = this;
    /*

        APPLICATION VERSION FINAL2.0
        private Bitmap productImage;
        private ImageButton productPhoto;
    */
    /*
    Start new activity

     */
    public void startNewActivity(Class<? extends Activity> T, String extras){
        Intent intent = new Intent(context,T);
        intent.putExtra("id",extras);
        startActivity(intent);
    }
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
        final String userId = extras.getString("id");
        /*
                     APPLICATION VERSION FINAL2.0

            productPhoto = findViewById(R.id.imageButton);
            productPhoto.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent takePhotoOfProduct = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    setResult(Activity.RESULT_OK,takePhotoOfProduct);
                    startActivityForResult(takePhotoOfProduct, 1);  //start activity for result to get an photo of product

                }
            });
        */

        Button submitButton = findViewById(R.id.button4);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///input parameters
                final EditText productName = findViewById(R.id.editText2);
                final String productNameText = productName.getText().toString();
                final EditText productAmount = findViewById(R.id.editText3);
                final String productAmountText = productAmount.getText().toString();
                if(!productAmountText.equals("") && !productNameText.equals("")){
                    Thread thread = new Thread(new Runnable() {
                        /*
                        Insert data via api
                         */
                       @Override
                        public void run() {
                            try  {
                                URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Hello");
                                String urlParameters = "barcode=" + barcodeFromExtras + "&name=" + productNameText + "&amount=" + productAmountText + "&id=" + userId;
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
                    thread.start();
                    try{
                        thread.join(); /// wait untill uploading data is finished
                    }catch (Exception e) {
                        Toast.makeText(context,"Error" + e,Toast.LENGTH_LONG).show();
                    }
                    startNewActivity(MainActivity.class, userId);
                } else {
                    Toast.makeText(context,"Podaj wszystkie dane",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    /*
    get data from result activity
     NEED TO FINISH ADDING PRODUCT PHOTO IN VERSION FINAL2.0 OF APPLICATION
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode ==  RESULT_OK){
                productImage = (Bitmap) data.getExtras().get("data");    ///camera gives bitmap photo file
                if(productPhoto!=null){
                    productPhoto.setImageBitmap(productImage);      ///put taken photo to image field
                }
            }
        }
    }*/
}
