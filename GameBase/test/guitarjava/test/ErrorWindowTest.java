package guitarjava.test;

import guitarjava.gui.GameWindow;
import guitarjava.gui.*;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucas
 */
public class ErrorWindowTest
{
    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testShowWindow() throws Exception
    {
        JFrame f = new JFrame("JOW");
        ErrorWindow errorWindow = new ErrorWindow(new RuntimeException("Test message."), f, null);
        errorWindow.showWindow();
    }
}