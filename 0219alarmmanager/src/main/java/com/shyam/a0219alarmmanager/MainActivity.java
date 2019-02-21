package com.shyam.a0219alarmmanager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shyam.a0219alarmmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private  NotificationManager notificationManager;
    private final static int NOTIFICATION_ID = 0;
    private final static String NOTIFICATION_CHANNEL_ID = "primary_channel_id";
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        createNotificationChannel();

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        binding.setChecked(alarmUp);

    }

    void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Stand Up Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setDescription("Notifies every 15 minutes to stand up and walk");

            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onCheckChange(CompoundButton button, Boolean isChecked) {
        String toastMsg;
        if(isChecked) {
            long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            long triggerTime = SystemClock.elapsedRealtime();
            if(alarmManager != null && notifyPendingIntent != null) {
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime,
                        repeatInterval,notifyPendingIntent);
            }
            toastMsg = "Alarm On";
        } else {
            alarmManager.cancel(notifyPendingIntent);
            toastMsg = "Alarm Off";
        }
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
