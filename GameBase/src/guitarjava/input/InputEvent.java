package guitarjava.input;

import java.util.EventObject;

/**
 * The Input event.
 * @author brunojadami
 */
public class InputEvent extends EventObject
{
    public static final int INPUT_KEYBOARD_RELEASED = 0; // Keyboard release type
    public static final int INPUT_KEYBOARD_PRESSED = 1; // Keyboard press type
    public static final int INPUT_JOYSTICK_PRESSED = 2; // Joystick press type
    public static final int INPUT_JOYSTICK_RELEASED = 3; // Joystick release type

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