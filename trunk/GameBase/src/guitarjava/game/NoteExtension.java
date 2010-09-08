package guitarjava.game;

/**
 * Class that respresents a note extension.
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 20;

    private boolean powning;
    private BurningInterface burningState;

    /**
     * @param track Track of this object.
     * @param height Height of the extension.
     */
    public NoteExtension(int track, double height, double noteOriginY, double noteHeight)
    {
        super(track, -(height / 2 + 1.5*noteHeight) + noteOriginY, 0, WIDTH, height);

        drawData.createAs2DFillRect(WIDTH, (int) height);
    }

    @Override
    public void think(double deltaTime)
    {
        y += TRACK_DEFAULT_SPEED * deltaTime;
        
        if (powning && y > TrackObject.BURNING_POSITION_Y - height / 2)
        {
            height = Math.max(0, height - TRACK_DEFAULT_SPEED * deltaTime);
            drawData.createAs2DFillRect(WIDTH, (int) height);

            y -= TRACK_DEFAULT_SPEED * deltaTime / 2;

            powning = burningState.isBurning();
        }

        drawData.setPosition(x, y, z);
    }

    /**
     * @param burningState An interface that indicates if it needs to keep burning.
     */
    public void setPowned(BurningInterface burningState)
    {
        powning = true;
        this.burningState = burningState;

        drawData.createAs2DFillRect((int) width, (int) height);
    }

}
