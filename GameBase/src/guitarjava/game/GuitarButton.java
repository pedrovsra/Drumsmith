package guitarjava.game;

import java.util.Iterator;
import java.util.List;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject implements BurningInterface
{
    public static final int POSITION_Y = 490;

    private int track;
    private boolean pressed;
    private Flame flame;

    /**
     * @param track Track where it is located.
     */
    public GuitarButton(int track)
    {
        super(track, POSITION_Y, 1, TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE);

        this.track = track;

        drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        if (flame != null)
        {
            flame.think(deltaTime);

            if (flame.canExtinguish() || (flame.canExtinguishNote() && !pressed))
                flame = null;
        }
    }

    public void unpress()
    {
        pressed = false;
        drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    public boolean press(List<Note> notes)
    {
        pressed = true;
        drawData.createAsFilledBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
        
        Iterator<Note> it = notes.iterator();
        while (it.hasNext())
        {
            Note note = it.next();

            boolean result = collide(note);
            if (result)
                return true;
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
        if (note.getY() > y - TrackObject.OBJECT_SIZE && note.getY() < y + TrackObject.OBJECT_SIZE)
        {
            // Passes this to the note as BurningState so the track extension of the note can check if
            // it still have to burn.
            note.setPowned(this);

            // After powning the note, calculates the flame duration and create it.
            double duration = TrackObject.OBJECT_SIZE / Note.PIXELS_JUMP_PER_FRAME
                    * Constant.FRAME_DURATION;
            double totalDuration = duration + note.getDuration() * 1000;

            flame = new Flame(track, totalDuration, duration);
        }

        return note.isPowned();
    }
}
