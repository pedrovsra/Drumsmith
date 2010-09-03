package guitarjava.input;

import guitarjava.graphics.Graphics2DContext;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An Input context.
 * @author brunojadami
 */
public class InputContext implements InputInterface, KeyListener
{
    private List listeners; // Listeners for the input event

    /**
     * Constructor.
     */
    public InputContext()
    {
        listeners = new ArrayList();
    }

    /**
     * Initializing the context.
     */
    public void init()
    {
        if (Graphics2DContext.component != null)
        {
            Graphics2DContext.component.addKeyListener(this);
        }
        else
            throw new RuntimeException("Could not find any Graphic context to add Input listeners.");
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
        InputEvent event = new InputEvent(this, e.getKeyCode(), InputEvent.INPUT_PRESSED);
        fireInputEvent(event);
    }

    /**
     * A key was released, from the KeyListener implementation.
     * @param e the KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {
        InputEvent event = new InputEvent(this, e.getKeyCode(), InputEvent.INPUT_RELEASED);
        fireInputEvent(event);
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

}
