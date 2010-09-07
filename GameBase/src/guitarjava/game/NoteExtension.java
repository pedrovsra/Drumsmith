package guitarjava.game;

/**
 * Class that respresents a note extension.
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 5;

    private boolean powning;
    private double height;
    private BurningInterface burningState;

    /**
     * @param track Track of this object.
     * @param height Height of the extension.
     */
    public NoteExtension(int track, int height)
    {
        super(track, (TrackObject.OBJECT_SIZE - WIDTH) / 2, -height, 1);

        this.height = height;
        drawData.createAsFilledBox(WIDTH, height, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        y += Note.DEFAULT_SPEED * deltaTime;
        
        if (powning)
        {
            height = Math.max(0, height - Note.DEFAULT_SPEED * deltaTime);
            drawData.createAsFilledBox(WIDTH, (int) height, 1);

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

        // Realocates and resizes it to centralize on the guitarButton object.
        double deltaY = y - (GuitarButton.POSITION_Y - height);
        height = Math.max(0, height - deltaY);
        y = GuitarButton.POSITION_Y - height;
        drawData.createAsFilledBox(WIDTH, (int) height, 1);

        drawData.setPosition(x, y, z);
    }

}
