package group.project.buddi.model;

/**
 * Describes the comparable factors of a Dog.
 * @author Umar Ahmed
 * @version 1.0
 */
public class DogFactors {

    private int m_noise;
    private int m_activity;
    private int m_friendliness;
    private int m_training;
    private int m_health;

    /**
     * Constructor
     * @param noise noise level (0-9)
     * @param activity activity level (0-9)
     * @param friend friendliness (0-9)
     * @param training  training (0-9)
     * @param health health (0-9)
     */
    public DogFactors(int noise, int activity, int friend, int training, int health) {
        m_noise = noise;
        m_activity = activity;
        m_friendliness = friend;
        m_training = training;
        m_health = health;
    }

    /**
     * Constructor
     * @param factors dog factors in order (noise, activity, friendliness, training, health)
     * @throws Exception if array is not length 5
     */
    public DogFactors(int[] factors) throws Exception {
        if (factors.length == 5) {
            m_noise = factors[0];
            m_activity = factors[1];
            m_friendliness = factors[2];
            m_training = factors[3];
            m_health = factors[4];
        } else {
            throw new Exception("Invalid array length.");
        }
    }
}
