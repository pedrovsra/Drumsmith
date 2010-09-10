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
 * @deprecated this class is not implemented anymore, use the 3D context :D
 * @author brunojadami
 */
public class Graphics2DContext //implements GraphicsInterface
{
    private Graphics dbg; // float buffering graphics
    private List listeners; // Listeners for the graphics update
    private long updateRate; // The update rate;
    private Timer timer; // Timer to update the graphics
    private BufferStrategy buffer; // Buffer image
    private Window window;

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
        /*if (data != null && buffer != null)
        {
            dbg.setColor(data.color);
            if (data.type == DrawData.DRAW_2D_RECT)
                dbg.drawRect((int)data.x - (int)data.width / 2, (int)data.y - (int)data.height / 2,
                        (int)data.width, (int)data.height);
            else if (data.type == DrawData.DRAW_2D_FILLED_RECT)
                dbg.fillRect((int)data.x - (int)data.width / 2, (int)data.y - (int)data.height / 2,
                        (int)data.width, (int)data.height);
            else if (data.type == DrawData.DRAW_NOTE)
                dbg.fillArc((int)data.x - (int)data.width / 2, (int)data.y - (int)data.height / 2,
                        (int)data.width, (int)data.height, 0, 360);
        }*/
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
        window = component;
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
            buffer = window.getBufferStrategy();
            if (buffer == null && window.isVisible())
            {
                window.createBufferStrategy(2);
                buffer = window.getBufferStrategy();
            }
        }
        else
        {
            try
            {
                dbg = buffer.getDrawGraphics();
                // Clear screen in background
                dbg.setColor(Color.BLACK);
                dbg.fillRect(0, 0, window.getSize().width, window.getSize().height);
                // Draw elements in background
                dbg.setColor(window.getForeground());
                fireGraphicsUpdateEvent();
                // Draw buffer on the screen

                if (!buffer.contentsLost())
                    buffer.show();
            }
            catch(Exception ex)
            {
                // Buffer not ready
                buffer = null;
                return;
            }
        }
    }

    /**
     * Do nothing, 2D context dont have a camera.
     */
    public void setCamera(float fx, float fy, float fz, float tx, float ty, float tz)
    {
        
    }
}
