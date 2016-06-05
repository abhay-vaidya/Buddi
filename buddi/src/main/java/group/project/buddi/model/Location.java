package group.project.buddi.model;

/**
 * Created by Ahmed on 2016-06-04.
 */
public class Location {

    private String m_name;
    private String m_telephone_num;
    private double m_latitude;
    private double m_longitude;

    public Location(String name, String phone, double latitude, double longitude) {
        m_name = name;
        m_telephone_num = phone;
        m_latitude = latitude;
        m_longitude = longitude;
    }
}
