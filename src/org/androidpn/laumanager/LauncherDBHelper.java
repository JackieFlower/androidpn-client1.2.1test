package org.androidpn.laumanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LauncherDBHelper extends SQLiteOpenHelper{
	/*
	 * 每个元素都应个有一个resourceID项，这样方便以后查询以及背景图片查找
	 * 
	 * 通过解析resource_id来得到很多信息
	 * 
	 * */
	
	private final String CREATE_APPTBL_SQL = "create table if not exists " + DBCONSTANTS.APP_TABLE +"("				 //表名
			 + "apk_id integer primary key autoincrement,"//字段(0)
			 + "resource_id char(30), "					  //resourceID(1)
			 + "apk_name varchar(255),"   				  //apk文件名称(2)
			 + "apk_type integer ,"  					  //apk 类别(3)
			
			 + "app_show integer DEFAULT 0 ,"      	//是否显示已安装的APP(小于0表示不显示，
			 										//如果大于0表示显示位置）(4)
			 + "apk_label char(50)," 	//apk_label显示(5)
			 + "pkg_name varchar(255),"	 	//APP包名(6)
			 + "bkground_path varchar(255),"// 背景图片路径(7)
			 + "bkground_name varchar(255),"//背景图片名称(8)
			 + "relate_pic_id char(30),"	//背景图片ID（9），便于删除PIC
			 + "used_time integer,"			//APP使用次数(10)
			 + "last_used time"				//APP上次使用时间记录(11)
			 +")";
	
	private final String CREATE_VIDEOTBL_SQL = "create table if not exists " + DBCONSTANTS.VIDEO_TABLE +"(" //表名
			 + "video_id integer primary key autoincrement,"//(0)
			 + "resource_id char(30), "
			 + "video_name varchar(255),"   //video文件名称(1)
			 + "video_path varchar(255)," 	// video路径(2)
			 + "video_type integer,"  	//video 类别	(3)
			 + "video_show integer DEFAULT 0,"      	//是否显示已下载的Video(4)
			 + "bkground_path varchar(255),"// video背景图片路径(5)
			 + "bkground_name varchar(255)"//video背景图片名称(6)
			 +")";
	
	private final String CREATE_PICTBL_SQL = "create table if not exists " + DBCONSTANTS.PIC_TABLE +"(" //表名
			 + "pic_id integer primary key autoincrement,"//(0)
			 + "resource_id char(30), "		//PIC的resourceID(1)
			// + "Relate_RID char(30), "   //对应的ResourceID
			 + "pic_name varchar(255),"   //pic文件名称(2)
			 + "pic_path varchar(255)," 	// pic路径(3)
			 + "pic_type integer,"  	//pic 类别	(4)
			// + "app_show integer DEFAULT 0,"      	//是否显示已下载的pic(4)
			 + "relate_id char(30)"     //对应的资源号（5）
			 +")";
	
	private final String CREATE_TVTBL_SQL = "create table if not exists " + DBCONSTANTS.TV_TABLE +"(" //表名
			 + "tv_id integer primary key autoincrement,"//(0)
			 + "resource_id char(30), "
			 + "tv_name varchar(255),"   //tv文件名称(1)
			 + "tv_type integer,"  	//tv 类别	(3)
			 + "tv_show integer DEFAULT 1," //是否显示已下载的tv(4)
			 + "tv_url char(255),"			//
			 + "tv_bkgname char(255),"	//
			 + "tv_bkgpath char(255)"//
			 +")";
	
	
	private final static String TAG = "====DBHelper===";
	
	public LauncherDBHelper(Context context,String dbName){
		super(context,dbName,null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*创建数据库*/
		db.execSQL(CREATE_APPTBL_SQL);
		db.execSQL(CREATE_VIDEOTBL_SQL);
		db.execSQL(CREATE_PICTBL_SQL);
		db.execSQL(CREATE_TVTBL_SQL);
	}
	
	private boolean checkPKGColumnExist(SQLiteDatabase db, String tableName, String columnName){
		boolean result = false;
		Cursor cursor = null;
		try{
			cursor = db.rawQuery("SELECT * from" + tableName + "LIMIT 0", null);
			result = cursor != null && cursor.getColumnIndex(columnName) != -1;
		}catch(Exception e){
			Log.e(TAG, "checkPKGColumnExist..." + e.getMessage());
		}finally{
			if(null != cursor && !cursor.isClosed()){
				cursor.close();
			}
		}
		return result;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 数据库被改变时，将原先的表删除，然后建立新表  
        String dropAppTBL = "drop table if exists " + DBCONSTANTS.APP_TABLE;  
        String dropVideoTBL = "drop table if exists" + DBCONSTANTS.VIDEO_TABLE;
        String dropPICTBL = "drop table if exists" + DBCONSTANTS.PIC_TABLE;
        String dropTVTBL = "drop table if exists" + DBCONSTANTS.TV_TABLE;
        db.execSQL(dropAppTBL);  
        db.execSQL(dropVideoTBL);
        db.execSQL(dropPICTBL);
        db.execSQL(dropTVTBL);
        onCreate(db); 
	}
	private void initAPPTable(){
		
	}
}
