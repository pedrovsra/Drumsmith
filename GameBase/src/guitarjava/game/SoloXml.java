package guitarjava.game;

/**
 * Contains the data fields of a solo.
 * @author brunojadami
 */
public class SoloXml
{
    private float time;
    private float duration;

    /**
     * @param time Time that it appears in the music.
     * @param duration Duration.
     */
    public SoloXml(float time, float duration)
    {
        this.time = time;
        this.duration = duration;
    }

    /**
     * @return The time.
     */
    public float getTime()
    {
        return time;
    }

    /**
     * @return The duration.
     */
    public float getDuration()
    {
        return duration;
    }
}
