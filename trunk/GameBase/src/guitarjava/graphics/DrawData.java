package guitarjava.graphics;

import java.awt.Color;

/**
 * Contains all simple informations about the drawing.
 * @author lucasjadami
 */
public class DrawData
{
    protected static final int DRAW_BOX = 0;
    protected static final int DRAW_FILLED_BOX = 1;
    protected static final int DRAW_HALF_SPHERE = 2;

    // Variables, protected to give a faster access to Graphics package
    protected double x;
    protected double y;
    protected double z;
    protected double width;
    protected double height;
    protected double depth;
    protected int type;
    protected Color color;

    /**
     * Constructor.
     */
    public DrawData()
    {

    }

    /**
     * Create the Data base as a box.
     * @param width the box width
     * @param height the box height
     * @param depth the box depth
     */
    public void createAsBox(int width, int height, int depth)
    {
        this.width = width;
        this.height = height;
        this.depth = depth;
        type = DRAW_BOX;
    }

    /**
     * Create the Data base as a box.
     * @param width the box width
     * @param height the box height
     * @param depth the box depth
     */
    public void createAsFilledBox(int width, int height, int depth)
    {
        this.width = width;
        this.height = height;
        this.depth = depth;
        type = DRAW_FILLED_BOX;
    }
    
    /**
     * Create the Data base as a sphere.
     * @param width the box width
     * @param height the box height
     */
    public void createAsHalfSphere(int radius, int height)
    {
        this.width = radius;
        this.height = height;
        type = DRAW_HALF_SPHERE;
    }

    /**
     * Sets the color.
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * Set the position.
     */
    public void setPosition(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


}
