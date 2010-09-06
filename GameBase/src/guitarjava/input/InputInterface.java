package guitarjava.input;

import java.awt.Window;

/**
 * The input interface context.
 * @author lucasjadami
 */
public interface InputInterface
{
    /**
     * Initializes the Input context.
     * @param component the component to add listeners like close operations
     */
    public void init(Window component);
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
