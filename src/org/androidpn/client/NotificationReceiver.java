package org.androidpn.client;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

	private static final int SUCCESS = 1;

	private static final int FAILED = 0;
	
	private long downloadId = -1;
	
    private DownloadCompleteReceiver downloadCompleteReceiver;

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationReceiver.class);
    
    public static DownloadManager dManager ;
    
    //DownloadCompleteReceiver receiver;
    
    public static Resource[] arrays ;
    
    public static int count = 0;
    
    public static int idx = 0;

    public static boolean isFirst = true;
    
    public static boolean downPic = false;
    

    public NotificationReceiver() {
    	downloadCompleteReceiver =  new DownloadCompleteReceiver();
    	//arrays = new Resource[];
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
    	
        String action = intent.getAction();
        
        Log.d(LOGTAG, "action=" + action);	
        
	dManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
   	 	
   	 	
   	 	//if(dManager.)
   	 	//Cursor cursor = dManager.query()
   	 	//dManager.remove();
   	 	
        if (Constants.ACTION_SHOW_NOTIFICATION.equals(action) && isFirst == true) {
        	
        	Log.d(LOGTAG, "in the ACTION_SHOW_NOTIFICATION");
        	isFirst = false;
        	String notificationConID = intent.
        			getStringExtra(Constants.NOTIFICATION_CONFIGURATION);
        	
        	String notificationDevID =  intent.
        			getStringExtra(Constants.NOTIFICATION_DEVICEID);
        	
        	ArrayList<Resource> lists = (intent.
        			getParcelableArrayListExtra(Constants.NOTIFICATION_RESOURCES_LIST));
        	    	
        	//类型转换，如果使用toArray()函数就会报出转换异常的错误～～
        	arrays = new Resource[lists.size()];

        	/*现将数据设的小一些，便于测试，以后再修改*/
        	count = lists.size();

        	idx = 0;
        	
        	lists.toArray(arrays);
        	
        	Log.i(LOGTAG, "begin to write data to the sharedprefence");
        	
        	SharedPreferences sharedPrefs = XmppManager.getSharedPrefs();
            Editor editor = sharedPrefs.edit();
            editor.putString(Constants.NOTIFICATION_CONFIGURATION,
            		notificationConID);
            editor.putString(Constants.NOTIFICATION_DEVICEID,
            		notificationDevID);
            editor.commit();
            
            Log.i(LOGTAG, "ConfigueID and deviceID registered successfully"); 
        	
            String notificationFrom = intent
            		.getStringExtra(Constants.NOTIFICATION_FROM);
            String packetId = intent
    				.getStringExtra(Constants.PACKET_ID);
            
            Log.d(LOGTAG, "notificationConID = " + notificationConID);
            Log.d(LOGTAG, "notificationDevID = " + notificationDevID);
            Log.d(LOGTAG, "get the resource ID");
            Log.d(LOGTAG, "notificationFrom = " + notificationFrom);
            Log.d(LOGTAG, "packetId = " + packetId);
            
            PreparedForDownload(idx, context);
            
            downPic = false;
        }else if(Constants.ACTION_NOTIFICATION_INSTALLED.equals(action)){
        	long id = intent.getLongExtra("DownloadID", 0);
        	
        	if(id == downloadId)
        		Log.d(LOGTAG, "the previous file download successful");
        	
        	if(downPic == false && (arrays[idx].getPicUri() != null || 
        			arrays[idx].getPicUri().length() != 0)){
        		
        		Log.d(LOGTAG, "begin to download the related pic info, Good Luck~~");
        		DownloadPic(idx, context);
        		downPic = true;
        	}else{
        		Log.d(LOGTAG, "begin to download a new resource, Good Luck~~");
        		//idx的初始值为0，当其大小为count时已经下载完毕
        		idx++;
            	if(idx >= count){
            		isFirst = true;
            		downPic = false;
            		count = 0;
            		idx = 0;
            	}else{
            		unregisterDownloadCompleteReceiver(context);
            		PreparedForDownload(idx, context);
            		downPic = false;
            	}
        	}
        }
    }
    
    private void PreparedForDownload(int index, Context context){
        Resource res = arrays[index];
     	String sname = null;
    	int idex = res.getResourceUri().lastIndexOf("/");
    	
    	if(idex >= 0 && idex < res.getResourceUri().length() - 2){
    		sname = res.getResourceUri().substring(idex + 1);
        	Log.d(LOGTAG, "notificationSourceName=" + sname);
        	
        	downloadCompleteReceiver.setSourceName(sname);
        	
        	downloadCompleteReceiver.setResourceID(res.getResourceId());
        	
        	downloadCompleteReceiver.setRelatedID(null);
        	
        	registerDownloadCompleteReceiver(context);
        	
        	download(context, res.getResourceUri(), sname);
        	//download(context, res.getPicUri(), sname);
        	
    	}
    }
    
    private void DownloadPic(int index, Context context){
    	Resource res = arrays[index];
      	String sname = null;
     	int idex = res.getPicUri().lastIndexOf("/");
     	
     	if(idex >= 0 && idex < res.getPicUri().length() - 2){
     		sname = res.getPicUri().substring(idex + 1);
         	Log.d(LOGTAG, "picName=" + sname);
         	
         	downloadCompleteReceiver.setSourceName(sname);
         	
         	//downloadCompleteReceiver.setResourceID(res.getPicUri());
         	
         	Log.d(LOGTAG, "set the pic resourceId :null " );
         	downloadCompleteReceiver.setResourceID(null);
         	
         	Log.d(LOGTAG, " the pic resourceId :" + downloadCompleteReceiver.getResourceID() );
         	
         	downloadCompleteReceiver.setRelatedID(res.getResourceId());
         	
         	registerDownloadCompleteReceiver(context);
         	
         	download(context, res.getPicUri(), sname);
         	
         	/*Notifier notifier = new Notifier(context);
         	
             notifier.notify("index = " + index, "sname = " + sname,
             		 res.getResourceId(), res.getPicUri(), res.getPicUri(), 
                      null, null);*/
     	}
    }

    private int download(Context context, final String uri, final String source_name){
    	 if (uri != null
                 && uri.length() > 0
                 && (uri.startsWith("http:") || uri.startsWith("https:")
                         || uri.startsWith("tel:") || uri
                         .startsWith("geo:"))){
    		 DownloadManager.Request down = new DownloadManager.Request(Uri.parse(uri));
    		 down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|
    				 						DownloadManager.Request.NETWORK_WIFI); 
    		 
    		 down.setShowRunningNotification(true);
    		 
    		 down.setVisibleInDownloadsUi(false); 
    		 
    		 down.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,source_name);
    		 
    		 System.out.println("the destination dir is " + Environment.DIRECTORY_DOWNLOADS + "  " + source_name);
    		
    		 downloadId = dManager.enqueue(down); 
    		 
    		 Log.d(LOGTAG, "download begin! the download ID is " + downloadId);
    		 
    	 }else{
    		 return FAILED;
    	 }
    	 return SUCCESS;
    }
    
    private void initDownload(Context context){
    	//dManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
    	
   	 //	DownloadManager.Query parseDownloadQuery = new DownloadManager.Query();
   	 	
	   //	parseDownloadQuery.setFilterByStatus(DownloadManager.STATUS_PAUSED);
	   	 
	   //	Cursor pausedDownloads = dManager.query(parseDownloadQuery);
	   	
	   //	int reasonIdx = pausedDownloads.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
	   	
	   	
	   	
	   //	while(pausedDownloads.moveToNext()){
	   		
	   //	}
 
   	 	//dManager.remove();
    }
    
    //注册信息
    private void registerDownloadCompleteReceiver(Context context){
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
    	context.registerReceiver(downloadCompleteReceiver, filter);
    	Log.d(LOGTAG,"download complete receiver registerd!");
    }
    
    private void unregisterDownloadCompleteReceiver(Context context){
   	 	context.unregisterReceiver(downloadCompleteReceiver);
   }
}
