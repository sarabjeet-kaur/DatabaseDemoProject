package com.example.databasedemoproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.databasedemoproject.model.DataModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.databasedemoproject.utility.AppConstants.DATABASE_NAME;
import static com.example.databasedemoproject.utility.AppConstants.DATABASE_VERSION;
import static com.example.databasedemoproject.utility.AppConstants.KET_DATE;
import static com.example.databasedemoproject.utility.AppConstants.KET_TIME;
import static com.example.databasedemoproject.utility.AppConstants.KET_TYPE;
import static com.example.databasedemoproject.utility.AppConstants.KEY_DESCRIPTION;
import static com.example.databasedemoproject.utility.AppConstants.KEY_ID;
import static com.example.databasedemoproject.utility.AppConstants.KEY_TITLE;
import static com.example.databasedemoproject.utility.AppConstants.TABLE_EVENT;

/**
 * Created by sarabjjeet on 9/8/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_EVENT = " CREATE TABLE " + TABLE_EVENT +
                " (" + KEY_ID + " INTEGER PRIMARY KEY," + KET_DATE + " TEXT," + KET_TIME +
                " TEXT," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT," + KET_TYPE + " TEXT" +" )";

        db.execSQL(CREATE_TABLE_EVENT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);

        // Create tables again
        onCreate(db);
    }


    // Adding new event
    public void addData(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KET_DATE, dataModel.getDate());
        values.put(KET_TIME, dataModel.getTime());
        values.put(KEY_TITLE, dataModel.getTitle());
        values.put(KEY_DESCRIPTION, dataModel.getDescription());
        values.put(KET_TYPE, dataModel.getType());


        // Inserting Row
        db.insert(TABLE_EVENT, null, values);
        db.close(); // Closing database connection
    }


    public List<DataModel> getAllDataWithDateType(String date, String type) {
        List<DataModel> dataList = new ArrayList<DataModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_EVENT +
                " where " + KET_DATE + " = ? AND  " + KET_TYPE + " = ? ", new String[] { date, type });

        if (cursor != null)
            cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataModel dataModel = new DataModel();
                 dataModel.setId(Integer.parseInt(cursor.getString(0)));
                dataModel.setDate(cursor.getString(1));
                dataModel.setTime(cursor.getString(2));
                dataModel.setTitle(cursor.getString(3));
                dataModel.setDescription(cursor.getString(4));
                dataModel.setType(cursor.getString(5));

                // Adding event  to list
                dataList.add(dataModel);
            } while (cursor.moveToNext());
        }

        // return event list
        return dataList;
    }

    public List<String> getDate() {
        List<String> dataList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENT, new String[] {KET_DATE}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return event list
        return dataList;
    }
}
