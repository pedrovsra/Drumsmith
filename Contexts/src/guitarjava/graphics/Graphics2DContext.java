package guitarjava.graphics;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implements the GraphicsInterface. This is a 2D implementation.
 * @author brunojadami
 */
public class Graphics2DContext extends Applet implements GraphicsInterface
{
    static public Component component = null; // The component to add listeners
    private Image dbImage; // Double buffering image
    private Graphics dbg; // Double buffering graphics
    private List listeners; // Listeners for the graphics update
    private long updateRate; // The update rate;

    /**
     * Constructor.
     * @param updateRate the update rate, remember that updateRate = 1000/FPS,
     * use milliseconds
     */
    public Graphics2DContext(long updateRate)
    {
        listeners = new ArrayList();
        this.updateRate = updateRate;
    }

    /**
     * Draws the DrawData onto the screen.
     * @param data the data to be draw
     */
    public void draw(DrawData data)
    {
        if (data != null)
        {

        }
    }

    /**
     * Adds an event lister to the GraphicsUpdate event.
     * @param listener the listener
     */
    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes an event lister from the GraphicsUpdate event.
     * @param listener the listener
     */
    public void removeGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Fires the GraphicsUpdate event, calling all the listeners.
     */
    private void fireGraphicsUpdateEvent()
    {
        GraphicsUpdateEvent event = new GraphicsUpdateEvent(this);
        Iterator i = listeners.iterator();
        while (i.hasNext())
        {
            ((GraphicsUpdateListener) i.next()).graphicsUpdateEvent(event);
        }
    }

    /**
     * Initializing the context.
     */
    @Override
    public void init()
    {
        setLayout(null);
        setSize(500, 400);
        component = this;
        start();
    }

    /**
     * Starts the context, automatically called on the init method.
     */
    @Override
    public void start()
    {
        // Scheduling to repeatdly call repaint at the FPS rate
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                // Repaint the applet
                repaint();
            }
        }, 0, updateRate);
    }

    /**
     * Stops the context.
     */
    @Override
    public void stop()
    {
    }

    /**
     * Updates the context.
     * @param g the graphics to update
     */
    @Override
    public void update(Graphics g)
    {
        // Create if not created
        if (dbImage == null)
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        // Clear screen in background
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        // Draw elements in background
        dbg.setColor(getForeground());
        fireGraphicsUpdateEvent();
        paint(dbg);
        // Draw image on the screen
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Painting the context.
     * @param g the graphics to paint
     */
    @Override
    public void paint(Graphics g)
    {

    }
}
