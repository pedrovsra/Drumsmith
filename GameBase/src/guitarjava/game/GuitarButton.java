package guitarjava.game;

import java.awt.Color;

/**
 * Represents the button where when the notes are passing trough, you should press the button.
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject
{
    public static final int POSITION_Y = 520;

    private int track;
    private boolean pressed;
    private Flame flame;

    public GuitarButton(int track)
    {
        super(track, POSITION_Y, 1);

        this.track = track;

        drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    @Override
    public void think(double deltaTime)
    {
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
        if (!pressed || note.getTrack() != track)
            return false;

        if (note.getY() > y - TrackObject.OBJECT_SIZE && note.getY() < y + TrackObject.OBJECT_SIZE)
        {
            note.setPowned();
            
            double duration = note.getDuration() + TrackObject.OBJECT_SIZE * Note.DEFAULT_SPEED;
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
            drawData.createAsBox(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE, 1);
    }

    /**
     * @return
     */
    public Flame getFlame()
    {
        return flame;
    }
}
