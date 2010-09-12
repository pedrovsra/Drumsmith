package guitarjava.game;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{

    public static final int PIXELS_JUMP_PER_FRAME = (int) (TrackObject.TRACK_DEFAULT_SPEED * Constant.FRAME_DURATION);
    public static final float ORIGIN_Y = -1200;
    private static final int Z_SURFACE_FACTOR = 3;
    private float duration;
    private boolean powned;
    private NoteExtension noteExtension;

    /**
     * @param track The track of the note.
     * @param duration The duration.
     */
    public Note(int track, float duration)
    {
        super(track, -DEFAULT_OBJECT_SIZE + ORIGIN_Y, 1, DEFAULT_OBJECT_SIZE,
                DEFAULT_OBJECT_SIZE, Constant.CACHEID_NOTE);

        this.duration = duration;

        if (duration > 0)
        {
            noteExtension = new NoteExtension(track, duration * 1000 * TRACK_DEFAULT_SPEED -
                    DEFAULT_OBJECT_SIZE, ORIGIN_Y, height);
        }

        double realRadiusSquared = width * width * Z_SURFACE_FACTOR * Z_SURFACE_FACTOR /
                (2 * Z_SURFACE_FACTOR - 1) / 4;

        drawData.createAs3DClippedSphere((float) (Math.pow(realRadiusSquared, .5) * 2),
                (float) (Math.pow(realRadiusSquared, .5) * 2 / Z_SURFACE_FACTOR));
    }

    @Override
    public void think(float deltaTime)
    {
        y += TRACK_DEFAULT_SPEED * deltaTime;
        drawData.setPosition(x, y, z);

        if (noteExtension != null)
        {
            noteExtension.think(deltaTime);
        }
    }

    /**
     * @return True if the note is visible.
     */
    public boolean isVisible()
    {
        return (y < Constant.WINDOW_HEIGHT + duration * 1000 * TRACK_DEFAULT_SPEED + height / 2);
    }

    /**
     * @return True if the note is powned.
     */
    public boolean isPowned()
    {
        return y > BURNING_POSITION_Y && powned;
    }

    /**
     * @return The duration.
     */
    public float getDuration()
    {
        return duration;
    }

    /**
     * @param burningState An interface that indicates if the noteExtension needs to keep burning.
     */
    public void setPowned(BurningInterface burningState)
    {
        powned = true;
        if (noteExtension != null)
        {
            noteExtension.setPowned(burningState);
        }
    }

    /**
     * @return The note extension.
     */
    public NoteExtension getNoteExtension()
    {
        return noteExtension;
    }
}
