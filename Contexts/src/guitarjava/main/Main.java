package guitarjava.main;

import guitarjava.components.ErrorWindow;
import guitarjava.graphics.DrawData;
import guitarjava.graphics.Graphics2DContext;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputContext;
import guitarjava.input.InputInterface;
import guitarjava.timing.TimingContext;
import guitarjava.timing.TimingInterface;
import java.awt.Color;
import java.awt.Window;
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
        Thread.setDefaultUncaughtExceptionHandler(new ErrorWindow("http://www.google.com"));

        final DrawData ddata = new DrawData();
        final GraphicsInterface graphics = new Graphics2DContext(33);
        final InputInterface input = new InputContext();
        final TimingInterface timing = new TimingContext();

        /*graphics.addGraphicsUpdateEventListener(new GraphicsUpdateListener()
        {
        public void graphicsUpdateEvent(EventObject e)
        {
            System.out.println(timing.getDeltaTime());
        }
        });*/

        // Initializing
        graphics.init((Window) graphics);
        input.init((Window) graphics);
        timing.init((Window) graphics);
        ddata.createAsSphere(60, 60);
        ddata.setColor(Color.yellow);

        graphics.addGraphicsUpdateEventListener(new GraphicsUpdateListener()
        {

            public void graphicsUpdateEvent(EventObject e)
            {
                graphics.draw(ddata);
            }
        });
    }
}
