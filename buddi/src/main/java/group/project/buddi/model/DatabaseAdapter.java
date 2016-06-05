package group.project.buddi.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 2016-06-04.
 */
public class DatabaseAdapter {

    private static final String LOG_TAG = "DatabaseAdapter";
    private static final String DATABASE_NAME = "dogs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_DOGS =
            "CREATE TABLE " + DogEntry.TABLE_NAME + " (" +
                    DogEntry._ID + " INTEGER PRIMARY KEY," +
                    DogEntry.COLUMN_NAME_REF + TEXT_TYPE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_AGE + INT_TYPE + COMMA_SEP +
                    ")";
    private static final String DELETE_DOGS =
            "DROP TABLE IF EXISTS " + DogEntry.TABLE_NAME;
    private static final String KEY_USER_NAME = "user_name";


    private static DatabaseHelper DBHelper = null;


    public DatabaseAdapter() {

    }

    public void init(Context context) {
        if (DBHelper == null) {
            DBHelper = new DatabaseHelper(context);
        }
    }


    /* Inner class that defines the dog table contents */
    public static abstract class DogEntry implements BaseColumns {
        public static final String TABLE_NAME = "dogs";

        public static final String COLUMN_NAME_REF = "reference_num";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";

    }


    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }

    // Inner class for SQLite database access
    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_DOGS);
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception onCreate for DatabaseHelper");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(DELETE_DOGS);
                onCreate(db);
            } catch (Exception e) {
                Log.d(LOG_TAG, "Exception onUpgrade for DatabaseHelper");
            }
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }



    // CRUD OPERATIONS

    public static void addDog(Dog data) {

        // Open database for Read / Write

        final SQLiteDatabase db = open();

        String ref = data.getReference_num();
        String name = data.getName();
        int age = data.getAge();
        ContentValues cVal = new ContentValues();
        cVal.put(KEY_USER_NAME, ref);
        cVal.put(KEY_USER_NAME, name);
        cVal.put(KEY_USER_NAME, age);
        // Insert user values in database
        db.insert(DogEntry.TABLE_NAME, null, cVal);
        db.close(); // Closing database connection
    }

    public static List<Dog> getAllDogs() {

        List<Dog> dogList = new ArrayList<Dog>();
        String queryString = "SELECT * FROM " + DogEntry.TABLE_NAME;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                Dog data = new Dog();
                data.setReferenceNum(cursor.getString(1));
                data.setName(cursor.getString(2));
                data.setAge( Integer.valueOf(cursor.getString(3)) );

                // Adding dog to list
                dogList.add(data);

            } while (cursor.moveToNext());
        }

        return dogList;

    }



}
