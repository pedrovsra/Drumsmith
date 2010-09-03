package guitarjava.game;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends GameObject
{
    @Override
    public void think(double deltaTime)
    {

    }

    /**
     * @param note
     * @return
     */
    public boolean collide(Note note)
    {
        return false;
    }

    /**
     * @param pressed
     */
    public void setPressed(boolean pressed)
    {

    }
}
