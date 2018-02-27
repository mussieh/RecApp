package com.mussieh.recapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mussieh.recapp.App;
import com.mussieh.recapp.RecappActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mussie on 2/25/2018.
 *
 */

public class BookListOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = BookListOpenHelper.class.getSimpleName();

    // Database Initialization Information
    private static final int DATABASE_VERSION = 1;
    private static final String BOOK_LIST_TABLE = "book_entries";
    private static final String DATABASE_NAME = "recapplist.db";
    private static final String DATABASE_LOCATION = "/data/data/com.mussieh.recapp/databases/";

    // Column Names
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_BOOK_TYPE = "book_type";
    public static final String KEY_RANK = "rank";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PICTURE_LOCATION = "picture_location";
    public static final String KEY_SUBJECT_NAME = "subject_name";

    private static final String[] COLUMNS = {KEY_ISBN, KEY_TITLE, KEY_AUTHOR, KEY_BOOK_TYPE,
            KEY_RANK, KEY_DESCRIPTION, KEY_PICTURE_LOCATION, KEY_SUBJECT_NAME};

    private static final String BOOK_LIST_TABLE_CREATE = "CREATE TABLE " + BOOK_LIST_TABLE + " (" +
            KEY_ISBN + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT NOT NULL UNIQUE, " +
            KEY_AUTHOR + " TEXT NOT NULL UNIQUE, " + KEY_BOOK_TYPE + " TEXT NOT NULL UNIQUE, " +
            KEY_RANK + " INTEGER NOT NULL, " + KEY_DESCRIPTION + " TEXT NOT NULL, " +
            KEY_PICTURE_LOCATION + " TEXT NOT NULL, " + KEY_SUBJECT_NAME + " TEXT NOT NULL );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    public BookListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        openDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Database onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // SAVE USER DATA FIRST!!!
        // todo: specify old and new database versions
//        Log.w(BookListOpenHelper.class.getName(),
//                "Upgrading database from version " + oldVersion + " to "
//                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_LIST_TABLE);
        onCreate(db);
    }

    /**
     * Open the SQLite database connection
     */
    private void openDatabase() {
        String dbPath = "/data/user/0/com.mussieh.recapp/databases/" + DATABASE_NAME;
        Log.d(TAG, dbPath);
        if (mReadableDB != null && mReadableDB.isOpen()) {
            return;
        }
        try {
            mReadableDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.d(TAG, "Database Opening Exception" + e.getMessage());
        }
    }

    /**
     * Close the SQLite database connection
     */
    public void closeDatabase() {

        try {
            if (mReadableDB != null) {
                mReadableDB.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "Database Closing Exception" + e.getMessage());
        } finally {
            mReadableDB.close();
        }
    }

//    private void fillDatabaseWithData(SQLiteDatabase db) {
//        BookItem[] Books;
//        ContentValues values = new ContentValues();
//        String dummyData = "dummy";
//        int counter = 0;
//
//        for (String column: COLUMNS) {
//            if (column == "rank" || column == "isbn") {
//                values.put(column, counter++);
//            }
//            else {
//                values.put(column, dummyData);
//            }
//        }
//
//        db.insert(BOOK_LIST_TABLE, null, values);
//    }

    /**
     * Query the readable SQLite database for book items
     * based on the given 'queryString'
     * @param queryString the string to search for
     * @return the list of BookItem objects
     */
    public List<BookItem> queryBooks(String queryString) {
        String query = "SELECT  * FROM " + BOOK_LIST_TABLE + " WHERE " + KEY_SUBJECT_NAME +
                "=" + queryString + " ORDER BY " + KEY_RANK + " ASC;";

        Cursor cursor = null;
        BookItem entry = new BookItem();
        List<BookItem> books = new ArrayList<>();

        try {
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setIsbn(cursor.getInt(cursor.getColumnIndex(KEY_ISBN)));
            entry.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            entry.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
            entry.setBookType(cursor.getString(cursor.getColumnIndex(KEY_BOOK_TYPE)));
            entry.setRank(cursor.getInt(cursor.getColumnIndex(KEY_RANK)));
            entry.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            entry.setPictureLocation(cursor.getString(cursor.getColumnIndex(KEY_PICTURE_LOCATION)));
            entry.setSubjectName(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));

            for (int i = 0; i < 5; i++) {
                books.add(entry);
            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION!" + e.getMessage());
        } finally {
            cursor.close();
            return books;
        }
    }
}
