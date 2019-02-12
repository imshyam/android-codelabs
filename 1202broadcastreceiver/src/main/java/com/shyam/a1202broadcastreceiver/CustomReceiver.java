package com.shyam.a1202broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.shyam.a1202broadcastreceiver.MainActivity.ACTION_CUSTOM_BROADCAST;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        String toastMsg = "Unknown intent action";
        if (intentAction != null) {
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMsg = "Power Connected";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMsg = "Power Disconnected";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMsg = "Custom Broadcast Received";
                    break;
            }
        }

        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
