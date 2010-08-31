package guitarjava.main;

import guitarjava.util.Time;
import javax.swing.JApplet;

/**
 * Main class of this project.
 * @author lucasjadami
 */
public class Main extends JApplet implements Runnable
{
    /**
     * The init method is the 'contructor' of the applet application.
     */
    @Override
    public void init()
    {
        
    }

    /**
     * Main thread method.
     */
    public void run()
    {
        while (true)
        {
            Time.waitSomeTime(1);
        }
    }
}
