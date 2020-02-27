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
        import java.util.Arrays;
        import java.util.List;
        import java.util.Random;

public class CheckActivity extends AppCompatActivity {
    private Context context = this;
    private EditText et = null; ///Text field
    ///Start new activity
    public void startNewActivity(Class<? extends Activity> T, String extras){
        Intent intent = new Intent(context,T);
        intent.putExtra("id", extras);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        /*
            Get values from extras
         */
        final String userId = getIntent().getStringExtra("id");
        List<String> barcodes = getIntent().getStringArrayListExtra("barcodes");
        List<String> name = getIntent().getStringArrayListExtra("name");
        List<String> amount = getIntent().getStringArrayListExtra("amount");
        /*
            Prepare output data
         */
        StringBuilder barcodesOutputBuilder = new StringBuilder("Barcode: \n");
        StringBuilder nameOutputBuilder = new StringBuilder("Name: \n");
        StringBuilder amountOutputBuilder = new StringBuilder("Amount: \n");

        /*
            order data and prepare for layout output
        */
        for(int i=0;i<barcodes.size();i++) {
            barcodesOutputBuilder.append(barcodes.get(i) + "\n");
            nameOutputBuilder.append(name.get(i) + "\n");
            amountOutputBuilder.append(amount.get(i) +"\n");
        }

        /*
            Print data on phone screen
         */
        EditText idText = findViewById(R.id.id);
        EditText nameText = findViewById(R.id.name);
        EditText amountText = findViewById(R.id.amount);
        idText.setText(barcodesOutputBuilder.toString());
        nameText.setText(nameOutputBuilder.toString());
        amountText.setText(amountOutputBuilder.toString());

        /*
            Delete single record
         */
        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = findViewById(R.id.idToDelete);
                /*
                    Check if barcode_id is given from user
                 */
                if(et.getText().toString().equals("")){
                    Toast.makeText(context, "Give important data", Toast.LENGTH_LONG).show();
                }else{
                    et = findViewById(R.id.idToDelete);
                    Thread deleteData = new Thread(new Runnable() {
                        /*
                            Delete data via api

                         */
                        @Override
                        public void run() {
                            try  {
                                URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Delete");
                                String urlParameters ="id=" + et.getText();
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
                    deleteData.start();
                    try{
                        deleteData.join();
                    }catch (Exception e){
                        Toast.makeText(context,"Error: " + e.toString(),Toast.LENGTH_LONG ).show();
                    }

                    startNewActivity(MainActivity.class, userId);
                }
            }
        });

        /*
            Delete everything in database
         */

        Button buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread deleteData = new Thread(new Runnable() {
                    /*
                        Delete data via api

                     */
                    @Override
                    public void run() {
                        try  {
                            URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Delete?id=" + userId);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setDoOutput(true);
                            con.setRequestMethod("DELETE");
                            con.setRequestProperty("User-Agent", "Java client");
                            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            new InputStreamReader(con.getInputStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                deleteData.start();
                try{
                    deleteData.join();
                }catch (Exception e) {
                    Toast.makeText(context,"Error" + e,Toast.LENGTH_LONG).show();
                }
                startNewActivity(MainActivity.class, userId);
            }
        });

        /*
            Insert random data to database
            ONLY FOR APPLICATION TESTS!!!
         */
        Button buttonAddRandomValues = findViewById(R.id.buttonAddRandomValues);
        buttonAddRandomValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread addRandom = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            URL url = new URL("https://matihaw17.ct8.pl/examples/servlets/servlet/Hello");
                            List<String> productName = Arrays.asList("bread","milk","watcher","coffee","cheese","juice","apple","ham","beer","butter");
                            for(int barcode=1; barcode<=10;barcode++) {
                                Random randomGenerator = new Random(10);
                                String urlParameters = "barcode=" + barcode + "&name=" + productName.get(barcode-1) + "&amount=" + (int)(Math.random()* (10)+1) + "&id=" + userId;
                                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setDoOutput(true);
                                con.setRequestMethod("POST");
                                con.setRequestProperty("User-Agent", "Java client");
                                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                                wr.write(postData);
                                new InputStreamReader(con.getInputStream());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                addRandom.start();
                try{
                    addRandom.join();
                }catch (Exception e) {
                    Toast.makeText(context,"Error" + e,Toast.LENGTH_LONG).show();
                }
                startNewActivity(MainActivity.class, userId);
            }
        });
    }
}
