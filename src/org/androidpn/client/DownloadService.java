package org.androidpn.client;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends IntentService {
	
	public DownloadService(String name) {
		super(name);
	}

	public static final String LOGTAG = LogUtil.makeLogTag(DownloadService.class);
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
//		NotificationIQ notification = new NotificationIQ();
//		String notificationConID = intent.
//    			getStringExtra(Constants.NOTIFICATION_CONFIGURATION);
//    	
//    	String notificationDevID =  intent.
//    			getStringExtra(Constants.NOTIFICATION_DEVICEID);
//    	
//    	ArrayList<Resource> lists = intent.
//    			getParcelableArrayListExtra(Constants.NOTIFICATION_RESOURCES_LIST);
    	
    	Log.i(LOGTAG, "begin to write data to the sharedprefence");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		
	}

	
	

}
