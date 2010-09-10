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
    }

    /**
     * Initializing the context.
     */
    public void init(Window component)
    {
        // Starting
        if (component != null)
        {
            component.addKeyListener(this);
        }
        else
        {
            ErrorWindow error = new ErrorWindow(new RuntimeException("Could not find any Graphic context to add Input listeners."), null);
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
    public void stop()
    {
        thread.interrupt();
    }

    /**
     * Adds an Input event listener.
     * @param listener the listener
     */
    public void addInputEventListener(InputListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes an Input event listener.
     * @param listener the listener
     */
    public void removeInputEventListener(InputListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * A key was typed, from the KeyListener implementation.
     * @param e the KeyEvent
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * A key was pressed, from the KeyListener implementation.
     * @param e the KeyEvent
     */
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
    public void joystickAxisChanged(Joystick j)
    {
    }

    /**
     * Button changed event.
     * @param j the event object
     */
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
                joystick = null;
            }
            catch (Throwable ex)
            {
                joystick = null;
            }
        }
    }

    /**
     * Run method for pooling and finding a joystick.
     */
    public void run()
    {
        boolean quit = false; // Quit flag
        Thread pollThread = null; // Thread to poll
        PollRunner pollRunner = null; // Poll runner
        prepareJoystick();
        while (!quit)
        {
            try
            {
                // Joystick poll
                if (joystick != null)
                {
                    if (pollThread == null || pollRunner.done)
                    {
                        pollRunner = new PollRunner(joystick);
                        pollThread = new Thread(pollRunner);
                        pollThread.start();
                    }
                    else
                    {
                        pollThread.interrupt();
                        quit = true;
                        ErrorWindow error = new ErrorWindow(
                                new RuntimeException("Joystick removed, it may cause library leakages, please restart the app."), null);
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

    private Joystick joystick; // Joystick object
    protected boolean done; // Done polling

    public PollRunner(Joystick joystick)
    {
        this.joystick = joystick;
        done = false;
    }

    /**
     * Run method.
     */
    public void run()
    {
        joystick.poll();
        done = true;
    }
}
