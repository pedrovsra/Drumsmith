package guitarjava.graphics;

import java.awt.Color;

/**
 * Contains all simple informations about the drawing.
 * @author lucasjadami
 */
public class DrawData
{
    protected static final int DRAW_2D_RECT = 0;
    protected static final int DRAW_2D_FILL_RECT = 1;
    protected static final int DRAW_NOTE = 2;

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
        color = Color.WHITE;
    }

    /**
     * Create the Data base as a 2D rect.
     * @param width the box width
     * @param height the box height
     * @param depth the box depth
     */
    public void createAs2DRect(double width, double height)
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
    public void createAs2DFillRect(double width, double height)
    {
        this.width = width;
        this.height = height;
        type = DRAW_2D_FILL_RECT;
    }
    
    /**
     * Create the Data base as a note.
     * @param width the box width
     * @param height the box height
     */
    public void createAsNote(double radius)
    {
        this.width = radius;
        type = DRAW_NOTE;
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
