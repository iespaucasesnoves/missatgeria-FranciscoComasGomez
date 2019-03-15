package com.example.xisco.missatgeria;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {
    DescarregaMissatges asyncNetworkStream = null;
    TextView txt_send;
    ListView llista_missatges;
    Button btn_serch;
    public static ArrayList<Missatge> msgs;
    MsgAdapter adapter;
    DataSourcePrefs db = new DataSourcePrefs(this);
    private String url = "https://iesmantpc.000webhostapp.com/public/provamissatge/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        handler.removeCallbacks(getResponceAfterInterval);
        handler.post(getResponceAfterInterval);
        llista_missatges = findViewById(R.id.llista);
        txt_send = findViewById(R.id.misstage_txt);
        msgs = new ArrayList<>();
        adapter = new MsgAdapter(this, msgs);
        llista_missatges.setAdapter(adapter);
        btn_serch = findViewById(R.id.send_btn);
        asyncNetworkStream = new DescarregaMissatges(getBaseContext(), db, adapter);
        asyncNetworkStream.execute(url);

        btn_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> missatge = new HashMap<>();
                missatge.put("msg", txt_send.getText().toString());
                missatge.put("codiusuari", String.valueOf(MainActivity.preferencies.getCodiusuari()));
                EnviarMissatge send = new EnviarMissatge(missatge, getBaseContext());
                send.execute(url);
                Toast.makeText(Chat.this, "Missatge Enviat", Toast.LENGTH_SHORT).show();
                asyncNetworkStream = new DescarregaMissatges(getBaseContext(), db, adapter);
                asyncNetworkStream.execute(url);

            }
        });
    }
    private final Handler handler = new Handler();
    private Runnable getResponceAfterInterval = new Runnable() {
        public void run() {
            try
            {
                asyncNetworkStream.execute(url);
            } catch (Exception e) {
            }
            handler.postDelayed(this, 5000*60);
        }
    };

}
class MsgAdapter extends ArrayAdapter<Missatge> {
    public MsgAdapter(Context context, ArrayList<Missatge> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Missatge msg = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.missatge, parent, false);
        }
        TextView missatge = (TextView) convertView.findViewById(R.id.msg);
        TextView data = (TextView) convertView.findViewById(R.id.data_msg);
        TextView usuari = (TextView) convertView.findViewById(R.id.nom_msg);
        missatge.setText(msg.getMsg());
        usuari.setText(msg.getUsuari());
        data.setText(msg.getDatahora());
        return convertView;
    }
}
