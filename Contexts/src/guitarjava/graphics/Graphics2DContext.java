package guitarjava.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Implements the GraphicsInterface. This is a 2D implementation.
 * @author brunojadami
 */
public class Graphics2DContext extends JFrame implements GraphicsInterface
{
    static final public int GRAPHICS_WIDTH = 500; // Width
    static final public int GRAPHICS_HEIGHT = 600; // Height

    private Graphics dbg; // Double buffering graphics
    private List listeners; // Listeners for the graphics update
    private long updateRate; // The update rate;
    private Timer timer; // Timer to update the graphics
    private BufferStrategy buffer; // Buffer image

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
            dbg.setColor(data.color);
            if (data.type == DrawData.DRAW_BOX)
                dbg.fillRect((int)data.x, (int)data.y, (int)data.width, (int)data.height);
            else if (data.type == DrawData.DRAW_SPHERE)
                dbg.fillArc((int)data.x, (int)data.y, (int)data.width, (int)data.height, 0, 360);
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
    public void init(Window component)
    {
        // Starting
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);
        setLayout(null);
        setSize(GRAPHICS_WIDTH, GRAPHICS_HEIGHT);
        // Scheduling to repeatdly call repaint at the FPS rate
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                // Repaint the applet
                update();
            }
        }, 0, updateRate);
        // Adding close operation
        component.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
        // Others
        setVisible(true);
        requestFocus();
        createBufferStrategy(2);
    }

    /**
     * Stops the context.
     */
    public void stop()
    {
        timer.cancel();
    }

    /**
     * Updates the context.
     */
    private void update()
    {
        if (buffer == null)
        {
            buffer = getBufferStrategy();
        }
        else
        {
            dbg = buffer.getDrawGraphics();
            // Clear screen in background
            dbg.setColor(Color.BLACK);
            dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
            // Draw elements in background
            dbg.setColor(getForeground());
            fireGraphicsUpdateEvent();
            // Draw buffer on the screen
            if (!buffer.contentsLost())
                buffer.show();
        }
    }
}
