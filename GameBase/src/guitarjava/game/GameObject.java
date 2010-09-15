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
    protected float x;
    protected float y;
    protected float z;
    protected float width;
    protected float height;

    /**
     * @param x Position x.
     * @param y Position y.
     * @param z Position z.
     * @param color Color.
     */
    public GameObject(float x, float y, float z, float width, float height, Color color, int cacheId)
    {
        drawData = new DrawData(cacheId);

        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;

        drawData.setPosition(x, y, z);

        drawData.setColor(color);
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
    public float getX()
    {
        return x;
    }

    /**
     * @return Position y.
     */
    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
}
