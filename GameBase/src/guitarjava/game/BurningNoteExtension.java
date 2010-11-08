package guitarjava.game;

/**
 * It is the NoteExtension on the burning state.
 * @author lucasjadami
 */
public class BurningNoteExtension extends NoteExtension
{
    private BurningInterface burningState;
    private boolean powning;

    /**
     * @param track The track.
     * @param position The y position.
     * @param height The height.
     * @param burningState The interface to read if the extension needs to keep burning.
     */
    public BurningNoteExtension(int track, double position, double height, BurningInterface burningState)
    {
        super(track, position, height);
        this.burningState = burningState;
        // Powning is a trigger to stop burning (powning == false then stop burning).
        powning = true;
    }

    @Override
    public void think(float deltaTime)
    {
        updateSolo();

        y += TRACK_DEFAULT_SPEED * deltaTime;

        if (powning && y > TrackObject.BURNING_POSITION_Y - height / 2)
        {
            // It will burn only when the extension reaches the guitar button y position.

            height = Math.max(0, height - TRACK_DEFAULT_SPEED * deltaTime);
            drawDatas.getFirst().createAs2DFilledRect((int) width, (int) height);
            y -= TRACK_DEFAULT_SPEED * deltaTime / 2;
            powning = burningState.isBurning();
        }

        updateDrawDataPosition(drawDatas.getFirst());
    }
}
