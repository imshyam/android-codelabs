package com.shyam.a0219alarmmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shyam.a0219alarmmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private  NotificationManager notificationManager;
    private final static int NOTIFICATION_ID = 0;
    private final static String NOTIFICATION_CHANNEL_ID = "primary_channel_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        createNotificationChannel();

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

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setSmallIcon(R.drawable.ic_walk_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public void onCheckChange(CompoundButton button, Boolean isChecked) {
        String toastMsg;
        if(isChecked) {
            deliverNotification(MainActivity.this);
            toastMsg = "Alarm On";
        } else {
            notificationManager.cancelAll();
            toastMsg = "Alarm Off";
        }
        Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT).show();
    }
}
