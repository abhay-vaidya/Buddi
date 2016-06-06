package group.project.buddi.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Ahmed on 2016-06-04.
 */
public class Dog {

    private int m_id;
    private String m_reference_num;
    private String m_name;
    private int m_age;
    private String m_imageURL;
    private Size m_size;
    private Gender m_gender;
    private String m_breed;
    private String m_color;
    private boolean m_declawed;
    private boolean m_neutered;
    private Location m_location;
    private String m_intakeDate;
    private DogFactors m_factors;
    private String m_desc;


    public Dog() {

    }

    public Dog(String ref, String name, int age, String breed, String image) {
        m_reference_num = ref;
        m_name = name;
        m_age = age;
        m_breed = breed;
        m_imageURL = image;
    }

    public Dog(String ref, String name, int age, Size size, Gender gender, String breed, String color, boolean declawed, boolean neutered, Location location, String intakeDate, DogFactors factors, String desc) {
        m_reference_num = ref;
        m_name = name;
        m_age = age;
        m_size = size;
        m_gender = gender;
        m_breed = breed;
        m_color = color;
        m_declawed = declawed;
        m_neutered = neutered;
        m_location = location;
        m_intakeDate = intakeDate;
        m_factors = factors;
        m_desc = desc;
    }

    public Dog(String ref, String name, Size size, Gender gender, String breed, String color, boolean declawed, boolean neutered, Location location, String intakeDate, DogFactors factors, String desc) {
        m_reference_num = ref;
        m_name = name;
        m_size = size;
        m_gender = gender;
        m_breed = breed;
        m_color = color;
        m_declawed = declawed;
        m_neutered = neutered;
        m_location = location;
        m_intakeDate = intakeDate;
        m_factors = factors;
        m_desc = desc;
    }

    // Getters and Setters
    public int getID() { return m_id; }

    public void setID(int id) { m_id = id; }

    public String getReferenceNum() {
        return m_reference_num;
    }

    public void setReferenceNum(String reference_num) {
        m_reference_num = reference_num;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public int getAge() {
        return m_age;
    }

    public void setAge(int age) {
        m_age = age;
    }

    public Size getSize() {
        return m_size;
    }

    public void setSize(Size size) {
        m_size = size;
    }

    public Gender getGender() {
        return m_gender;
    }

    public void setGender(Gender gender) {
        m_gender = gender;
    }

    public String getBreed() {
        return m_breed;
    }

    public void setBreed(String breed) {
        m_breed = breed;
    }

    public String getColor() {
        return m_color;
    }

    public void setColor(String color) {
        m_color = color;
    }

    public boolean isDeclawed() {
        return m_declawed;
    }

    public void setDeclawed(boolean declawed) {
        m_declawed = declawed;
    }

    public boolean isNeutered() {
        return m_neutered;
    }

    public void setNeutered(boolean neutered) {
        m_neutered = neutered;
    }

    public Location getLocation() {
        return m_location;
    }

    public void setLocation(Location location) {
        m_location = location;
    }

    public String getIntakeDate() {
        return m_intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        m_intakeDate = intakeDate;
    }

    public DogFactors getFactors() {
        return m_factors;
    }

    public void setFactors(DogFactors factors) {
        m_factors = factors;
    }

    public String getDesc() {
        return m_desc;
    }

    public void setDesc(String desc) {
        m_desc = desc;
    }

    public String getImageURL() { return m_imageURL; }

    public void setImageURL(String image) { m_imageURL = image; }

    public String toString() {
        return "Dog [reference_num=" + m_reference_num + " name=" + m_name + " breed=" + m_breed + " age=" + m_age + "]";
    }

}
