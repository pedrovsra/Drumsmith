package guitarjava.game;

/**
 * Class that respresents a note extension.
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 16;

    private boolean powning;
    private BurningInterface burningState;

    /**
     * @param track Track of this object.
     * @param height Height of the extension.
     */
    public NoteExtension(int track, double height, double noteOriginY, double noteHeight)
    {
        super(track, -(height / 2 + .5f*noteHeight) + noteOriginY, 0, WIDTH, height, -1);

        drawData.createAs2DFilledRect(WIDTH, (int) height);
    }

    @Override
    public void think(float deltaTime)
    {
        y += TRACK_DEFAULT_SPEED * deltaTime;
        
        if (powning && y > TrackObject.BURNING_POSITION_Y - height / 2)
        {
            height = Math.max(0, height - TRACK_DEFAULT_SPEED * deltaTime);
            drawData.createAs2DFilledRect(WIDTH, (int) height);

            y -= TRACK_DEFAULT_SPEED * deltaTime / 2;

            powning = burningState.isBurning();
        }

        updateDrawDataPosition();
    }

    /**
     * @param burningState An interface that indicates if it needs to keep burning.
     */
    public void setPowned(BurningInterface burningState)
    {
        powning = true;
        this.burningState = burningState;

        drawData.createAs2DFilledRect((int) width, (int) height);
    }

}
