/**
 * 
 */
package com.example.ankitsodha.myapplication4;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * @author ADMIN
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION=1;
	public static final String DATABASE_NAME="contact_manager";
	public static final String TABLE_CONTACT="contact";
	public static final String COLUMN_ID="id";
	public static final String COLUMN_NAME="name";
	public static final String COLUMN_NUMBER="number";
	

	public static final String CREATE_TABLE_CONTACT="CREATE TABLE "+TABLE_CONTACT+" ("
		        +COLUMN_ID+" INTEGER PRIMARY KEY autoincrement, "
		        +COLUMN_NAME+" TEXT, "
		        +COLUMN_NUMBER+" TEXT)";

	public static final String DELETE_CONTACTS =
	        "DROP TABLE IF EXISTS " + TABLE_CONTACT;
	
	SQLiteDatabase db;
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db=this.getWritableDatabase();
    }
	
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_CONTACT);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL(DELETE_CONTACTS);
		onCreate(db);
	}
	
	
	public long InsertContact(Contact contact)
	{
		ContentValues values=new ContentValues();
		values.put(COLUMN_NAME, contact.get_name());
		values.put(COLUMN_NUMBER, contact.get_number());
		long id=db.insert(TABLE_CONTACT, null, values);
		return id;
	}
	
	public long UpdateContact(int id, Contact contact)
	{
		ContentValues values=new ContentValues();
		values.put(COLUMN_NAME, contact.get_name());
		values.put(COLUMN_NUMBER, contact.get_number());
		long updatedId=db.update(TABLE_CONTACT, values, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return updatedId;		
	}

	public long deleteContact(int id)
	{
		long deletedID=db.delete(TABLE_CONTACT, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return deletedID;
	}
	
	public Contact SelectContactByID(int id)
	{
		//DatabaseHandler handler=new DatabaseHandler(context);
		SQLiteDatabase db=this.getReadableDatabase();
		String[] projection={COLUMN_ID,COLUMN_NAME,COLUMN_NUMBER};
		
		
		Cursor cursor = db.query(
			    TABLE_CONTACT,  			// The table to query
			    projection,                               // The columns to return
			    COLUMN_ID+"="+id,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                 // The sort order
			    );
		if(cursor.moveToFirst())
		{
			Contact contact=new Contact();
			contact.set_id((int)cursor.getLong( cursor.getColumnIndexOrThrow(COLUMN_ID)));
			contact.set_name(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NAME)));
			contact.set_number(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NUMBER)));

			return contact;
		}
		return null;
	}
	
	public ArrayList<Contact> SelectAllContacts()
	{
		//DatabaseHandler handler=new DatabaseHandler(context);
		SQLiteDatabase db=this.getReadableDatabase();
		String[] projection={COLUMN_ID,COLUMN_NAME,COLUMN_NUMBER};
		
		
		Cursor cursor = db.query(
			    TABLE_CONTACT,  			// The table to query
			    projection,                               // The columns to return
			    null,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                 // The sort order
			    );
		ArrayList<Contact> contactList=new ArrayList<Contact>();
		Contact contact;
		
		if(cursor.moveToFirst())
		{
			do{
				contact=new Contact();
				contact.set_id((int)cursor.getLong( cursor.getColumnIndexOrThrow(COLUMN_ID)));
				contact.set_name(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NAME)));
				contact.set_number(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NUMBER)));
	
				contactList.add(contact);
			}while(cursor.moveToNext());
		}
		return contactList;
	}
}
