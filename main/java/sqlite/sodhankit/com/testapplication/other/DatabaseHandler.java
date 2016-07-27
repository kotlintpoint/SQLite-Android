package sqlite.sodhankit.com.testapplication.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import sqlite.sodhankit.com.testapplication.model.Contact;

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
		values.put(COLUMN_NAME, contact.getName());
		values.put(COLUMN_NUMBER, contact.getNumber());
		long id=db.insert(TABLE_CONTACT, null, values);
		return id;
	}
	
	public int UpdateContact(int id, Contact contact)
	{
		ContentValues values=new ContentValues();
		values.put(COLUMN_NAME, contact.getName());
		values.put(COLUMN_NUMBER, contact.getNumber());
		int count=db.update(TABLE_CONTACT, values, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return count;
	}

	public int deleteContact(int id)
	{
		int count=db.delete(TABLE_CONTACT, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return count;
	}
	
	public Contact SelectContactByID(int id)
	{
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
			return cursorToContact(cursor);
		}
		return null;
	}
	
	public ArrayList<Contact> SelectAllContacts()
	{

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

		if(cursor.moveToFirst())
		{
			do{
				contactList.add(cursorToContact(cursor));
			}while(cursor.moveToNext());
		}
		return contactList;
	}

	private Contact cursorToContact(Cursor cursor) {
		Contact contact=new Contact();
		contact.setId((int)cursor.getLong( cursor.getColumnIndexOrThrow(COLUMN_ID)));
		contact.setName(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NAME)));
		contact.setNumber(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NUMBER)));
		return contact;
	}
}
