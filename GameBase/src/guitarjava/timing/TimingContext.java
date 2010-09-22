package guitarjava.timing;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A Timing context.
 * @author brunojadami
 */
public class TimingContext implements TimingInterface
{

    private long last;

    /**
     * Constructor.
     */
    public TimingContext()
    {
    }

    /**
     * Initialize the Timing context.
     */
    public void init(Window component)
    {
        // Adding close listener
        component.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
        last = System.nanoTime();
    }

    /**
     * Stops the context.
     */
    public void stop()
    {
    }

    /**
     * Gets the delta time and updates the current time. Should be called
     * once on update event.
     * @return the delta time in milliseconds
     */
    public double getDeltaTime()
    {
        long now = System.nanoTime();
        double ret = (now - last);
        ret /= 1000000;
        last = now;
        return ret;
    }
}
