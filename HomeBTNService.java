package com.shakil.stop;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class HomeBTNService extends Service {

    protected Integer NOTIFICATION_ID = 23213123;
    private static final String TAG = "HomeBTNService";
    private PowerManager.WakeLock wakeLock;
    private AppForegroundControler appForegroundControler;


    public HomeBTNService() {
        Log.d(TAG, "HomeBTNService: ");
    }




    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");


        LoadNotification loadNotification = new LoadNotification("Device is in locked Mode", "User is Driving");
        loadNotification.notifyMessage();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        wakeLock.acquire();

        appForegroundControler = new AppForegroundControler(this);
        appForegroundControler.startTracking();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }







    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: ");

        appForegroundControler.stopTracking();

        if(wakeLock!=null) wakeLock.release();



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    class LoadNotification {

        private String titleMessage;
        private String textMessage;


        public LoadNotification(String titleMessage, String textMessage) {
            this.titleMessage = titleMessage;
            this.textMessage = textMessage;
        }

        public void notifyMessage() {
            NotificationCompat.Builder builder = getNotificationBuilder(MainActivity.class);
            startForeground(NOTIFICATION_ID, builder.build());

        }

        protected NotificationCompat.Builder getNotificationBuilder(Class clazz) {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

            builder.setSmallIcon(R.drawable.ic_launcher);  // icon id of the image

            builder.setContentTitle(this.titleMessage)
                    .setContentText(this.textMessage)
                    .setContentInfo("JukeSpot");

            Intent foregroundIntent = new Intent(getApplicationContext(), clazz);

            foregroundIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, foregroundIntent, 0);

            builder.setContentIntent(contentIntent);
            return builder;
        }

    }


}
