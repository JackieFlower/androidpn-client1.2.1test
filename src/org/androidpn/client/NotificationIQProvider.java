package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

public class NotificationIQProvider implements IQProvider{
	private static final String TAG = "NOTIFICATION_IQ_PROVIDER ";
	
	public NotificationIQProvider(){}
	
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		Log.d(TAG, "begin to parse notification iq");
		NotificationIQ notification = new NotificationIQ();
		Resource resource = null;
		for(boolean done = false; !done;){
			int eventType = parser.next();
			if(eventType == XmlPullParser.START_TAG){//判断当前事件是否是标签元素开始事件
				if("device_id".equals(parser.getName())){
					notification.setDeviceID(parser.nextText());
					
					Log.d(TAG, "device_id = " + notification.getDeviceID());
				}else if("config_id".equals(parser.getName())){
					notification.setConfigID(parser.nextText());
					Log.d(TAG, "config_id = " + notification.getConfigID());
					
				}else if("resource_id".equals(parser.getName())){
					resource = new Resource();
					resource.setResourceId(parser.getAttributeValue(0));	
					if(resource != null)
						Log.d(TAG, "resource_id = " + resource.getResourceId());
				}else if(resource != null){
					if("resource_uri".equals(parser.getName())){
						resource.setResourceUri(parser.nextText());
						Log.d(TAG, resource.getResourceUri());
					}else if("picture_uri".equals(parser.getName())){
						resource.setPicUri(parser.nextText());
						Log.d(TAG, resource.getPicUri());
					}
				}
			}else if(eventType == XmlPullParser.END_TAG &&
					"resource_id".equals(parser.getName())){
				notification.getResources().add(resource);
				resource = null;
				Log.d(TAG, "/resource_id");
			}else if(eventType == XmlPullParser.END_TAG &&
					 "notification".equals(parser.getName())){
				done = true;
			}			
		}
	        Log.d(TAG, "get out of the parse");
	        Log.d(TAG, "notification == null  = " + (notification==null));
	        
	        Log.d(TAG, notification.getChildElementXML());
	        
	        return notification;
	}

}
