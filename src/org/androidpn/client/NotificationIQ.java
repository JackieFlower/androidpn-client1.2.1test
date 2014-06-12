package org.androidpn.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.packet.IQ;

public class NotificationIQ extends IQ {
	private String deviceID;
	private String configID;
	private ArrayList<Resource> resources;
	
	public NotificationIQ() {
		super();
		resources = new ArrayList<Resource>();
		resources.clear();
	}

	public String getDeviceID() {
		return deviceID;
	}


	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getConfigID() {
		return configID;
	}

	public void setConfigID(String configID) {
		this.configID = configID;
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		StringBuilder buf = new StringBuilder();
		 buf.append("<").append("notification").append(" xmlns=\"").append(
	                "androidpn:iq:notification").append("\">");
		 
	        if (deviceID != null) {
	            buf.append("<device_id>").append(deviceID).append("</device_id>");
	        }
	        if(configID != null){
	        	buf.append("<config_id>").append(configID).append("</config_id>");
	        }
	        if(!resources.isEmpty()){
	        	Iterator<Resource> itr = resources.iterator();
	        	while(itr.hasNext()){
	        		Resource res = itr.next();
	        		if(res.getResourceId() != null){
	    	        	buf.append("<resource_id value=\"").append(res.getResourceId())
	    	        		.append("\">");
	        		}
	        		if(res.getResourceUri() !=  null){
	        			buf.append("<resource_uri>").append(res.getResourceUri())
	        			.append("</resource_uri>");
	        		}
	        		if(res.getPicUri() != null){
	        			buf.append("<picture_uri>").append(res.getPicUri())
	        			.append("</picture_uri>");
	        		}
	        		buf.append("</resource_id>");
	        	}	
	        }
	        buf.append("</").append("notification").append("> ");
	        return buf.toString();
	}

}
