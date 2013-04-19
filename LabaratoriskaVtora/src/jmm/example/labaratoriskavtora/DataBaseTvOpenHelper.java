package jmm.example.labaratoriskavtora;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseTvOpenHelper extends SQLiteOpenHelper {
	
	
//	public static final String COLUMN_NAME = "name";
//	public static final String COLUMN_DONE = "done";
//	public static final String COLUMN_DUE_DATE = "due_date";
	
	public static final String TABLE_NAME = "Television";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME_TITLE = "title";
	public static final String COLUMN_NAME_PRICE = "subtitle";
	

	public static final String COLUMN_DUE_DATE = "due_date"; //  ???????????????????
	


	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME= "DatabaseTelevision.db";

	private static final String DATABASE_CREATE = 
			String.format("create table %s (%s integer primary key autoincrement, " + "%s text not null, %s text not null, %s datetime not null);",
	                       TABLE_NAME, COLUMN_ID, COLUMN_NAME_TITLE, COLUMN_NAME_PRICE, COLUMN_DUE_DATE );

	
	//constructor
	public DataBaseTvOpenHelper(Context context, String lang) 
	{
		super(context, DATABASE_NAME, null , DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) 
	{		
		arg0.execSQL(DATABASE_CREATE);
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{		
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
		onCreate(db);

	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

	
}
