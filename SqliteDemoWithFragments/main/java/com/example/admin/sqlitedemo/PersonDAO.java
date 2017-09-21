package com.example.admin.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Admin on 9/20/2017.
 */

public class PersonDAO {

    KotlinSqliteOpenHelper openHelper;
    SQLiteDatabase sqLiteDatabase;

    public PersonDAO(Context context)
    {
        openHelper=new KotlinSqliteOpenHelper(context);
        sqLiteDatabase=openHelper.getWritableDatabase();
    }

    public String savePerson(Person person)
    {
        ContentValues values=new ContentValues();
        values.put(KotlinSqliteOpenHelper.COL_FIRST_NAME,person.getFirstName());
        values.put(KotlinSqliteOpenHelper.COL_LAST_NAME,person.getLastName());
        values.put(KotlinSqliteOpenHelper.COL_MOBILE,person.getMobile());
        long id=sqLiteDatabase.insert(KotlinSqliteOpenHelper.TABLE_PERSON,null,values);
        if(id>0)
            return "Success";
        else
            return "Fail";
    }

    public String removePerson(int id)
    {
        String whereClause=KotlinSqliteOpenHelper.COL_ID + " = ?";
        String[] whereArgs={String.valueOf(id)};
        int count=sqLiteDatabase.delete(KotlinSqliteOpenHelper.TABLE_PERSON,whereClause,whereArgs);
        if(id>0)
            return "Success";
        else
            return "Fail";
    }
    public String updatePerson(Person person)
    {
        ContentValues values=new ContentValues();
        values.put(KotlinSqliteOpenHelper.COL_FIRST_NAME,person.getFirstName());
        values.put(KotlinSqliteOpenHelper.COL_LAST_NAME,person.getLastName());
        values.put(KotlinSqliteOpenHelper.COL_MOBILE,person.getMobile());
        String whereClause=KotlinSqliteOpenHelper.COL_ID + " = ?";
        String[] whereArgs={String.valueOf(person.getId())};
        int count=sqLiteDatabase.update(KotlinSqliteOpenHelper.TABLE_PERSON,values,whereClause,whereArgs);
        if(count>0)
            return "Success";
        else
            return "Fail";
    }
    public ArrayList<Person> getAllPerson()
    {
        String[] columns={KotlinSqliteOpenHelper.COL_ID,KotlinSqliteOpenHelper.COL_FIRST_NAME,
                    KotlinSqliteOpenHelper.COL_LAST_NAME,KotlinSqliteOpenHelper.COL_MOBILE};
        String selection=null;
        String[] selectionArgs=null;
        String groupBy=null;
        String having=null;
        String orderBy=null;
        Cursor cursor=sqLiteDatabase.query(KotlinSqliteOpenHelper.TABLE_PERSON,columns,selection,
                selectionArgs,groupBy,having,orderBy);
        ArrayList<Person> persons=new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                persons.add(cursorToPerson(cursor));
            }while (cursor.moveToNext());
        }
        return persons;
    }

    private Person cursorToPerson(Cursor cursor) {
        Person person=new Person();
        person.setId(cursor.getInt(cursor.getColumnIndex(KotlinSqliteOpenHelper.COL_ID)));
        person.setFirstName(cursor.getString(cursor.getColumnIndex(KotlinSqliteOpenHelper.COL_FIRST_NAME)));
        person.setLastName(cursor.getString(cursor.getColumnIndex(KotlinSqliteOpenHelper.COL_LAST_NAME)));
        person.setMobile(cursor.getString(cursor.getColumnIndex(KotlinSqliteOpenHelper.COL_MOBILE)));
        return person;
    }
}
