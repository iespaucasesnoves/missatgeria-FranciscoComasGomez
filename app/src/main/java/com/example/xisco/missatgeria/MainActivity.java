package com.example.xisco.missatgeria;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    private Button log_btn;
    private EditText nom, password;
    private static String url = "https://iesmantpc.000webhostapp.com/public/login/";
    public static Preferencies preferencies;
    private static SharedPreferences prefs;
    private ReceptorXarxa receptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receptor = new ReceptorXarxa();
        this.registerReceiver(receptor, filter);

        Context context = getBaseContext();
        prefs = this.getSharedPreferences(
                "PreferenciesQuepassaEh", Context.MODE_PRIVATE);
        log_btn = findViewById(R.id.login_btn);
        nom = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("nom", nom.getText().toString());
                parameters.put("password", password.getText().toString());
                Login login = new Login(parameters, getBaseContext());
                login.execute(url);
                if(preferencies == null) {
                    if(montaPrefs(login.res, password.getText().toString())){
                        Intent i = new Intent(MainActivity.this, Chat.class);
                        startActivity(i);
                    }
                }else{
                    Intent i = new Intent(MainActivity.this, Chat.class);
                    startActivity(i);
                }
            }
        });

    }
    public boolean montaPrefs(String data, String pwd){
        try {
            JSONObject object = new JSONObject(data);
            prefs.edit().putInt("id", Integer.parseInt(object.getJSONObject("dades").getString("codiusuari"))).apply();
            prefs.edit().putString("user", object.getJSONObject("dades").getString("nom")).apply();
            prefs.edit().putString("passwd", pwd).apply();
            prefs.edit().putString("token", object.getJSONObject("dades").getString("token")).apply();
            preferencies = new Preferencies(getBaseContext());
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void onStart() {
        super.onStart();
        //Obtenim un gestor de les connexions de xarxa
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Obtenim l’estat de la xarxa
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //Obtenim l’estat de la xarxa mòbil
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean connectat3G = networkInfo.isConnected();

        //Obtenim l’estat de la xarxa Wifi
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean connectatWifi = networkInfo.isConnected();

        if (networkInfo != null && connectat3G) {
            //Xarxa OK
            Toast.makeText(this, "Xarxa ok, conectat3G", Toast.LENGTH_LONG).show();
        } else if (networkInfo != null && connectatWifi){
            //Xarxa OK
            Toast.makeText(this, "Xarxa ok, conectat Wifi", Toast.LENGTH_LONG).show();
        } else {
            //Xarxa no disponible
            Toast.makeText(this, "Xarxa no disponible", Toast.LENGTH_LONG).show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        //Donam de baixa el receptor de broadcast quan es destrueix l’aplicació
        if (receptor != null) {
            this.unregisterReceiver(receptor);
        }
    }
}
