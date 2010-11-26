package guitarjava.input;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;
import guitarjava.components.ErrorWindow;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Input context. It ignores key repeats.
 * @author brunojadami
 */
public class InputNoRepeatContext implements InputInterface, KeyListener, JoystickListener, Runnable
{

    private static final int POLL_TIME = 20; // Poll time
    private static final int MAX_KEYS = 256;
    private static final int MAX_JKEYS = 32;
    private List listeners; // Listeners for the input event
    private Joystick joystick; // USB Joystick
    private Thread thread; // Thread to pool input
    private int keys[]; // Keyboard keys
    private int jkeys[]; // Joystick keys
    PollRunner pollRunner = null; // Poll runner
    private Window mainWindow; // Main Window

    /**
     * Constructor.
     */
    public InputNoRepeatContext()
    {
        listeners = new ArrayList();
        joystick = null;
        thread = new Thread(this);
        keys = new int[MAX_KEYS];
        jkeys = new int[MAX_JKEYS];
        for (int x = 0; x < MAX_KEYS; ++x)
        {
            keys[x] = -1;
        }
        for (int x = 0; x < MAX_JKEYS; ++x)
        {
            jkeys[x] = 0;
        }
        pollRunner = new PollRunner();
    }

    /**
     * Initializing the context.
     */
    @Override
    public void init(Window component)
    {
        this.mainWindow = component;
        // Starting
        if (component != null)
        {
            component.addKeyListener(this);
        }
        else
        {
            ErrorWindow error = new ErrorWindow(new RuntimeException("Could not find any Graphic context to add Input listeners."),
                    component, "http://www.google.com");
            error.showWindow();
        }
        // Adding close listener
        component.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent e)
            {
                stop();
            }
        });
        // Start thread
        thread.start();
    }

    /**
     * Stops the context.
     */
    @Override
    public void stop()
    {
        thread.interrupt();
        pollRunner.stop();
    }

    /**
     * Adds an Input event listener.
     * @param listener the listener
     */
    @Override
    public void addInputEventListener(InputListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes an Input event listener.
     * @param listener the listener
     */
    @Override
    public void removeInputEventListener(InputListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * A key was typed, from the KeyListener implementation.
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * A key was pressed, from the KeyListener implementation.
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < MAX_KEYS)
        {
            if (keys[e.getKeyCode()] == -1)
            {
                InputEvent event = new InputEvent(this, InputEvent.INPUT_KEYBOARD_PRESSED, e.getKeyCode());
                fireInputEvent(event);
            }
            keys[e.getKeyCode()] = 2;
        }
    }

    /**
     * A key was released, from the KeyListener implementation.
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < MAX_KEYS)
        {
            keys[e.getKeyCode()] = 1;
        }
    }

    /**
     * Fires the Input event, calling all the listeners.
     */
    private void fireInputEvent(InputEvent event)
    {
        Iterator i = listeners.iterator();
        while (i.hasNext())
        {
            ((InputListener) i.next()).inputEvent(event);
        }
    }

    /**
     * Axis changed event.
     * @param j the event object
     */
    @Override
    public void joystickAxisChanged(Joystick j)
    {
    }

    /**
     * Button changed event.
     * @param j the event object
     */
    @Override
    public void joystickButtonChanged(Joystick j)
    {
        int b = j.getButtons();
        for (int x = 0; x < MAX_JKEYS; ++x)
        {
            int v = b >> x & 0x1;
            if (v == 1 && jkeys[x] == 0)
            {
                InputEvent event = new InputEvent(this, InputEvent.INPUT_JOYSTICK_PRESSED, x);
                fireInputEvent(event);
            }
            else if (v == 0 && jkeys[x] == 1)
            {
                InputEvent event = new InputEvent(this, InputEvent.INPUT_JOYSTICK_RELEASED, x);
                fireInputEvent(event);
            }
            jkeys[x] = v;
        }

    }

    /**
     * Preparing joystick.
     */
    private void prepareJoystick()
    {
        if (joystick == null)
        {
            try
            {
                joystick = Joystick.createInstance();
                if (joystick != null)
                {
                    joystick.setPollInterval(POLL_TIME);
                    joystick.addJoystickListener(this);
                }
            }
            catch (IOException ex)
            {
                System.out.println("No joystick plugged in...");
                joystick = null;
            }
            catch (Throwable ex)
            {
                System.out.println("Could not create joystick instance! Probably its a lib error.");
                joystick = null;
            }
        }
    }

    /**
     * Run method for pooling and finding a joystick.
     */
    @Override
    public void run()
    {
        boolean quit = false; // Quit flag
        prepareJoystick();
        while (!quit)
        {
            try
            {
                // Joystick poll
                if (joystick != null)
                {
                    if (pollRunner.joystick == null)
                    {
                        pollRunner.joystick = joystick;
                        pollRunner.start();
                    }
                    else if (pollRunner.getDoRun() == false)
                    {
                        pollRunner.setDoRun(true);
                    }
                    else
                    {
                        //pollRunner.stop();
                        quit = true;
                        ErrorWindow error = new ErrorWindow(
                                new RuntimeException("Joystick removed, it may cause library leakages, please restart the app."),
                                mainWindow, "http://www.google.com");
                        error.showWindow();
                    }
                }
                // Keyboard poll
                for (int x = 0; x < MAX_KEYS; ++x)
                {
                    if (keys[x] > -1 && keys[x] < 2)
                    {
                        --keys[x];
                        if (keys[x] == -1)
                        {
                            InputEvent event = new InputEvent(this, InputEvent.INPUT_KEYBOARD_RELEASED, x);
                            fireInputEvent(event);
                        }
                    }

                }
                // Sleep
                Thread.sleep(POLL_TIME);
            }
            catch (InterruptedException ex)
            {
            }
        }
    }
}

/**
 * Adding a fix to the poll block bug.
 * @author brunojadami
 */
class PollRunner implements Runnable
{
    private boolean doRun = false;
    protected Joystick joystick; // Joystick object
    private Thread thread; // The thread

    public PollRunner()
    {
        
    }

    /**
     * Starts polling.
     */
    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops polling.
     */
    public void stop()
    {
        if (thread != null)
            thread.interrupt();
    }

    /**
     * Gets if needs to run.
     */
    public synchronized boolean getDoRun()
    {
        return doRun;
    }

    /**
     * Sets if needs to run.
     */
    public synchronized void setDoRun(boolean val)
    {
        doRun = val;
        if (doRun)
        {
            synchronized(thread)
            {
                    thread.notify();
            }
        }
    }

    /**
     * Run method.
     */
    @Override
    public void run()
    {
        while (true)
        {
            joystick.poll();
            setDoRun(false);
            try
            {
                synchronized(thread)
                {
                    thread.wait();
                }
            }
            catch (InterruptedException ex)
            {
            }
        }
    }
}
