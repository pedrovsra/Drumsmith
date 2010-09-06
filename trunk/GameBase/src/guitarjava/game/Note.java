package guitarjava.game;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final float DEFAULT_SPEED = 0.24f;

    private float duration;
    private boolean powned;

    public Note(int track, float duration)
    {
        super(track, 0, 1);

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
        return (y < Constant.WINDOW_HEIGHT + duration * DEFAULT_SPEED);
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
