package guitarjava.game;

/**
 *
 * @author lucasjadami
 */
public class BurningNoteExtension extends NoteExtension
{
    private BurningInterface burningState;
    private boolean powning;
    
    public BurningNoteExtension(int track, double position, double height, BurningInterface burningState)
    {
        super(track, position, height);
        this.burningState = burningState;
        powning = true;
    }

    @Override
    public void think(float deltaTime)
    {
        y += TRACK_DEFAULT_SPEED * deltaTime;

        if (powning && y > TrackObject.BURNING_POSITION_Y - height / 2)
        {
            height = Math.max(0, height - TRACK_DEFAULT_SPEED * deltaTime);
            drawData.createAs2DFilledRect((int) width, (int) height);

            y -= TRACK_DEFAULT_SPEED * deltaTime / 2;

            powning = burningState.isBurning();
        }

        updateDrawDataPosition();
    }
}
