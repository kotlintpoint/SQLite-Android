# SQLite in Android
CRUD Operation with Sqlite in Android

SQLite is an Open Source database. SQLite supports standard relational database features like SQL syntax, transactions and prepared statements.

SQLite is embedded into every Android device. Using an SQLite database in Android does not require a setup procedure or administration of the database.

SQLiteDatabase is the base class for working with a SQLite database in Android and provides methods to open, query, update and close the database. More specifically SQLiteDatabase provides the insert() , update() and delete() methods.


**Insert into Table**
```
ContentValues values=new ContentValues();
values.put(COLUMN_NAME, contact.getName());
values.put(COLUMN_NUMBER, contact.getNumber());
long id=db.insert(TABLE_CONTACT, null, values);

```

**Update Table**
```
ContentValues values=new ContentValues();
values.put(COLUMN_NAME, contact.getName());
values.put(COLUMN_NUMBER, contact.getNumber());
int count=db.update(TABLE_CONTACT, values, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
```

**Delete Table**
```
int count=db.delete(TABLE_CONTACT, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
```

**Select Data**
```
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
.......
private Contact cursorToContact(Cursor cursor) {
		Contact contact=new Contact();
		contact.setId((int)cursor.getLong( cursor.getColumnIndexOrThrow(COLUMN_ID)));
		contact.setName(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NAME)));
		contact.setNumber(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_NUMBER)));
		return contact;
}
		
```

![screen_shots](https://cloud.githubusercontent.com/assets/20207324/17171486/88518f6a-540e-11e6-9170-1b66a22002bf.png)

![screen_shots](https://cloud.githubusercontent.com/assets/20207324/17171492/8d424514-540e-11e6-81af-b1cd88027b57.png)


