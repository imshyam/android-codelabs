package com.shyam.a0216notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shyam.a0216notifications.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final static String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager notificationManager;
    private ActivityMainBinding binding;
    private NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        updateVisibility(true, false, false);

        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationReceiver.ACTION_NOTIFICATION_UPDATE);
        filter.addAction(NotificationReceiver.ACTION_NOTIFICATION_CANCEL);
        notificationReceiver = new NotificationReceiver();
        this.registerReceiver(notificationReceiver, filter);

    }

    void updateVisibility(boolean notify, boolean cancel, boolean update) {
        binding.setVisibleNotify(notify);
        binding.setVisibleCancel(cancel);
        binding.setVisibleUpdate(update);
    }

    void createNotificationChannel() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setDescription("Notification from Code labs App.");
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Intent userCancelIntent = new Intent(NotificationReceiver.ACTION_NOTIFICATION_CANCEL);
        PendingIntent cancelPI = PendingIntent.getBroadcast(this, NOTIFICATION_ID, userCancelIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified.")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDeleteIntent(cancelPI)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notificationBuilder;
    }

    public void sendNotification(View view) {
        createNotificationChannel();
        NotificationCompat.Builder builder = getNotificationBuilder();

        Intent notificationIntent = new Intent(NotificationReceiver.ACTION_NOTIFICATION_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);
        builder.addAction(R.drawable.ic_update, "Update", pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        updateVisibility(false, true, true);

    }

    public void updateNotification(View view) {
        update();
    }
    void update() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.setStyle(new NotificationCompat
                .BigPictureStyle().bigPicture(bitmap)
                .setBigContentTitle("Notification Updated!"));
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        updateVisibility(false, true, false);
    }
    public void cancelNotification(View view) {
        cancel();
    }
    void cancel() {
        notificationManager.cancel(NOTIFICATION_ID);
        updateVisibility(true, false, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(notificationReceiver);
    }
}
