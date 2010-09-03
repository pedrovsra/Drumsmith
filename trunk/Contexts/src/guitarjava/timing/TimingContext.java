package guitarjava.timing;

/**
 * A Timing context.
 * @author brunojadami
 */
public class TimingContext implements TimingInterface
{
    private long last;
    /**
     * Constructor.
     */
    public TimingContext()
    {

    }

    /**
     * Initialize the Timing context.
     */
    public void init()
    {
        last = System.nanoTime();
    }

    /**
     * Gets the delta time and updates the current time. Should be called
     * once on update event.
     * @return the delta time in milliseconds
     */
    public double getDeltaTime()
    {
        long now = System.nanoTime();
        double ret = (now - last);
        ret /= 1000000;
        last = now;
        return ret;
    }

}
