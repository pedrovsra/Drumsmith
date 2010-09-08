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
     * @param color Color.
     */
    public GameObject(double x, double y, double z, double width, double height, Color color)
    {
        drawData = new DrawData();
        
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
    public abstract void think(double deltaTime);

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

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }
}
