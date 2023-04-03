package com.adi.pfe2023;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@androidx.annotation.NonNull String token) {
        super.onNewToken(token);
        Map<String, Object> tokenData= new HashMap<>();
        tokenData.put("token", token);
        FirebaseFirestore firestore=
                FirebaseFirestore.getInstance();
        firestore.collection("DeviceTokens")
                .document()
                .set(tokenData);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        final String CHANNEL_ID = "Notification alerte";

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Alerte effraction",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification =
                new Notification.Builder(
                        this, CHANNEL_ID
                )
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat
                .from(this)
                .notify(1, notification.build());
    }
}
