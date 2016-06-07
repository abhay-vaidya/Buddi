package group.project.buddi.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import group.project.buddi.model.DogEntry;

/**
 * Created by Ahmed on 2016-06-05.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DatabaseAdapter";
    private static final String DATABASE_NAME = "dogs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String UNIQUE = " UNIQUE";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_DOGS =
            "CREATE TABLE " + DogEntry.TABLE_NAME + " (" +
                    DogEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    DogEntry.COLUMN_NAME_DOG_ID + INT_TYPE + UNIQUE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_REF + TEXT_TYPE + UNIQUE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_AGE + INT_TYPE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_BREED + TEXT_TYPE + COMMA_SEP +
                    DogEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
                    ");";
    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + DogEntry.TABLE_NAME;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DOGS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

}
