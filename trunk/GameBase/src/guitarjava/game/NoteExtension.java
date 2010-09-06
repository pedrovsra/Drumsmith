package guitarjava.game;

/**
 *
 * @author lucasjadami
 */
public class NoteExtension extends TrackObject
{
    private static final int WIDTH = 5;
    
    public NoteExtension(int track, int height)
    {
        super(track, (TrackObject.OBJECT_SIZE - WIDTH) / 2, -(TrackObject.OBJECT_SIZE + height), 1);

        drawData.createAsFilledBox(WIDTH, height, 1);
    }

    @Override
    public void think(double deltaTime)
    {
        y += Note.DEFAULT_SPEED * deltaTime;
        drawData.setPosition(x, y, z);
    }

}
