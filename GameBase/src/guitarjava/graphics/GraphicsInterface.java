package guitarjava.graphics;

import java.awt.Window;

/**
 * The Graphics context interface.
 * @author lucasjadami
 */
public interface GraphicsInterface
{   
    /**
     * Initialize the Graphics context.
     * @param component the component to add listeners like close operations
     */
    public void init(Window component);
    /**
     * Stops the Graphics context.
     */
    public void stop();
    /**
     * Draws an object to the context.
     * @param data the object drawing data
     */
    public void draw(DrawData data);
    /**
     * Adds a GraphicsUpdate event listener.
     * @param listener
     */
    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener);
    /**
     * Removes a GraphicsUpdate event listener.
     * @param listener
     */
    public void removeGraphicsUpdateEventListener(GraphicsUpdateListener listener);
}
