package guitarjava.game;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject
{
    public static final int POSITION_Y = 520;

    private int track;
    private boolean pressed;
    private Note notePowning;

    public GuitarButton(int track)
    {
        super(track, POSITION_Y, 1);

        this.track = track;

        drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        if (notePowning != null)
        {

        }
    }

    /**
     * @param note
     * @return
     */
    public boolean collide(Note note)
    {
        if (!pressed || note.getTrack() != track)
            return false;

        if (note.getY() > y - TrackObject.OBJECT_SIZE && note.getY() < y + TrackObject.OBJECT_SIZE)
        {
            note.setPowned();
            notePowning = note;
        }

        return note.isPowned();
    }

    /**
     * @param pressed
     */
    public void setPressed(boolean pressed)
    {
        this.pressed = pressed;
    }
}
