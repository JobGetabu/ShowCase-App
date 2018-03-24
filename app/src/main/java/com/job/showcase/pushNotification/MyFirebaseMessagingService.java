package com.job.showcase.pushNotification;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.job.showcase.R;

/**
 * Created by Job on Saturday : 3/24/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageTitle = remoteMessage.getNotification().getTitle();
        String messageBody = remoteMessage.getNotification().getBody();
        String clickAction = remoteMessage.getNotification().getClickAction();

        String messagedata = remoteMessage.getData().get("message");
        String fromData =remoteMessage.getData().get("from_id");

        // Create an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(clickAction);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.putExtra("message",messagedata);
        resultIntent.putExtra("from_id",fromData);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageTitle))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = (int)System.currentTimeMillis();
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build());
    }
}
