package drumsmith.game;

/**
 * Class that respresents a note extension.
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 16;

    /**
     * @param track Track of this object.
     * @param height Height of the extension.
     * @param noteOriginY The initial y position of the note.
     * @param noteHeight  The height of the note.
     */
    public NoteExtension(int track, double height, double noteOriginY, double noteHeight)
    {
        this(track, -(height / 2 + .5f*noteHeight) + noteOriginY, height);
    }

    /**
     * @param track Track of this object.
     * @param position The y position of this object.
     * @param height The height.
     */
    public NoteExtension(int track, double position, double height)
    {
        super(track, position, 0, WIDTH, height, -1);

        drawDatas.getFirst().createAs2DFilledRect(WIDTH, (int) height);
    }

    @Override
    public void think(float deltaTime)
    {
        updateSolo();

        y += TRACK_DEFAULT_SPEED * deltaTime;

        updateDrawDataPosition(drawDatas.getFirst());
    }
}
