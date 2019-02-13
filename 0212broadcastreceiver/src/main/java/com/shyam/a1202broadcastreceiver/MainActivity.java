package com.shyam.a1202broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private CustomReceiver customReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customReceiver = new CustomReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);

        LocalBroadcastManager.getInstance(this).registerReceiver(customReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));

        this.registerReceiver(customReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(customReceiver);
        this.unregisterReceiver(customReceiver);
    }

    public void sendCustomBroadcast(View view) {
        Intent customBroadcast = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcast);
    }
}
