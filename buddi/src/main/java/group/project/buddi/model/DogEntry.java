package group.project.buddi.model;

import android.provider.BaseColumns;

/**
 * Created by Ahmed on 2016-06-05.
 */
/* Inner class that defines the dog table contents */
public abstract class DogEntry implements BaseColumns {

    public static final String TABLE_NAME = "dogs";

    public static final String COLUMN_NAME_DOG_ID = "dogID";
    public static final String COLUMN_NAME_REF = "reference_num";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_AGE = "age";
    public static final String COLUMN_NAME_BREED = "breed";
    public static final String COLUMN_NAME_IMAGE = "image";

}
