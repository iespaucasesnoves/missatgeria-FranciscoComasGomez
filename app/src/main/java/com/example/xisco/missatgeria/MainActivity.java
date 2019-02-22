package com.example.xisco.missatgeria;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity{
    private Button log_btn;
    private EditText nom, password;
    private static String url = "https://iesmantpc.000webhostapp.com/public/login/";
    private static Preferencies preferencies;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getBaseContext();
        SharedPreferences prefs = this.getSharedPreferences(
                "PreferenciesQuepassaEh", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
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
                    montaPrefs(login.res, password.getText().toString());
                }
            }
        });
    }
    public void montaPrefs(String data, String pwd){
        try {
            HashMap<String, String> pref = jsonToMap(data);
            editor.putInt("id", 20);
            editor.putString("user", pref.get("nom"));
            editor.putString("passwd", pwd);
            editor.putString("token", pref.get("token"));
            preferencies = new Preferencies(getBaseContext());
            Toast.makeText(this, preferencies.getAll(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, String> jsonToMap(String t) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }
}
