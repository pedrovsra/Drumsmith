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

    public Note(int track, double duration)
    {
        super(track, -TrackObject.OBJECT_SIZE, 1);

        this.duration = duration;

        if (duration > 0)
            noteExtension = new NoteExtension(track, (int) (duration * 1000 * DEFAULT_SPEED - TrackObject.OBJECT_SIZE));
        
        drawData.createAsSphere(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE);
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
     * @return
     */
    public boolean isVisible()
    {
        return (y < Graphics2DContext.GRAPHICS_HEIGHT + duration * 1000 * DEFAULT_SPEED);
    }

    /**
     * @return
     */
    public boolean isPowned()
    {
        return powned;
    }

    /**
     * @return
     */
    public double getDuration()
    {
        return duration;
    }

    /**
     * @param button
     */
    public void setPowned(BurningInterface burningState)
    {
        powned = true;
        if (noteExtension != null)
            noteExtension.setPowned(burningState);
    }

    /**
     * @return
     */
    public NoteExtension getNoteExtension()
    {
        return noteExtension;
    }
}
