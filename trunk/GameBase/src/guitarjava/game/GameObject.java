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
    protected int x;
    protected int y;
    protected int z;
    protected double deltaTimeRest;

    /**
     * @param x
     * @param y
     * @param z
     */
    public GameObject(int x, int y, int z, Color color)
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
    public int getX()
    {
        return x;
    }

    /**
     * @return 
     */
    public int getY()
    {
        return y;
    }
}
