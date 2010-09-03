package guitarjava.graphics;

import java.applet.Applet;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implements the GraphicsInterface. This is a 2D implementation.
 * @author brunojadami
 */
public class Graphics2DContext extends Applet implements GraphicsInterface, Runnable
{

    private Image dbImage; // Double buffering image
    private Graphics dbg; // Double buffering graphics
    private List listeners = new ArrayList(); // Listeners for the graphics update

    /**
     * Constructor.
     */
    public Graphics2DContext()
    {
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
        setSize(640, 480);
        Frame frame = new Frame("Testing...");
        frame.add(this);
        frame.pack(); // Make the frame the size of the Applet
        frame.setVisible(true);
        // Enable the close button just to be nice
        frame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
                System.exit(0);
            }
        });
        start();
    }

    /**
     * Starts the context, automatically called on the init method.
     */
    @Override
    public void start()
    {
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
        dbg.setColor(getBackground());
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

    /**
     * The Runnable run implementation, a loop to update the graphics.
     */
    public void run()
    {
        while (true)
        {
            // Repaint the applet
            repaint();
            try
            {
                // Stop thread for 1 milliseconds
                Thread.sleep(1);
            }
            catch (InterruptedException ex)
            {
                // Do nothing
            }
        }
    }
}
