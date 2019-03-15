package com.example.xisco.missatgeria;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DescarregaMissatges extends AsyncTask<String, Void, String> {

    private Context context;
    private InputStream in;
    private DataSourcePrefs db;
    private MsgAdapter adapter;
    protected DescarregaMissatges(Context context, DataSourcePrefs db, MsgAdapter adapter){
        this.context = context;
        this.db = db;
        this.adapter = adapter;
    }

    protected String doInBackground(String... urls) {
        URL url = null;
        HttpsURLConnection urlConnection = null;
        StringBuilder sb;
        try {
            url =new URL(urls[0]);
            urlConnection =(HttpsURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            sb = new StringBuilder();
            String line;

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try{
                urlConnection.disconnect();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String in) {

        try{
            JSONObject jsonObject = new JSONObject(in);
            JSONArray jsonArray = jsonObject.getJSONArray("dades");

            boolean bo = jsonObject.getBoolean("correcta");

            if (!bo) {
                Toast.makeText(context,"Dades incorrectes",Toast.LENGTH_LONG).show();
            }

            ArrayList<HashMap<String, Object>> llista = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                db.open();
                JSONObject jsonObjectTemp = jsonArray.getJSONObject(i);
                if(!db.usuariExist(jsonObjectTemp.getString("codiusuari"))){
                    db.guardarUsuari(jsonObjectTemp.getString("codiusuari"), jsonObjectTemp.getString("nom"), "");
                }
                if(!db.msgExist(jsonObjectTemp.getString("codi"))){
                    db.guardaMissatges(jsonObjectTemp.getString("codi"), jsonObjectTemp.getString("datahora"), jsonObjectTemp.getString("msg"), jsonObjectTemp.getString("codiusuari"));
                }
                db.close();
            }
            db.open();
            Chat.msgs = db.getAllMissatges();
            db.close();
            adapter.addAll(Chat.msgs);
            adapter.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
