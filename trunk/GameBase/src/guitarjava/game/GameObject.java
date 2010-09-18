package guitarjava.game;

import guitarjava.graphics.DrawData;
import java.awt.Color;

/**
 * It is an abstract class containing game object data.
 * @author lucasjadami
 */
public abstract class GameObject
{
    protected DrawData drawData;
    protected double x;
    protected double y;
    protected double z;
    protected double width;
    protected double height;

    /**
     * @param x Position x.
     * @param y Position y.
     * @param z Position z.
     * @param width Width.
     * @param height Height.
     * @param color Color.
     * @param cacheId Cache of the object image.
     */
    public GameObject(double x, double y, double z, double width, double height, Color color, int cacheId)
    {
        drawData = new DrawData(cacheId);

        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;

        updateDrawDataPosition();

        drawData.setColor(color);
    }

    /**
     * Updates the draw data position with the current object position.
     */
    public final void updateDrawDataPosition()
    {
        drawData.setPosition((float) x, (float) y, (float) z);
    }

    /**
     * Method called to do logic operations.
     * @param deltaTime Delta time.
     */
    public abstract void think(float deltaTime);

    public DrawData getDrawData()
    {
        return drawData;
    }

    /**
     * @return Position x.
     */
    public double getX()
    {
        return x;
    }

    /**
     * @return Position y.
     */
    public double getY()
    {
        return y;
    }

    /**
     * @param y The new value of y.
     */
    public void setY(double y)
    {
        this.y = y;
    }

    /**
     * @return The width.
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * @return The height.
     */
    public double getHeight()
    {
        return height;
    }
}
