package guitarjava.input;

import java.util.EventObject;

/**
 * The Input event.
 * @author brunojadami
 */
public class InputEvent extends EventObject
{
    public static final int INPUT_RELEASED = 0; // Release type
    public static final int INPUT_PRESSED = 1; // Press type

    private int type;
    private int keyCode;

    /**
     * Constructor.
     */
    public InputEvent(Object source, int type, int keyCode)
    {
        super(source);
        this.type = type;
        this.keyCode = keyCode;
    }

    /**
     * Gets the event type.
     * @return the event type
     */
    public int getType()
    {
        return type;
    }

    /**
     * Gets the keyCode.
     * @return the key code
     */
    public int getKeyCode()
    {
        return keyCode;
    }
}