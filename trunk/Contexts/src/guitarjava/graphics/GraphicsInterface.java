package guitarjava.graphics;

/**
 * The graphics context interface.
 * @author lucasjadami
 */
public interface GraphicsInterface
{
    public void init();
    public void draw(DrawData data);
    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener);
    public void removeGraphicsUpdateEventListener(GraphicsUpdateListener listener);
}
