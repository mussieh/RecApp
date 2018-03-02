package com.mussieh.recapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.ProgressBar;

import com.mussieh.recapp.App;
import com.mussieh.recapp.RecappActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;
    private String databasePath;
    private Context mContext;


    /**
     * Constructor for the BookListOpenHelper
     * @param context the RecappActivity context
     */
    public BookListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        databasePath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        Log.d(TAG, "DATABASE_PATH" + databasePath);
    }

    @Override
    public synchronized void close() {
        super.close();
        if (mReadableDB != null) {
            mReadableDB.close();
        }
    }

    /**
     * Creates the SQLite database if it does not already exist
     */
    public void createDatabase() {
        boolean databaseExists = checkIfDatabaseExists();

        if (databaseExists) {
            openDatabase();
        }
        else {
            try {
                copyDatabase();
                openDatabase();
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    /**
     * Opens the SQLite database connection
     */
    private void openDatabase() {
        if (mReadableDB != null && mReadableDB.isOpen()) {
            return;
        }
        try {
            mReadableDB = SQLiteDatabase.openDatabase(databasePath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.d(TAG, "Database Opening Exception" + e.getMessage());
        }
    }

    /**
     * Copies the prebuilt database into the databases folder
     */
    public void copyDatabase() {
        try {
            InputStream myInput = mContext.getAssets().open("databases/"+DATABASE_NAME);
            OutputStream myOutput = new FileOutputStream(databasePath);

            byte[] buffer = new byte[1024];
            int length;
            while ( (length = myInput.read(buffer)) > 0 ) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     *
     * @return the truth value of the database existence
     */
    private boolean checkIfDatabaseExists() {
        SQLiteDatabase testDB = null;
        File dbFile = mContext.getDatabasePath(DATABASE_NAME);

        if (dbFile.exists()) {
            try {
                testDB = SQLiteDatabase.openDatabase(databasePath, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteException e) {
                Log.d(TAG, e.getMessage());
            } finally {
                testDB.close();
            }
            if (testDB != null) {return true;}
        }

        return false;
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
     * Query the readable SQLite database for book items
     * based on the given 'queryString'
     * @param queryString the string to search for
     * @return the list of BookItem objects
     */
    public List<BookItem> queryBooks(String queryString) {
        String query = "SELECT  * FROM " + BOOK_LIST_TABLE + " WHERE " + KEY_SUBJECT_NAME +
                "=" + queryString + " ORDER BY " + KEY_RANK + " ASC;";

        Cursor cursor = null;
        BookItem bookEntry;
        List<BookItem> books = new ArrayList<>();

        try {
            cursor = mReadableDB.rawQuery(query, null);

            while (!cursor.isAfterLast()) {
                cursor.moveToNext();
                bookEntry = new BookItem(); // new object needed for each unique book entry
                bookEntry.setIsbn(cursor.getString(cursor.getColumnIndex(KEY_ISBN)));
                bookEntry.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                bookEntry.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
                bookEntry.setBookType(cursor.getString(cursor.getColumnIndex(KEY_BOOK_TYPE)));
                bookEntry.setRank(cursor.getInt(cursor.getColumnIndex(KEY_RANK)));
                bookEntry.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                bookEntry.setPictureLocation(cursor.getString(cursor.getColumnIndex(KEY_PICTURE_LOCATION)));
                bookEntry.setSubjectName(cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
                books.add(bookEntry);
            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION!" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return books;
        }
    }
}
