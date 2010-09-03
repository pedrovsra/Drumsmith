package guitarjava.main;

import guitarjava.graphics.DrawData;
import guitarjava.graphics.Graphics2DContext;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputContext;
import guitarjava.input.InputInterface;
import guitarjava.timing.TimingContext;
import guitarjava.timing.TimingInterface;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;

/**
 *
 * @author brunojadami
 */
public class Main
{
    /** Program's main entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args)
    {
        final DrawData ddata = new DrawData();
        final GraphicsInterface graphics = new Graphics2DContext(33);
        final InputInterface input = new InputContext();
        final TimingInterface timing = new TimingContext();

        // Initializing
        graphics.init();
        input.init();
        timing.init();
        ddata.createAsSphere(60, 60);
        ddata.setColor(Color.yellow);

        graphics.addGraphicsUpdateEventListener(new GraphicsUpdateListener()
        {
            public void graphicsUpdateEvent(EventObject e)
            {
                graphics.draw(ddata);
            }
        });

        /*graphics.addGraphicsUpdateEventListener(new GraphicsUpdateListener()
        {
            public void graphicsUpdateEvent(EventObject e)
            {
                System.out.println(timing.getDeltaTime());
            }
        });*/

        // Faking an Applet
        Frame frame = new Frame("Testing...");
        frame.add((Component) graphics);
        frame.pack(); // Make the frame the size of the Applet
        frame.setVisible(true);
        // Enable the close button just to be nice
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                graphics.stop();
                input.stop();
                timing.stop();
                System.exit(0);
            }
        });
        ((Graphics2DContext)graphics).requestFocus();
    }
    
}
