package guitarjava.game;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final int DEFAULT_SPEED = 6;

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
        double timesToThink = (deltaTime + deltaTimeRest) / Constant.FRAME_DURATION;
        int timesToThinkInt = (int) timesToThink;
        this.deltaTimeRest = timesToThink - timesToThinkInt;

        y += DEFAULT_SPEED * timesToThinkInt;
        
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
