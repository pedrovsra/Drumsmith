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
    private boolean enabled;
    private Flame flame;

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
            if (flame.canExtinguish())
                flame = null;
        }
    }

    /**
     * @param note
     * @return
     */
    public boolean collide(Note note)
    {
        if (!pressed || note.getTrack() != track || !enabled)
            return false;

        if (note.getY() > y - TrackObject.OBJECT_SIZE && note.getY() < y + TrackObject.OBJECT_SIZE)
        {
            note.setPowned(this);
            
            double duration = note.getDuration() * 1000 + TrackObject.OBJECT_SIZE / Note.PIXELS_JUMP_PER_FRAME
                    * Constant.FRAME_DURATION;
            flame = new Flame(track, duration);
        }

        return note.isPowned();
    }

    /**
     * @param pressed
     */
    public void setPressed(boolean pressed)
    {
        this.pressed = pressed;

        if (pressed)
            drawData.createAsFilledBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
        else
        {
            drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
            flame = null;
            enabled = true;
        }
    }

    /**
     * @return
     */
    public Flame getFlame()
    {
        return flame;
    }

    public boolean isBurning()
    {
        return pressed;
    }
}
