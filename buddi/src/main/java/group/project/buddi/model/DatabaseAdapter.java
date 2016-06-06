package group.project.buddi.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import group.project.buddi.helper.DatabaseHelper;

/**
 * Created by Ahmed on 2016-06-04.
 */
public class DatabaseAdapter {


    public SQLiteDatabase db;
    private final Context m_context;
    private DatabaseHelper m_dbHelper = null;


    public DatabaseAdapter(Context context) {
        m_context = context;
        if (m_dbHelper == null) {
            m_dbHelper = new DatabaseHelper(context);
        }
    }

    public void open() throws SQLException {
        db = m_dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    // CRUD OPERATIONS

    public void insertDog(Dog dog) {

        int id = dog.getID();
        String ref = dog.getReferenceNum();
        String name = dog.getName();
        int age = dog.getAge();
        String imageURL = dog.getImageURL();

        ContentValues cVal = new ContentValues();
        cVal.put(DogEntry.COLUMN_NAME_DOG_ID, id);
        cVal.put(DogEntry.COLUMN_NAME_REF, ref);
        cVal.put(DogEntry.COLUMN_NAME_NAME, name);
        cVal.put(DogEntry.COLUMN_NAME_AGE, age);
        cVal.put(DogEntry.COLUMN_NAME_IMAGE, imageURL);

        // Insert user values in database
        db.insert(DogEntry.TABLE_NAME, null, cVal);

    }

    public List<Dog> getAllDogs() {

        List<Dog> dogList = new ArrayList<Dog>();
        String queryString = "SELECT * FROM " + DogEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                Dog dog = new Dog();
                dog.setID(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_DOG_ID) ));
                dog.setReferenceNum(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_REF) ));
                dog.setName(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_NAME) ));
                dog.setAge(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_AGE) ));
                dog.setImageURL(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_IMAGE) ));

                // Adding dog to list
                dogList.add(dog);

            } while (cursor.moveToNext());
        }

        return dogList;

    }


    public Dog getDog(int id) {

        Dog dog = new Dog();
        String queryString = "SELECT * FROM " + DogEntry.TABLE_NAME + " WHERE dogID=" + String.valueOf(id);

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            dog.setID(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_DOG_ID) ));
            dog.setReferenceNum(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_REF) ));
            dog.setName(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_NAME) ));
            dog.setAge(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_AGE) ));
            dog.setImageURL(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_IMAGE) ));
        }

        return dog;
    }
}
