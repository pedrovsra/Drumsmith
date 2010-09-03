package guitarjava.input;

/**
 * The input interface context.
 * @author lucasjadami
 */
public interface InputInterface
{
    /**
     * Initializes the Input context.
     */
    public void init();
    /**
     * Stops the Input context.
     */
    public void stop();
    /**
     * Adds an Input event listener.
     * @param listener the listener
     */
    public void addInputEventListener(InputListener listener);
    /**
     * Removes an Input event listener.
     * @param listener the listener
     */
    public void removeInputEventListener(InputListener listener);
}
