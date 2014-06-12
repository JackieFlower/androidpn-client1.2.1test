/*
 * 需要做的工作：每个表中都加入了resourceID字段，本程序中还没有进行改动
 * 
 * 当图片插入时需要对表进行更新
 * 
 * 是全局维护同一个resource资源表还是每个元素都要有一个resource属性
 * */

package org.androidpn.laumanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBManager {
	private static SQLiteDatabase DBEntity;
	private static LauncherDBHelper DBHelper;
	private static final String LOGTAG = "DBManager~";
	
	public DBManager(Context context){
		DBHelper = new LauncherDBHelper(context, DBCONSTANTS.DB_Name);
		DBEntity = DBHelper.getReadableDatabase();
		//init();
	}
	
	public DBManager(LauncherDBHelper helper)
	{
		DBHelper = helper;
		DBEntity = DBHelper.getReadableDatabase();
	}
	
	public void init(){
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
				" values(null,?,?,?,?,null,null,?,?,0, null)";
	//	DBEntity.execSQL(insertSql,
	//			new Object[]{resourceID, apk_name, apk_type , position, bkground_path, background_name});
		DBEntity.execSQL(insertSql, new Object[]{"1240000", "zhongguoxiangqi_162.apk",
				1, 1, "mnt/sdcard/Download", "qii.jpg"});
		
		DBEntity.execSQL(insertSql, new Object[]{"1260001", "AngryBirds_4100.apk",
				1, 2, "mnt/sdcard/Download", "bird.jpg"});
		
		DBEntity.execSQL(insertSql, new Object[]{"1220002", "baoweiluobu2.apk",
				1, 3, "mnt/sdcard/Download", "luobo.jpg"});
		DBEntity.execSQL(insertSql, new Object[]{"1210003", "buyudaren2_130.apk",
				1, 4, "mnt/sdcard/Download", "buyu.jpg"});
		DBEntity.execSQL(insertSql, new Object[]{"1270004", "shenmiaotaowang2.apk",
				1, 5, "mnt/sdcard/Download", "temple.jpg"});
		DBEntity.execSQL(insertSql, new Object[]{"1230005", "zhiwudazhanjiangshi2gaoqing.apk",
				1, 6, "mnt/sdcard/Download", "war.jpg"});
	//	DBEntity.execSQL(insertSql, new Object[]{"1240000", "zhongguoxiangqi_162.apk",
	//			1, 1, "mnt/sdcard/Download", "qii.jpg"});
		

	}
	
//	public String queryAPPByApkName(String apkName){
//	
//		cursor.moveToNext();
//		ContentValues values = new ContentValues();
//		
//		values.put("relate_pic_id", resourceID);
//		values.put("bkground_path", pic_path);
//		values.put("bkground_name", pic_name);	
//		
//		DBEntity.update(DBCONSTANTS.APP_TABLE, values, " where resource_id = ?", 
//				new String[]{relateID});
//		cursor.close();
//	}
//	
//	public boolean updateBkgPathBYAPKName(String apkName){
//		String querySQL = "select * from " + DBCONSTANTS.APP_TABLE + "where apk_name = ?";
//		Cursor cursor = DBEntity.rawQuery(querySQL, new String[]{apkName});
//		cursor.moveToFirst();
//		
//		if(cursor == null || cursor.getCount() == 0){
//			cursor.close();
//			return false;
//		}
//		cursor.moveToNext();
//		return false;
//	}
	
	
	/*
	 * 插入操作,需要的APK信息包括：
	 * resource_id
	 * apk_name, 
	 * apk_type,
	 * app_show, 
	 * apk_label,
	 * pkg_name,
	 * bkground_path, 
	 * background_name,
	 * used_times, 
	 * last_used
	 * */
	
	public void insertAPKData(String resourceID, String apk_name, int apk_type){
		
		String picResource = queryResourcePicAndDelete(apk_name);
		
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
				" values(null,?,?,?,0,null,null,null,null,?,0, null)";
		
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, apk_name, apk_type, picResource });
		
		Log.i(LOGTAG,"insert apk_name = " + 
				apk_name  + " apk_type = " + apk_type  + " resourceID = " + resourceID);
	}
	
	public void insertAPKData(String resourceID, String apk_name, int apk_type, int position){
		String picResource = queryResourcePicAndDelete(apk_name);
		
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
				" values(null,?,?,?,?,null,null,null,null,?,0, null)";
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, apk_name, apk_type , position, picResource});
		
		Log.i(LOGTAG,"insert apk_name = " + 
				apk_name  + " apk_type = " + apk_type  + " resourceID = " + resourceID
				+ " position = " + position);
	}
	
	public void insertAPKData(String resourceID, String apk_name, int apk_type, 
			int position, String pkgName){
		
		String picResource = queryResourcePicAndDelete(apk_name);
		
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
				" values(null,?,?,?,?,null,?,null,null,?,0, null)";
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, apk_name, apk_type , position, pkgName, picResource});
		
		Log.i(LOGTAG,"insert apk_name = " + 
				apk_name  + " apk_type = " + apk_type  + " resourceID = " + resourceID
				+ " position = " + position + 
				" pkgName = " + pkgName);
	}
	
	public void insertAPKData(String resourceID, String apk_name, int apk_type, 
			int position, String appLabel, String pkgName){

		String picResource = queryResourcePicAndDelete(apk_name);
		
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
				" values(null,?,?,?,?,?,?,null,null,?,0, null)";
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, apk_name, apk_type , position, appLabel, pkgName, picResource});
		
		Log.i(LOGTAG,"insert apk_name = " + 
		apk_name  + " apk_type = " + apk_type  + " resourceID = " + resourceID
		+ " position = " + position + " appLabel = " + appLabel +
		" pkgName = " + pkgName);
	}
	
	public String queryResourcePicAndDelete(String apkName){
		
		Log.d(LOGTAG, "begin to query the previous info~~");

		String query = "select * from " + DBCONSTANTS.APP_TABLE + " where apk_name = ?";
		Cursor cursor = DBEntity.rawQuery(query, new String[]{apkName});
		int rePicIdx = cursor.getColumnIndexOrThrow("relate_pic_id");
		String picResource = null;
		while(cursor.moveToNext()){
			picResource = cursor.getString(rePicIdx);
		}
		cursor.close();
		
		//String deleteSQL = "delete from " + DBCONSTANTS.APP_TABLE + " where apk_name = ?";
		
		String deleteSQL = " apk_name = ?";
		DBEntity.delete(DBCONSTANTS.APP_TABLE, deleteSQL, new String[]{apkName});
		
		return picResource;
	}
	
	public void insertAPKData(String resourceID, String apk_name, int apk_type,
			int position, String appLabel, String pkg_name, String bkground_path, String relateID,
			String background_name)
	{
		String insertSql = "insert into " + DBCONSTANTS.APP_TABLE + 
							" values(null,?,?,?,?,?,?,?,?,?,0, null)";
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, apk_name, apk_type, position, appLabel, pkg_name, bkground_path,
				relateID,background_name});
		Log.i(LOGTAG,"insert apk_name = " + apk_name  + " apk_type = " + apk_type);
		
	}
	
	public void updateBackPic(String relatedID, String picID, String bkPath, String bkName){
		System.out.println("begin to update the related ResourceID");
		
		ContentValues values = new ContentValues();
		if(picID != null)
			values.put("relate_pic_id", relatedID);
		
		values.put("bkground_path", bkPath);
		values.put("bkground_name", bkName);	
		
		DBEntity.update(DBCONSTANTS.APP_TABLE, values, " resource_id = ?", 
				new String[]{relatedID});
		
		showAPPs();
	}
	
	/*
	 *  + "video_name varchar(255),"   //video文件名称(1)
		 + "video_path varchar(255)," 	// video路径(2)
		 + "video_type integer,"  	//video 类别	(3)
		 + "video_show integer DEFAULT 0,"      	//是否显示已下载的Video(4)
		 + "bkground_path varchar(255),"// video背景图片路径(5)
		 + "bkground_name varchar(255),"//video背景图片名称(6)
	 * 
	 * */
	public void insertVIDEOData(String resourceID, String video_name, String video_path,
			int video_type, int app_show, String bkground_path, String bkground_name)
	{
		String insertSql = "insert into " + DBCONSTANTS.VIDEO_TABLE + 
							" values(null, ? ,?, ?, ?, ?, ?, ?)";
		DBEntity.execSQL(insertSql,
				new Object[]{ resourceID, video_name, video_path,  video_type, 
				              app_show, bkground_path, bkground_name});

		Log.i(LOGTAG,"insert video_name = "+video_name 
												+ "video_path = " + video_path);
	}
	
	
	/*
	 * 
 * 		 + "tv_name varchar(255),"   //tv文件名称(1)
		 + "tv_type integer,"  	//tv 类别	(3)
		 + "tv_show integer DEFAULT 1," //是否显示已下载的tv(4)
		 + "tv_url char(255)"			//
		 + "tv_bkgname char(255)"	//
		 + "tv_bkgpath char(255)"//
	 * 
	 * 
	 * */
	public void insertTVData(String resourceID, String tv_name, int tv_type,
			int tv_show, String tv_url, String tv_bkgname, String tv_bkgpath)
	{
		String insertSql = "insert into " + DBCONSTANTS.TV_TABLE + 
							" values(null,? ,?, ?, ?, ?, ?, ?)";
		DBEntity.execSQL(insertSql,
				new Object[]{ resourceID, tv_name, tv_type, tv_show, 
							  tv_url,   tv_bkgname, tv_bkgpath});

		Log.i(LOGTAG,"insert tv_name = "+tv_name 
												+ "tv_bkgname = " + tv_bkgname);
	}
	
	/*
	 * 	 + "pic_name varchar(255),"   //pic文件名称(1)
			 + "pic_path varchar(255)," 	// pic路径(2)
			 + "pic_type varchar(100),"  	//pic 类别	(3)
			 + "resource_ID char(255),"     //对应的资源号（5）
	 * 
	 * */
	
	public void insertPICData(String resourceID, String pic_name, String pic_path,
			int pic_type, String relateID)
	{
		String insertSql = "insert into " + DBCONSTANTS.PIC_TABLE + 
							" values(null,?,?,?,?,?)";
		DBEntity.execSQL(insertSql,
				new Object[]{resourceID, pic_name,pic_path, pic_type, relateID});

		Log.i(LOGTAG,"insert pic_name = " + pic_name 
							+ "pic_path = " + pic_path
							+ "resourceID = " + resourceID
							+ "pic_type = " + pic_type
							+ "relateID = " + relateID);
		
		System.out.println("begin to update the related ResourceID");
		
		ContentValues values = new ContentValues();
		
		values.put("relate_pic_id", resourceID);
		values.put("bkground_path", pic_path);
		values.put("bkground_name", pic_name);	
		
		DBEntity.update(DBCONSTANTS.APP_TABLE, values, " where resource_id = ?", 
				new String[]{relateID});
		
		showAPPs();
	}
	
	public void showAPPs(){
		/*
		 *  "apk_id integer primary key autoincrement,"//字段(0)
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
			 */
		String[] args = new String[]{"resource_id" , "apk_name","apk_type" , "app_show","apk_label" , 
				"pkg_name",
				"bkground_path" , "bkground_name","relate_pic_id" , "used_time","last_used" };
		Cursor cursor = DBEntity.rawQuery("select * from " + DBCONSTANTS.APP_TABLE, null);
		while(cursor.moveToNext()){
			System.out.print("APP INFO:");
			for(int i = 0; i < args.length; i++){
				if(i != 2 && i != 3){
					System.out.print("  " + args[i] + cursor.getString(i + 1));
				}else{
					System.out.print("  " + args[i] + cursor.getInt(i + 1));
				}
			}
				
			System.out.println();
		}
		
		cursor.close();
	}
	
	/*
	 * 根据pkg_name查询APK的背景图片路径(路径 + 名称)
	 * 
	 * */
	
	public String queryBkground(String pkg_name){
		String querySQL = "select * from " + DBCONSTANTS.APP_TABLE + " where pkg_name = ?";
		String bkgPath = null;
		Cursor cursor= DBEntity.rawQuery(querySQL, new String[]{pkg_name});
		if(cursor == null || cursor.getCount() == 0){
			cursor.close();
			return null;
		}
		cursor.moveToNext();
		if(cursor.getString(6).length() > 0 && cursor.getString(7).length()>0)
			bkgPath = cursor.getString(6) + "/" + cursor.getString(7);
		cursor.close();
		
		return bkgPath;
	}
	
	/*
	 * 根据pkg_name查询APP的使用次数
	 * 
	 * */
	public int queryUSEDTime(String pkg_name){
		String querySQL = "select * from " + DBCONSTANTS.APP_TABLE + " where pkg_name = ?";
		Cursor cursor = DBEntity.rawQuery(querySQL, new String[]{pkg_name});
		if(cursor == null || cursor.getCount() == 0)
			return -1;
		cursor.moveToNext();
		int times =  cursor.getInt(8);
		cursor.close();
		return times;
	}
	
	public void closeDB(){
		if(DBHelper != null)
			DBHelper.close();
	}

}