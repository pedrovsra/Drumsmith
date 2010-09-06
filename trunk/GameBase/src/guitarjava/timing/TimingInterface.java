package guitarjava.timing;

import java.awt.Window;

/**
 * The timing interface context.
 * @author lucasjadami
 */
public interface TimingInterface
{
    /**
     * Initialize the Timing context.
     * @param component the component to add listeners like close operations
     */
    public void init(Window component);
    /**
     * Stops the Timing context.
     */
    public void stop();
    /**
     * Gets the delta time.
     * @return the delta time in milliseconds
     */
    public double getDeltaTime();
}
