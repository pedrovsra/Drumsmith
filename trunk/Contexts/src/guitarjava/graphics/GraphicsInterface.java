package guitarjava.graphics;

/**
 * The Graphics context interface.
 * @author lucasjadami
 */
public interface GraphicsInterface
{
    /**
     * Initialize the Graphics context.
     */
    public void init();
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
