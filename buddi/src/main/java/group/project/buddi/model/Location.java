package group.project.buddi.model;

/**
 * Describes a shelter location.
 * @author Team Buddi
 * @version 1.0
 */
public class Location {

    private String m_name;
    private String m_telephone_num;
    private double m_latitude;
    private double m_longitude;

    /**
     * Constructor
     * @param name name of the shelter
     * @param phone phone number of the shelter
     * @param latitude latitude of the shelter
     * @param longitude longitude of the shelter
     */
    public Location(String name, String phone, double latitude, double longitude) {
        m_name = name;
        m_telephone_num = phone;
        m_latitude = latitude;
        m_longitude = longitude;
    }
}
