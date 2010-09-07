package guitarjava.main;

import guitarjava.components.ErrorWindow;
import guitarjava.graphics.DrawData;
import guitarjava.graphics.Graphics2DContext;
import guitarjava.graphics.Graphics3DContext;
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
        Thread.setDefaultUncaughtExceptionHandler(new ErrorWindow(null));

        final DrawData datas[] = new DrawData[5];
        final GraphicsInterface graphics = new Graphics3DContext();
        final InputInterface input = new InputContext();
        final TimingInterface timing = new TimingContext();

        // Initializing
        graphics.init((Window) graphics);
        //input.init((Window) graphics);
        timing.init((Window) graphics);
        for (int x = 0; x < datas.length; ++x)
        {
            datas[x] = new DrawData();
            datas[x].createAsHalfSphere(0, 0);
        }

        graphics.addGraphicsUpdateEventListener(new GraphicsUpdateListener()
        {
            double aux = 10;
            public void graphicsUpdateEvent(EventObject e)
            {
                aux -= 0.1;
                for (int x = 0; x < datas.length; ++x)
                {
                    datas[x].setPosition(x - 2, aux, 0);
                    graphics.draw(datas[x]);
                }
                if (aux < -8)
                    aux = 10;
            }
        });
    }
}
