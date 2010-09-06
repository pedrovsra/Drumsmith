package guitarjava.game;

import guitarjava.graphics.Graphics2DContext;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final float DEFAULT_SPEED = 0.24f;
    public static final int PIXELS_JUMP_PER_FRAME = 6; // DEFAULT_SPEED * FRAME_DURATION;

    private float duration;
    private boolean powned;

    public Note(int track, float duration)
    {
        super(track, -TrackObject.OBJECT_SIZE, 1);

        this.duration = duration;
        
        drawData.createAsSphere(TrackObject.OBJECT_SIZE, TrackObject.OBJECT_SIZE);
    }

    @Override
    public void think(double deltaTime)
    {
        y += DEFAULT_SPEED * deltaTime;
        drawData.setPosition(x, y, z);
    }

    /**
     * @return
     */
    public boolean isVisible()
    {
        return (y < Graphics2DContext.GRAPHICS_HEIGHT + duration * DEFAULT_SPEED);
    }

    /**
     * @return
     */
    public boolean isPowned()
    {
        return powned;
    }

    /**
     * @param powned
     */
    public void setPowned()
    {
        powned = true;
    }
}
