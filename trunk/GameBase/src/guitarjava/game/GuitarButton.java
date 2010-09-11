package guitarjava.game;

import java.util.Iterator;
import java.util.List;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject implements BurningInterface
{

    private boolean pressed;
    private Flame flame;

    /**
     * @param track Track where it is located.
     */
    public GuitarButton(int track)
    {
        super(track, BURNING_POSITION_Y, 1, DEFAULT_OBJECT_SIZE, DEFAULT_OBJECT_SIZE, -1);

        this.track = track;

        drawData.createAs2DRect((int) width, (int) height);
    }

    @Override
    public void think(float deltaTime)
    {
        if (flame != null)
        {
            flame.think(deltaTime);

            if (flame.canExtinguish())
            {
                flame = null;
            }
        }
    }

    public void unpress()
    {
        pressed = false;
        drawData.createAs2DRect((int) width, (int) height);
    }

    public boolean press(List<Note> notes)
    {
        pressed = true;
        drawData.createAs2DFilledRect((int) width, (int) height);

        Iterator<Note> it = notes.iterator();
        while (it.hasNext())
        {
            Note note = it.next();

            boolean result = collide(note);
            if (result)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * @return The flame.
     */
    public Flame getFlame()
    {
        return flame;
    }

    /**
     * @return True if it is pressed.
     */
    public boolean isBurning()
    {
        return pressed;
    }

    /**
     * @param note The note to test the collision.
     * @return True if the collision happened.
     */
    private boolean collide(Note note)
    {
        if (note.getY() > y - DEFAULT_OBJECT_SIZE && note.getY() < y + DEFAULT_OBJECT_SIZE)
        {
            // Passes this to the note as BurningState so the track extension of the note can check if
            // it still have to burn.
            note.setPowned(this);

            // After powning the note, calculates the flame duration and create it.
            float duration = DEFAULT_OBJECT_SIZE / Note.PIXELS_JUMP_PER_FRAME
                    * Constant.FRAME_DURATION;
            float totalDuration = duration + note.getDuration() * 1000;

            flame = new Flame(this, track, totalDuration);
        }

        return note.isPowned();
    }
}
