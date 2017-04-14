package com.rashi.find4rent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nithin on 11/4/17.
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final String TAG = DBHelper.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_HOUSE = "house";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHOTO_ID = "photoid";
    private static final String KEY_Rent = "rent";
    private static final String KEY_PLACE = "place";

    private static DBHelper sInstance;


    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_HOUSE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_PLACE + " TEXT, " +
                KEY_PHOTO_ID + " INTEGER, " + KEY_Rent + " INTEGER )";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addHouse(House house) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, house.name);
            values.put(KEY_PHOTO_ID, house.photoId);
            values.put(KEY_PLACE, house.place);
            values.put(KEY_Rent, house.rent);

            // Inserting Row
            long id = db.insertOrThrow(TABLE_HOUSE, null, values);
            db.setTransactionSuccessful(); // Closing database connection

            Log.d(TAG, "New house inserted into sqlite" + id);
        } catch (Exception e)
        {
            Log.d(TAG, "Error inserting into sqlite");
        } finally {
            db.endTransaction();
        }
    }

    public List<House> getAllHouses()
    {
        List<House> houses = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_HOUSE );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    House house = new House();
                    house.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    house.rent = cursor.getInt(cursor.getColumnIndex(KEY_Rent));
                    house.place = cursor.getString(cursor.getColumnIndex(KEY_PLACE));
                    house.photoId = cursor.getInt(cursor.getColumnIndex(KEY_PHOTO_ID));

                    houses.add(house);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get houses from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return houses;
    }

    public List<House> searchHouses(String place)
    {
        place = place.toLowerCase();

        List<House> houses = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s LIKE '%s' ",
                        TABLE_HOUSE, KEY_PLACE, place );

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    House house = new House();
                    house.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    house.rent = cursor.getInt(cursor.getColumnIndex(KEY_Rent));
                    house.place = cursor.getString(cursor.getColumnIndex(KEY_PLACE));
                    house.photoId = cursor.getInt(cursor.getColumnIndex(KEY_PHOTO_ID));

                    houses.add(house);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get houses from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return houses;
    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_HOUSE, null, null);
    }

    public void insertDummy()
    {
        removeAll();
        List<House> houses;
        houses = new ArrayList<>();
        houses.add(new House("ram villa", "anderi", 1200, R.drawable.h1));
        houses.add(new House("krishna", "mulund", 1400, R.drawable.h2));
        houses.add(new House("namul house", "malad", 4200, R.drawable.h3));
        houses.add(new House("xyz house", "mulund", 1500,R.drawable.h3));
        houses.add(new House("rass house", "powai", 4200, R.drawable.h3));

        for (House house: houses) {
            addHouse(house);
        }
    }

}
