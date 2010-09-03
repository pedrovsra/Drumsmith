package guitarjava.timing;

/**
 * The timing interface context.
 * @author lucasjadami
 */
public interface TimingInterface
{
    /**
     * Initialize the Timing context.
     */
    public void init();
    /**
     * Gets the delta time.
     * @return the delta time in milliseconds
     */
    public double getDeltaTime();
}
