package com.gmall.Utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SystemDbConnection extends SQLiteOpenHelper{
	private final static int DATABASE_VERSION = 1;// 数据库版本号
	private final static String DATABASE_NAME = "gmallSystem.db";// 数据库名

	private static Context context;

	public static void setContext(Context context) {
		SystemDbConnection.context = context;
	}

	public SystemDbConnection() {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		String sql;
		try {
			sql = "CREATE TABLE users(" + "id Integer PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT NOT NULL unique,"
					+ "password	TEXT NOT NULL,"
					+ "phone TEXT NOT NULL,"
					+ "gender TEXT NOT NULL,"
					+ "school TEXT NOT NULL,"
					+ "headPhotoPath TEXT NOT NULL)";
			db.execSQL(sql);
			sql=  "CREATE TABLE goods(" + "id Integer PRIMARY KEY AUTOINCREMENT,"
					+ "name	TEXT NOT NULL,"
					+ "money double NOT NULL,"
					+ "typeName TEXT NOT NULL,"
					+ "contact TEXT NOT NULL,"
					+ "photoPath TEXT NOT NULL,"
					+ "userId integer NOT NULL,"
					+ "releaseTime datetime default(datetime(CURRENT_TIMESTAMP,'localtime')\r\n" + 
					") NOT NULL,"
					+ "information TEXT NOT NULL)";
			db.execSQL(sql);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	} 

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public SQLiteDatabase getConnection() {
		SQLiteDatabase db=null;
		try {
			db = getWritableDatabase();
			//InputStream is=context.getApplicationContext().getAssets().open("economy.db");
			
		}catch(Exception e) {
			
		}
		return db;
	}

	public void close(SQLiteDatabase db) {
		db.close();
	}

}
