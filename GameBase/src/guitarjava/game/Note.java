package guitarjava.game;

import guitarjava.graphics.Graphics2DContext;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final double DEFAULT_SPEED = 0.32;
    public static final int PIXELS_JUMP_PER_FRAME = (int)(DEFAULT_SPEED * Constant.FRAME_DURATION);

    private double duration;
    private boolean powned;
    private NoteExtension noteExtension;

    /**
     * @param track The track of the note.
     * @param duration The duration.
     */
    public Note(int track, double duration)
    {
        super(track, -TrackObject.OBJECT_SIZE / 2, 1);

        this.duration = duration;

        if (duration > 0)
            noteExtension = new NoteExtension(track, (int) (duration * 1000 * DEFAULT_SPEED) / 2);
        
        drawData.createAsHalfSphere(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE);
    }

    @Override
    public void think(double deltaTime)
    {
        y += DEFAULT_SPEED * deltaTime;
        drawData.setPosition(x, y, z);

        if (noteExtension != null)
            noteExtension.think(deltaTime);
    }

    /**
     * @return True if the note is visible.
     */
    public boolean isVisible()
    {
        return (y < Graphics2DContext.GRAPHICS_HEIGHT + duration * 1000 * DEFAULT_SPEED);
    }

    /**
     * @return True if the note is powned.
     */
    public boolean isPowned()
    {
        return powned;
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
