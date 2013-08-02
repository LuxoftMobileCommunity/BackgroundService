package com.example.myservice;

import java.util.Random;

import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;



public class MyService extends Service {

	private final IBinder mBinder = new LocalBinder();
	private final Random mGenerator = new Random();
   // private Intent intent;
	
	public class LocalBinder extends Binder {
		MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//TODO do something useful
		Log.d("test MyService", "starting srvice");
       // this.intent = intent;
		return Service.START_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		//TODO for communication return IBinder implementation
		Log.d("test MyService", "Bind to service");
        //this.intent = intent;
		return mBinder;
	}
	public int getRandomNumber() {
		Log.d("test MyService", "Get random number");
       /* NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(this);

        mBuilder.setContentTitle("My Application")
                .setContentText("Downloading...")
                .setProgress(0, 0, true)
                .setSmallIcon(R.drawable.ic_launcher);

        PendingIntent in = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        mBuilder.setContentIntent(in);

        mNotificationManager.notify(0, mBuilder.build());*/
        int x = mGenerator.nextInt(100);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        CharSequence notiText = "Your notification from the service";
        long meow = System.currentTimeMillis();

        Notification notification = new Notification(icon, notiText, meow);

        Context context = getApplicationContext();
        CharSequence contentTitle = "Your notification";
        CharSequence contentText = "Random: "+x;
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int SERVER_DATA_RECEIVED = 1;
        notificationManager.notify(SERVER_DATA_RECEIVED, notification);

		return x;
	}

}
