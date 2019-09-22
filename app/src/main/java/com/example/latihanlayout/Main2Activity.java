package com.example.latihanlayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity{

    private Button btnAbout;
    private TextView txtUser;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtUser = (TextView) findViewById(R.id.txtUser);

        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAbout();
            }
        });

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(getIntent().getExtras()!=null){
            /**
             * Jika Bundle ada, ambil data dari Bundle
             */
            Bundle bundle = getIntent().getExtras();
            txtUser.setText(bundle.getString("dataEmail"));
        }else{
            /**
             * Apabila Bundle tidak ada, ambil dari Intent
             */
            txtUser.setText(getIntent().getStringExtra("dataEmail"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(wifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(wifiManager.EXTRA_WIFI_STATE,wifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(getApplicationContext(), "Terhubung...",Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(getApplicationContext(), "Terputus...",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void openActivityAbout(){
        Intent intent = new Intent(this, aboutActivity.class);
        startActivity(intent);

    }

}
