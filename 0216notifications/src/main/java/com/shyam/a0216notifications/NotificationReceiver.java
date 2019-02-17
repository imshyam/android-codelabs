package com.shyam.a0216notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    public final static String ACTION_NOTIFICATION_UPDATE = BuildConfig.APPLICATION_ID + ".ACTION_NOTIFICATION_UPDATE";
    public final static String ACTION_NOTIFICATION_CANCEL = BuildConfig.APPLICATION_ID + ".ACTION_NOTIFICATION_CANCEL";

    NotificationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction != null) {
            switch (intentAction) {
                case ACTION_NOTIFICATION_UPDATE:
                    ((MainActivity) context).update();
                    break;
                case ACTION_NOTIFICATION_CANCEL:
                    ((MainActivity) context).cancel();
                    break;
            }
        }
    }
}
