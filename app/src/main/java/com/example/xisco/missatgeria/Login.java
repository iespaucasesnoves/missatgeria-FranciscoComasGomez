package com.example.xisco.missatgeria;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Login extends AsyncTask<String, Void, String> {
    public static String res = "";
    HashMap<String, String> login;
    Context context;

    public Login(HashMap<String, String> login, Context context){
        this.login = login;
        this.context = context;
    }
    private static String montaParametres(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        // A partir d'un hashmap clau-valor cream
        // clau1=valor1&clau2=valor2&...
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) { first = false;} else {result.append("&");}
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    @Override
    protected String doInBackground(String... strings) {
        String resultat="";
        try {
            URL url = new URL(strings[0]);
            Log.i("ResConnectUtils", "Connectant "+strings[0]);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setReadTimeout(15000);
            httpConn.setConnectTimeout(25000);
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(montaParametres(this.login));
            writer.flush();
            writer.close();
            os.close();
            int resposta = httpConn.getResponseCode();
            if (resposta == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(httpConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    resultat+=line;
                }
                Log.i("ResConnectUtils", resultat);

            }
            else {
                resultat="";
                Log.i("ResConnectUtils","Errors:"+resposta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        res = resultat;
        return resultat;
    }
}
