package guitarjava.game;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject implements BurningInterface
{
    public static final int POSITION_Y = 490;

    private int track;
    private boolean pressed;
    private boolean enabled; // Used so the player has to release the button and hit it again on every note.
    private Flame flame;

    /**
     * @param track Track where it is located.
     */
    public GuitarButton(int track)
    {
        super(track, POSITION_Y, 1);

        this.track = track;

        enabled = true;

        drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        // After testing all collisions, if the button is pressed, disable it.
        if (pressed)
            enabled = false;
        
        if (flame != null)
        {
            flame.think(deltaTime);
            if (flame.canExtinguish() || (flame.canExtinguishNote() && !pressed))
                flame = null;
        }
    }

    /**
     * @param note The note to test the collision.
     * @return True if the collision happened.
     */
    public boolean collide(Note note)
    {
        if (!pressed || note.getTrack() != track || !enabled)
            return false;

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

    /**
     * @param pressed The new pressed state.
     */
    public void setPressed(boolean pressed)
    {
        this.pressed = pressed;

        if (pressed)
            drawData.createAsFilledBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
        else
        {
            // The button is up, extinguish the flame and set it enabled again.
            drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
            enabled = true;
        }
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
}
