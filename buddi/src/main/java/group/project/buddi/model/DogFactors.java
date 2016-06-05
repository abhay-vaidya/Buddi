package group.project.buddi.model;

/**
 * Created by Ahmed on 2016-06-04.
 */
public class DogFactors {

    private int m_noise;
    private int m_activity;
    private int m_friendliness;
    private int m_training;
    private int m_health;

    public DogFactors(int noise, int activity, int friend, int training, int health) {
        m_noise = noise;
        m_activity = activity;
        m_friendliness = friend;
        m_training = training;
        m_health = health;
    }

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
