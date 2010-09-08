package guitarjava.game;

import guitarjava.graphics.Graphics2DContext;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final int PIXELS_JUMP_PER_FRAME = (int)(TrackObject.TRACK_DEFAULT_SPEED * Constant.FRAME_DURATION);
    public static final double ORIGIN_Y = -1200;

    private double duration;
    private boolean powned;
    private NoteExtension noteExtension;

    /**
     * @param track The track of the note.
     * @param duration The duration.
     */
    public Note(int track, double duration)
    {
        super(track, -DEFAULT_OBJECT_SIZE + ORIGIN_Y, 1, DEFAULT_OBJECT_SIZE,
                DEFAULT_OBJECT_SIZE);

        this.duration = duration;

        if (duration > 0)
            noteExtension = new NoteExtension(track, duration * 1000 * TRACK_DEFAULT_SPEED,
                    ORIGIN_Y, height);
        
        drawData.createAsHalfSphere((int) width, (int)  height);
    }

    @Override
    public void think(double deltaTime)
    {
        y += TRACK_DEFAULT_SPEED * deltaTime;
        drawData.setPosition(x, y, z);

        if (noteExtension != null)
            noteExtension.think(deltaTime);
    }

    /**
     * @return True if the note is visible.
     */
    public boolean isVisible()
    {
        return (y < Constant.WINDOW_HEIGHT + duration * 1000 * TRACK_DEFAULT_SPEED);
    }

    /**
     * @return True if the note is powned.
     */
    public boolean isPowned()
    {
        return y > BURNING_POSITION_Y - height / 2 && powned;
    }

    /**
     * @return The duration.
     */
    public double getDuration()
    {
        return duration;
    }

    /**
     * @param burningState An interface that indicates if the noteExtension needs to keep burning.
     */
    public void setPowned(BurningInterface burningState)
    {
        powned = true;
        if (noteExtension != null)
            noteExtension.setPowned(burningState);
    }

    /**
     * @return The note extension.
     */
    public NoteExtension getNoteExtension()
    {
        return noteExtension;
    }
}
