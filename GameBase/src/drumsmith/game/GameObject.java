package drumsmith.game;

import drumsmith.graphics.DrawData;
import java.awt.Color;
import java.util.LinkedList;

/**
 * It is an abstract class containing game object data.
 * @author lucasjadami
 */
public abstract class GameObject
{
    protected LinkedList<DrawData> drawDatas;
    protected double x;
    protected double y;
    protected double z;
    protected double width;
    protected double height;
    protected boolean doingSolo;

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
        DrawData drawData = new DrawData(cacheId);
        drawDatas = new LinkedList<DrawData>();

        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;

        updateDrawDataPosition(drawData);

        drawData.setColor(color);
        drawDatas.add(drawData);
    }

    /*
     * Empty contructor.
     */
    public GameObject()
    {
        drawDatas = new LinkedList<DrawData>();
    }

    /**
     * Updates the draw data position with the current object position.
     */
    public final void updateDrawDataPosition(DrawData drawData)
    {
        drawData.setPosition((float) x, (float) y, (float) z);
    }

    /**
     * Method called to do logic operations.
     * @param deltaTime Delta time.
     */
    public abstract void think(float deltaTime);

    public LinkedList<DrawData> getDrawDatas()
    {
        return drawDatas;
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

    /**
     * Set doingSolo.
     */
    public void setDoingSolo(boolean set)
    {
        doingSolo = set;
    }
}
