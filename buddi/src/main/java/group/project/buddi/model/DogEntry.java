package group.project.buddi.model;

import android.provider.BaseColumns;

/**
 * Class that defines the dog table contents
 * @author Team Buddi
 * @version 1.0
 */
public class DogEntry implements BaseColumns {

    public static final String TABLE_NAME = "dogs";

    public static final String COLUMN_NAME_DOG_ID = "dogID";
    public static final String COLUMN_NAME_REF = "reference_num";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_AGE = "age";
    public static final String COLUMN_NAME_BREED = "breed";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_COLOR = "color";
    public static final String COLUMN_NAME_GENDER = "gender";
    public static final String COLUMN_NAME_BLACKLIST = "blacklist";

}
