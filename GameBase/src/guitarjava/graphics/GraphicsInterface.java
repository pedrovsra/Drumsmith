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
     * Setting camera, used only for 3D contexts.
     * @param fx camera x position
     * @param fy camera y position
     * @param fz camera z position
     * @param tx looking at x pos
     * @param ty looking at y pos
     * @param tz looking at z pos
     */
    public void setCamera(float fx, float fy, float fz, float tx, float ty, float tz);

    /**
     * Setting light position. It will shine to the origin.
     */
    public void setLightPos(float[] lightPos);

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

    /**
     * Sets the light.
     * @param ambient the ambient light
     * @param diffuse the diffuse light
     */
    public void setLight(float[] ambient, float[] diffuse);

    /**
     * Sets the clear color.
     */
    public void setClearColor(float[] clearColor);
}
