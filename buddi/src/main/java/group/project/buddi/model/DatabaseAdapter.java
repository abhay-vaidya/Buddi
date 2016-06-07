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
        String breed = dog.getBreed();
        String imageURL = dog.getImageURL();
        String color = dog.getColor();
        String gender = dog.getGender();

        ContentValues cVal = new ContentValues();
        cVal.put(DogEntry.COLUMN_NAME_DOG_ID, id);
        cVal.put(DogEntry.COLUMN_NAME_REF, ref);
        cVal.put(DogEntry.COLUMN_NAME_NAME, name);
        cVal.put(DogEntry.COLUMN_NAME_AGE, age);
        cVal.put(DogEntry.COLUMN_NAME_BREED, breed);
        cVal.put(DogEntry.COLUMN_NAME_IMAGE, imageURL);
        cVal.put(DogEntry.COLUMN_NAME_COLOR, color);
        cVal.put(DogEntry.COLUMN_NAME_GENDER, gender);
        cVal.put(DogEntry.COLUMN_NAME_BLACKLIST, 0);

        // Insert user values in database
        db.insert(DogEntry.TABLE_NAME, null, cVal);

    }

    public void updateDog(String column, String value) {

    }

    public void updateDog(String column, int value) {

    }

    public void updateDog(String column, int id, boolean value) {

        if (value) {

            String queryString = "UPDATE " + DogEntry.TABLE_NAME + " SET " + column + "=1 WHERE dogID=" + id + ";";
            db.execSQL(queryString);

        } else {
            String queryString = "UPDATE " + DogEntry.TABLE_NAME + " SET " + column + "=0 WHERE dogID=" + id + ";";
            db.execSQL(queryString);
        }

    }

    public void clearDogs() {

        String queryString = "DELETE FROM " + DogEntry.TABLE_NAME + ";";
        db.execSQL(queryString);

    }

    public List<Dog> getAllDogs() {

        List<Dog> dogList = new ArrayList<Dog>();
        String queryString = "SELECT * FROM " + DogEntry.TABLE_NAME + " WHERE " + DogEntry.COLUMN_NAME_BLACKLIST + "=0;";

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                Dog dog = new Dog();
                dog.setID(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_DOG_ID) ));
                dog.setReferenceNum(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_REF) ));
                dog.setName(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_NAME) ));
                dog.setAge(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_AGE) ));
                dog.setBreed(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_BREED) ));
                dog.setImageURL(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_IMAGE) ));
                dog.setColor(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_COLOR) ));
                dog.setGender(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_GENDER )));

                // Adding dog to list
                dogList.add(dog);

            } while (cursor.moveToNext());
        }

        return dogList;

    }


    public Dog getDog(int id) {

        Dog dog = new Dog();
        String queryString = "SELECT * FROM " + DogEntry.TABLE_NAME + " WHERE dogID=" + String.valueOf(id) + ";";;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            dog.setID(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_DOG_ID) ));
            dog.setReferenceNum(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_REF) ));
            dog.setName(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_NAME) ));
            dog.setAge(cursor.getInt( cursor.getColumnIndex(DogEntry.COLUMN_NAME_AGE) ));
            dog.setBreed(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_BREED) ));
            dog.setImageURL(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_IMAGE) ));
            dog.setColor(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_COLOR )));
            dog.setGender(cursor.getString( cursor.getColumnIndex(DogEntry.COLUMN_NAME_GENDER )));
        }

        return dog;
    }
}
