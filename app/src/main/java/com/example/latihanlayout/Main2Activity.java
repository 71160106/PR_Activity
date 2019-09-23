package com.example.latihanlayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.latihanlayout.service.MyJobService;

public class Main2Activity extends AppCompatActivity{

    private static final String TAG = "Main2Activity";
    private Button btnAbout;
    private TextView txtUser;
    private WifiManager wifiManager;
    private Button mNotifBtn;

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

        mNotifBtn = (Button) findViewById(R.id.notif_btn);

        final String message = "Ini adalah isi notifikasi";
        final String title = "Ini adalah judul notifikasi";

        mNotifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notifTemplate(title,message);
            }
        });
    }

    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                //.setRequiresCharging(true)
                /*.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)*/
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }

    private void notifTemplate(String title, String message){
        final Intent intent = new Intent(Main2Activity.this, Tab1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(Main2Activity.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(Main2Activity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title).setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.FLAG_AUTO_CANCEL);
        NotificationManager notificationManager =
                (NotificationManager) Main2Activity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,mBuilder.build());
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
