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
 *
 * @author brunojadami
 */
public class Graphics2DContext extends Applet implements GraphicsInterface, Runnable
{

    private Image dbImage;
    private Graphics dbg;
    private List listeners = new ArrayList();

    public Graphics2DContext()
    {
    }

    public void draw(DrawData data)
    {
    }

    public void addGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.add(listener);
    }

    public void removeGraphicsUpdateEventListener(GraphicsUpdateListener listener)
    {
        listeners.remove(listener);
    }

    private synchronized void fireGraphicsUpdateEvent()
    {
        GraphicsUpdateEvent event = new GraphicsUpdateEvent(this);
        Iterator i = listeners.iterator();
        while (i.hasNext())
        {
            ((GraphicsUpdateListener) i.next()).handleUpdateEvent(event);
        }
    }

    @Override
    public void init()
    {
        setLayout(null);
        setSize(640, 480);
        Frame frame = new Frame("Testing...");
        frame.add(this);
        frame.pack();// make the frame the size of the Applet
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() // Enable the close button just to be nice
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

    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

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

    @Override
    public void paint(Graphics g)
    {

    }

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
