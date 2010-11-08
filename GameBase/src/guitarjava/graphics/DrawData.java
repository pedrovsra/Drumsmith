package guitarjava.graphics;

import java.awt.Color;

/**
 * Contains all simple informations about the drawing.
 * @author lucasjadami
 */
public class DrawData
{

    protected static final int DRAW_2D_RECT = 0;
    protected static final int DRAW_2D_FILLED_RECT = 1;
    protected static final int DRAW_2D_CIRCLE = 2;
    protected static final int DRAW_2D_FILLED_CIRCLE = 3;
    protected static final int DRAW_3D_CLIPPED_SPHERE = 4;
    protected static final int DRAW_3D_SPHERE = 5;
    protected static final int DRAW_2D_TEXT = 6;
    protected float x;
    protected float y;
    protected float z;
    protected int cacheId;
    protected float width;
    protected float height;
    protected float depth;
    protected int type;
    protected Color color;
    protected float colorMul = 1;
    protected String text;

    /**
     * Constructor. Use the id to cache this data, or make it less than 1
     * to disable the cache. It will cache if the Graphics context supports caching.
     * @param cacheId the id of the data
     */
    public DrawData(int cacheId)
    {
        color = Color.WHITE;
        this.cacheId = cacheId;
    }

    /**
     * Create the Data as a 2D circle.
     * @param radius the circle radius
     */
    public void createAs2DCircle(float radius)
    {
        this.width = radius;
        type = DRAW_2D_CIRCLE;
    }

    /**
     * Create the Data as a 2D filled circle.
     * @param radius the circle radius
     */
    public void createAs2DFilledCircle(float radius)
    {
        this.width = radius;
        type = DRAW_2D_FILLED_CIRCLE;
    }

    /**
     * Create the Data base as a 2D rect.
     * @param width the box width
     * @param height the box height
     */
    public void createAs2DRect(float width, float height)
    {
        this.width = width;
        this.height = height;
        type = DRAW_2D_RECT;
    }

    /**
     * Create the Data base as filled 2D rect.
     * @param width the box width
     * @param height the box height
     * @param depth the box depth
     */
    public void createAs2DFilledRect(float width, float height)
    {
        this.width = width;
        this.height = height;
        type = DRAW_2D_FILLED_RECT;
    }

    /**
     * Create the Data base as a 3D clipped sphere.
     * @param radius the radius
     * @param clipZ the z coordinate to clip
     */
    public void createAs3DClippedSphere(float radius, float clipZ)
    {
        this.width = radius;
        this.height = clipZ;
        type = DRAW_3D_CLIPPED_SPHERE;
    }

    /**
     * Create the Data base as a 3D sphere.
     * @param radius the radius
     */
    public void createAs3DSphere(float radius)
    {
        this.width = radius;
        type = DRAW_3D_SPHERE;
    }

    /**
     * Create as a 2d text.
     */
    public void createAs2DText(String text)
    {
        this.text = text;
        type = DRAW_2D_TEXT;
    }

    /**
     * Sets the color.
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * Sets the color multiplier.
     */
    public void setColorMul(float mul)
    {
        colorMul = mul;
    }

    /**
     * Set the position.
     */
    public void setPosition(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the x
     */
    public float getX()
    {
        return x;
    }

    /**
     * @return the y
     */
    public float getY()
    {
        return y;
    }

    /**
     * @return the z
     */
    public float getZ()
    {
        return z;
    }

    /**
     *  Gets the color.
     */
    public Color getColor()
    {
        return color;
    }
}
