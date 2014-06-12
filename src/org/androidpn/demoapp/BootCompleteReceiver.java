package org.androidpn.demoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 Log.d("LibraryTestActivity", "recevie boot completed ... ");  
	        context.startService(new Intent(context, DemoAppActivity.class));  
	}

}
