package com.example.xisco.missatgeria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Chat extends AppCompatActivity {
    DescarregaMissatges asyncNetworkStream = null;
    TextView txt_send;
    ListView lst_persones;
    Button btn_serch;
    DataSourcePrefs db = new DataSourcePrefs(this);
    private String url = "https://iesmantpc.000webhostapp.com/public/provamissatge/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lst_persones = findViewById(R.id.llista);
        txt_send = findViewById(R.id.misstage_txt);

        btn_serch = findViewById(R.id.send_btn);

        asyncNetworkStream = new DescarregaMissatges(getBaseContext(), db);
        asyncNetworkStream.execute(url);

        btn_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
