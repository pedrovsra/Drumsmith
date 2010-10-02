package guitarjava.game;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends TrackObject
{
    public static final double ORIGIN_Y = -1260;
    private static final int Z_SURFACE_FACTOR = 3;

    private int number;
    private float duration;
    private boolean powned;
    private boolean readMiss;
    private NoteExtension noteExtension;
    private NoteListener listener;

    /**
     * @param listener Listener of the miss event.
     * @param number Position on the notes list.
     * @param track The track of the note.
     * @param duration The duration.
     */
    public Note(NoteListener listener, int number, int track, float duration)
    {
        super(track, ORIGIN_Y, 1, DEFAULT_OBJECT_SIZE,
                DEFAULT_OBJECT_SIZE, Constant.CACHEID_NOTE);

        this.listener = listener;
        this.number = number;
        this.duration = duration;

        if (duration > 0)
        {
            noteExtension = new NoteExtension(track, duration * 1000 * TRACK_DEFAULT_SPEED -
                    height, ORIGIN_Y, height);
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
        updateDrawDataPosition();

        if (noteExtension != null)
        {
            noteExtension.think(deltaTime);
        }

        if (!readMiss && y > BURNING_POSITION_Y + height && !isPowned())
        {
            readMiss = true;
            listener.proccessMissEvent(number);
        }
    }

    /**
     * Moves the note forward from its y position.
     * @param y The space to be forwarded.
     */
    public void forward(double y)
    {
        setY(this.y + y);
        if (noteExtension != null)
            noteExtension.setY(noteExtension.getY() + y);
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
        return powned;
    }

    /**
     * @return True if the note can be drawn.
     */
    public boolean canDraw()
    {
        return y < BURNING_POSITION_Y || !powned;
    }

    /**
     * @return The number of the note on the notes list.
     */
    public int getNumber()
    {
        return number;
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
            noteExtension = new BurningNoteExtension(noteExtension.getTrack(), noteExtension.getY(),
                    noteExtension.getHeight(), burningState);
        }
    }
    
    /**
     * @return The note extension.
     */
    public NoteExtension getNoteExtension()
    {
        return noteExtension;
    }

    /**
     * @return True if this object already sent a miss event.
     */
    public boolean isMissed()
    {
        return readMiss;
    }
}
