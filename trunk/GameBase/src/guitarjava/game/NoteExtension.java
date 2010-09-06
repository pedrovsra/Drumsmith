package guitarjava.game;

/**
 *
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 5;

    private boolean powned;
    private double height;
    private BurningInterface burningState;
    
    public NoteExtension(int track, int height)
    {
        super(track, (TrackObject.OBJECT_SIZE - WIDTH) / 2, -(TrackObject.OBJECT_SIZE + height), 1);

        this.height = TrackObject.OBJECT_SIZE + height;
        drawData.createAsFilledBox(WIDTH, height, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        y += Note.DEFAULT_SPEED * deltaTime;
        
        if (powned)
        {
            if (burningState.isBurning())
            {
                height = Math.max(0, height - Note.DEFAULT_SPEED * deltaTime);
                drawData.createAsFilledBox(WIDTH, (int) height, 1);
            }
        }

        drawData.setPosition(x, y, z);
    }

    public void setPowned(BurningInterface burningState)
    {
        powned = true;
        this.burningState = burningState;

        double deltaY = y - (GuitarButton.POSITION_Y - height);
        height = Math.max(0, height - deltaY);
        y = GuitarButton.POSITION_Y - height;
        drawData.createAsFilledBox(WIDTH, (int) height, 1);

        drawData.setPosition(x, y, z);
    }

}
