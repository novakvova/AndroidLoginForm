package com.example.novak.myexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.*;
import java.io.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    final private String host="https://27s9.azurewebsites.net/";
    private Button btn_login;
    private EditText txt_email, txt_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
    }
    public void login() {
        btn_login = (Button)findViewById(R.id.btnLogin);
        txt_email = (EditText)findViewById(R.id.txtEmail);
        txt_password = (EditText)findViewById(R.id.txtPassword);

        btn_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //String email=txt_email.getText();
//                            HttpsHelper helper = new HttpsHelper(host);
//                            Map<String, String> parameters = new HashMap<String, String>();
//                            parameters.put("username", "ww@ww.ww");
//                            parameters.put("password", "Qwerty1-");
//                            parameters.put("grant_type", "password");
//                            String data = helper.postFormSend("Token", parameters);
//                            Toast.makeText(MainActivity.this,
//                                    "Good Request "+data,Toast.LENGTH_LONG).show();


                        } catch (Exception ioe) {
                            Toast.makeText(MainActivity.this,
                                   "Error url "+ioe.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    /**
     * Send a POST request to itcuties.com
     * @param email
     * @throws IOException
     */
    public String GetDataByServer(String email, String password) throws IOException {
        // Encode the query
        String encodedQuery = URLEncoder.encode(email, "UTF-8");
        String encodedQuery1 = URLEncoder.encode(password, "UTF-8");
        // This is the data that is going to be send to itcuties.com via POST request
        // 'e' parameter contains data to echo
        String postData = "username=" + email+"&password="+encodedQuery1+
                "&grant_type=password";

        String requestUrl = host+"Token";
        // Connect to google.com
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length",  String.valueOf(postData.length()));

        // Write data
        OutputStream os = connection.getOutputStream();
        os.write(postData.getBytes());

        // Read response
        StringBuilder responseSB = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ( (line = br.readLine()) != null)
            responseSB.append(line);

        // Close streams
        br.close();
        os.close();

        return responseSB.toString();

    }
}
