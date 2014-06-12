package org.androidpn.laumanager;

import android.net.Uri;

public final class DBCONSTANTS {
	
	public static final String TYPE = "";
	public final static String APP_TABLE = "AppInfo";
	public final static String VIDEO_TABLE = "VideoInfo";
	public final static String PIC_TABLE = "PicInfo";
	public final static String TV_TABLE = "TVInfo";
	
	public static final String DB_Name= "TVLauncherDB";
	
	public static final String[] TABLES = new String[]{APP_TABLE,
										   VIDEO_TABLE, PIC_TABLE, TV_TABLE}; 
    

	public static final String APP_TYPE = "vnd.android.cursor.dir/" + APP_TABLE;
	public static final String APP_ITEM_TYPE = "vnd.android.cursor.item/" + APP_TABLE;
	
	public static final String VIDEO_TYPE = "vnd.android.cursor.dir/" + VIDEO_TABLE;
	public static final String VIDEO_ITEM_TYPE = "vnd.android.cursor.item/" + VIDEO_TABLE;
	
	public static final String TV_TYPE = "vnd.android.cursor.dir/" + TV_TABLE;
	public static final String TV_ITEM_TYPE = "vnd.android.cursor.item/" + TV_TABLE;
	
	public static final String PIC_TYPE = "vnd.android.cursor.dir/" + PIC_TABLE;
	public static final String PIC_ITEM_TYPE = "vnd.android.cursor.item/" + PIC_TABLE;
    
    public static final String AUTHORITY = "org.androidpn.laumanager.dbprovider";
    
    public static final int APKS = 1;
    public static final int APK = 2;
    
    public static final int VIDEOS = 3;
    public static final int VIDEO = 4;
    
    public static final int TVS = 5;
    public static final int TV = 6;
    
    public static final int PICS = 7;
    public static final int PIC = 8;
    

    public static final Uri APK_NOIFY_URI = 
				Uri.parse("content://" + DBCONSTANTS.AUTHORITY + "/" + DBCONSTANTS.APP_TABLE);
    public static final Uri VIDEO_NOIFY_URI = 
			Uri.parse("content://" + DBCONSTANTS.AUTHORITY + "/" + DBCONSTANTS.VIDEO_TABLE);
    public static final Uri TV_NOIFY_URI = 
			Uri.parse("content://" + DBCONSTANTS.AUTHORITY + "/" + DBCONSTANTS.TV_TABLE);
    public static final Uri PIC_NOIFY_URI = 
			Uri.parse("content://" + DBCONSTANTS.AUTHORITY + "/" + DBCONSTANTS.PIC_TABLE);
    
    
}
