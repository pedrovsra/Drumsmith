package guitarjava.game;

/**
 * Contains the data fields of a music note.
 * @author lucasjadami
 */
public class NoteXml
{
    private float time;
    private float duration;
    private int track;

    /**
     * @param time Time that it appears in the music.
     * @param duration Duration.
     * @param track Track.
     */
    public NoteXml(float time, float duration, int track)
    {
        this.time = time;
        this.duration = duration;
        this.track = track;
    }

    /**
     * @return The time.
     */
    public float getTime()
    {
        return time;
    }

    /**
     * @return The duration.
     */
    public float getDuration()
    {
        return duration;
    }

    /**
     * @return The track.
     */
    public int getTrack()
    {
        return track;
    }
}
