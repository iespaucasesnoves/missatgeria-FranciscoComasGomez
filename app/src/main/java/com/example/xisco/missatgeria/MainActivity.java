package com.example.xisco.missatgeria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    private Button log_btn;
    private EditText nom, password;
    private static String url = "https://iesmantpc.000webhostapp.com/public/login/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log_btn = findViewById(R.id.login_btn);
        nom = findViewById(R.id.editText);
        password = findViewById(R.id.editText);
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put(nom.getText().toString(), password.getText().toString());
                Login.CridadaPost(url,parameters);
            }
        });
    }

}
