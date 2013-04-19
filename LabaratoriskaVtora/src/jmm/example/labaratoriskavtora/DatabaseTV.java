package jmm.example.labaratoriskavtora;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import jmm.example.labaratoriskavtora.ModelItem;

public class DatabaseTV {

	private SQLiteDatabase database;
	private DataBaseTvOpenHelper dbHelper;
	private String[] allColumns = { DataBaseTvOpenHelper.COLUMN_ID,
									DataBaseTvOpenHelper.COLUMN_NAME_TITLE,
									DataBaseTvOpenHelper.COLUMN_NAME_PRICE,
									DataBaseTvOpenHelper.COLUMN_DUE_DATE };

	public DatabaseTV(Context context, String lang) 
	{
		dbHelper = new DataBaseTvOpenHelper(context, lang);
	}
	
	public void open() throws SQLException 
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close() 
	{
		database.close();
		dbHelper.close();
	}
	
	public boolean insert(ModelItem item) {

		if (item.getId() != null) {
		return update(item);
		}

		long insertId = database.insert(DataBaseTvOpenHelper.TABLE_NAME, null, itemToContentValues(item));
		
		if (insertId > 0) 
		{
			item.setId(insertId);
			return true;
		} 
		else 
		{
			return false;
		}
	
	}
	
	public boolean update(ModelItem item) 
	{
		long numRowsAffected = database.update(DataBaseTvOpenHelper.TABLE_NAME,
												itemToContentValues(item), 
												DataBaseTvOpenHelper.COLUMN_ID + " = "+ item.getId(), 
												null);
		return numRowsAffected > 0;
	}
	
	public List<ModelItem> getAllItems()
	{
		List<ModelItem> items = new ArrayList<ModelItem>();
		
		Cursor cursor = database.query(DataBaseTvOpenHelper.TABLE_NAME, allColumns,
		null, null, null, null, null);
		
		if (cursor.moveToFirst())
		{
			do 
			{
				items.add(cursorToItem(cursor));
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return items;
	}
	
	public ModelItem getById(long id) 
	{
	
		Cursor cursor = database.query(DataBaseTvOpenHelper.TABLE_NAME, allColumns,
				                       DataBaseTvOpenHelper.COLUMN_ID + " = " + id, null, null,	null, null);
		try
		{
			if (cursor.moveToFirst()) 
			{
				return cursorToItem(cursor);
			} else 
			{
				// no items found
				return null;
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
		finally 
		{
			cursor.close();
		}
	
	}
	
	protected ModelItem cursorToItem(Cursor cursor)
	{
		ModelItem item = new ModelItem();
		item.setId(cursor.getLong(cursor.getColumnIndex(DataBaseTvOpenHelper.COLUMN_ID)));
		
		item.setTitle(cursor.getString(cursor.getColumnIndex(DataBaseTvOpenHelper.COLUMN_NAME_TITLE)));
		
		item.setPrice(cursor.getString((cursor.getColumnIndex(DataBaseTvOpenHelper.COLUMN_NAME_PRICE))));
		
		item.setDueDate(new Date(cursor.getLong(cursor.getColumnIndex(DataBaseTvOpenHelper.COLUMN_DUE_DATE))));
		
		return item;
	}
	
	protected ContentValues itemToContentValues(ModelItem item) 
	{
		ContentValues values = new ContentValues();
		if (item.getId() != null) 
		{
			values.put(DataBaseTvOpenHelper.COLUMN_ID, item.getId());
		}
		values.put(DataBaseTvOpenHelper.COLUMN_NAME_TITLE, item.getTitle());
		values.put(DataBaseTvOpenHelper.COLUMN_NAME_PRICE, item.getPrice());
		values.put(DataBaseTvOpenHelper.COLUMN_DUE_DATE, item.getDueDate().getTime());
		return values;
	}
}
