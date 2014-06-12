/*
 * 
 * 接下来需要实现resolver端
 * 
 * 主要是进行查询，根据不同的类型界面查询
 * 
 * 每个显示包括：name/position/intent/bkgpic*/

package org.androidpn.laumanager;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class DBProvider extends ContentProvider {
	public static final String LOGTAG = "DBProvider";
	LauncherDBHelper dbHelp;
	SQLiteDatabase db;
	
	private static final UriMatcher URI_MATCHER ;

	static{
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.APP_TABLE, DBCONSTANTS.APKS);
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.APP_TABLE + "/#", DBCONSTANTS.APK);
		
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.VIDEO_TABLE, DBCONSTANTS.VIDEOS);
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.VIDEO_TABLE + "/#", DBCONSTANTS.VIDEO);
		
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.TV_TABLE, DBCONSTANTS.TVS);
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.TV_TABLE + "/#", DBCONSTANTS.TV);
		
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.PIC_TABLE, DBCONSTANTS.PICS);
		URI_MATCHER.addURI(DBCONSTANTS.AUTHORITY, DBCONSTANTS.PIC_TABLE + "/#", DBCONSTANTS.PIC);
	}
	@Override
	public boolean onCreate() {
		System.out.println("======in the body of contentprovider's onCreate=======");
		dbHelp =  new LauncherDBHelper(getContext(), DBCONSTANTS.DB_Name);
		return true;
	}
	
	/*
	 * public String getType(Uri uri)：该方法用于返回当前Url所代表数据的MIME类型。
	 * 
	 * 	如果操作的数据属于集合类型，那么MIME类型字符串应该以vnd.android.cursor.dir/开头，
	 * 	
	 * 	例如：要得到所有person记录的Uri为content://com.ljq.provider.personprovider/person，
	 * 	那么返回的MIME类型字符串应该为："vnd.android.cursor.dir/person"。
	 * 	
	 * 	如果要操作的数据属于非集合类型数据，那么MIME类型字符串应该以vnd.android.cursor.item/开头，
	 * 	
	 * 	例如：得到id为10的person记录，Uri为content://com.ljq.provider.personprovider/person/10，
	 * 	那么返回的MIME类型字符串为："vnd.android.cursor.item/person"
	 * 
	 * */

	@Override
	public String getType(Uri uri) {
		int match = URI_MATCHER.match(uri);
		
		switch(match){
		case DBCONSTANTS.APKS:
			return DBCONSTANTS.APP_TYPE;
		case DBCONSTANTS.APK:
			return DBCONSTANTS.APP_ITEM_TYPE;
		case DBCONSTANTS.VIDEOS:
			return DBCONSTANTS.VIDEO_TYPE;
		case DBCONSTANTS.VIDEO:
			return DBCONSTANTS.VIDEO_ITEM_TYPE;
		case DBCONSTANTS.TVS:
			return DBCONSTANTS.TV_TYPE;
		case DBCONSTANTS.TV:
			return DBCONSTANTS.TV_ITEM_TYPE;
		case DBCONSTANTS.PICS:
			return DBCONSTANTS.PIC_TYPE;
		case DBCONSTANTS.PIC:
			return DBCONSTANTS.PIC_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("UnknownURI" + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Log.d(LOGTAG, "begin to query the info");
		db = dbHelp.getReadableDatabase();
		long _id = 0;
		int match = URI_MATCHER.match(uri); 
		switch(match){
		case DBCONSTANTS.APKS:
			break;
		case DBCONSTANTS.APK:
			_id = ContentUris.parseId(uri);
			selection = " apk_id = ?";
			selectionArgs = new String[]{String.valueOf(_id)};
			break;
		case DBCONSTANTS.VIDEOS:
			break;
		case DBCONSTANTS.VIDEO:
			_id = ContentUris.parseId(uri);
			selection = " video_id = ?";
			selectionArgs = new String[]{String.valueOf(_id)};
			break;
		case DBCONSTANTS.TVS:
			break;
		case DBCONSTANTS.TV:
			_id = ContentUris.parseId(uri);
			selection = " tv_id = ?";
			selectionArgs = new String[]{String.valueOf(_id)};
			break;
		case DBCONSTANTS.PICS:
			break;
		case DBCONSTANTS.PIC:
			_id = ContentUris.parseId(uri);
			selection = " pic_id = ?";
			selectionArgs = new String[]{String.valueOf(_id)};
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);  
		}
		
		Log.d(LOGTAG, "begin to exec the command");
		return db.query(DBCONSTANTS.TABLES[match / 2], projection, selection, 
				        selectionArgs, null, null, sortOrder);
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int match = URI_MATCHER.match(uri); 
		
		if(match < 1 || match > 8){
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}
		db = dbHelp.getWritableDatabase();
		long rowId;

		rowId = db.insert(DBCONSTANTS.TABLES[match / 2], null, values);
		if(rowId > 0){
			System.out.println("===========in the body of notify================");
			notifyDataChanged();
			
			getContext().getContentResolver().notifyChange(uri, null);
			return ContentUris.withAppendedId(uri, rowId);
			
			
		}
		return null;
	}

	private void notifyDataChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		db = dbHelp.getWritableDatabase();
		int match = URI_MATCHER.match(uri); 
//	    switch (match) {  
//        case APP_ALL:  
//            
//            break;  
//        case APP_ONE:  
//            long _id = ContentUris.parseId(uri);  
//            selection = "_id = ?";  
//            selectionArgs = new String[]{String.valueOf(_id)};  
//            break;  
//        default:  
//            throw new IllegalArgumentException("Unknown URI: " + uri);  
//        }  
		if(match < 1 || match > 8)
			 throw new IllegalArgumentException("Unknown URI: " + uri); 
		
        int count = db.update(DBCONSTANTS.TABLES[match / 2], values, selection, selectionArgs);  
        if (count > 0) {  
            //getContext().getContentResolver().notifyDataChanged();  
        	getContext().getContentResolver().notifyChange(uri, null);
        }  
        return count;  
	}
	


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
//		db = dbHelp.getWritableDatabase();
//		int match = URI_MATCHER.match(uri); 
//		
//	    switch (match) {  
//        case APP_ALL:  
//            //doesn't need any code in my provider.  
//            break;  
//        case APP_ONE:  
//            long _id = ContentUris.parseId(uri);  
//            selection = "_id = ?";  
//            selectionArgs = new String[]{String.valueOf(_id)};  
//            break;  
//        default:  
//            throw new IllegalArgumentException("Unknown URI: " + uri);  
//        }  
//        int count = db.delete(DBCONSTANTS.DB_Name, selection, selectionArgs);  
//        if (count > 0) {  
//            notifyDataChanged();  
//        }  
//        return count;  
		
		return 0;
	}


}

