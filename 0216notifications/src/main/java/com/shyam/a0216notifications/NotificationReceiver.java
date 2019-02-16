package com.shyam.a0216notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    public final static String ACTION_NOTIFICATION_UPDATE = BuildConfig.APPLICATION_ID + ".ACTION_NOTIFICATION_UPDATE";

    NotificationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ((MainActivity) context).update();
    }
}
