package org.androidpn.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class InstallTask implements Runnable {

	 public static final String LOGTAG = "Jackie Install Queue";
	    private Handler mHandler = null;
	    private Context mContext = null;
	    
	    private BlockingQueue<String> installQueue = new LinkedBlockingQueue<String>();
	    public Boolean run = false;
	    private Thread thread = null;

	    public InstallTask(Context mContext, Handler mHandler) {
	        super();
	        this.mHandler = mHandler;
	        this.mContext = mContext;
	        thread = new Thread(this);
	    }

	    public void startInstall() {
	        if (!run) {
	            run = true;
	            thread.start();
	        }
	    }
	    
	    public void stopInstall() {
	        run = false;
	    }
	    
	    public void putTask(String apkName) {
	        try {
	            installQueue.put(apkName);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    //silent install and then add item to the database
	    public void SilentInstall(String path){     
	    	if(path.equals(null) || path.length() == 0)
	    		return;
			Process process = null;
	        try {
	            File installed = new File(path);
	        	if(installed.exists()){
	        		 process = Runtime.getRuntime().exec("pm install -r "+ installed);
	        	}
	           }catch(Exception e) {
	        	   Log.d(LOGTAG, "exception occures, the download failed~~");
	        	   e.printStackTrace();
	           } finally {
				if (process != null) {
					process.destroy();
				}
			}
	    }
	    
	public void run() {
		  while (run) {
	            try {
	                String ai = installQueue.take();
	                SilentInstall(ai);
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }

	}

}
