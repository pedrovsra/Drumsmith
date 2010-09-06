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

    /**
     * @param x
     * @param y
     * @param z
     */
    public GameObject(double x, double y, double z, Color color)
    {
        drawData = new DrawData();
        
        this.x = x;
        this.y = y;
        this.z = z;

        drawData.setPosition(x, y, z);

        drawData.setColor(color);
    }

    /**
     * Method called to do logic operations.
     * @param deltaTime Delta time.
     */
    public abstract void think(double deltaTime);

    public DrawData getDrawData()
    {
        return drawData;
    }

    /**
     * @return
     */
    public double getX()
    {
        return x;
    }

    /**
     * @return 
     */
    public double getY()
    {
        return y;
    }
}
