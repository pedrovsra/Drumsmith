package guitarjava.test;

import guitarjava.components.*;
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
        GameWindow gameWindow = new GameWindow(800, 600);
        ErrorWindow errorWindow = new ErrorWindow(new RuntimeException("Test message."), gameWindow, null);
        errorWindow.showWindow();
    }
}