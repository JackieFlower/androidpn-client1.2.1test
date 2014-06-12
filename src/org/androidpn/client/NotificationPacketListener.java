package org.androidpn.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NotificationPacketListener implements PacketListener {
	private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationPacketListener.class);

	 private final XmppManager xmppManager;

	    public NotificationPacketListener(XmppManager xmppManager) {
	        this.xmppManager = xmppManager;
	    }

	    public void processPacket(Packet packet) {
	        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
	        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

	        if (packet instanceof NotificationIQ) {
	            NotificationIQ notification = (NotificationIQ) packet;

	            if (notification.getChildElementXML().contains(
	                    "androidpn:iq:notification")) {
	            	Log.d(LOGTAG, "ChildElementXML="+notification.getChildElementXML());
	            	
	            	String configID = notification.getConfigID();
	            	String devID = notification.getDeviceID();
	            	ArrayList<Resource> lists = notification.getResources();
	            	          
	                String notificationFrom = notification.getFrom();
	                String packetId = notification.getPacketID();

	                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
	                
	                intent.putExtra(Constants.NOTIFICATION_CONFIGURATION, 
	                		configID);
	                intent.putExtra(Constants.NOTIFICATION_DEVICEID, 
	                		devID);
	                
	                
	                /*将resource list 放入bundle中，将bundle放入Intent中，进行传输*/
	                Bundle bundle = new Bundle();
	                bundle.putParcelableArrayList(Constants.NOTIFICATION_RESOURCES_LIST, lists);
	               
	                Log.d("NPListener", "put the lists to the bundle then to the intent~~");
	                intent.putExtras(bundle);

	                intent.putExtra(Constants.NOTIFICATION_FROM, notificationFrom);
	                intent.putExtra(Constants.PACKET_ID, packetId);
	                
	                IQ result = NotificationIQ.createResultIQ(notification);    
	                xmppManager.getConnection().sendPacket(result);
	                xmppManager.getContext().sendBroadcast(intent); 
	            }
	        }

	    }

}
