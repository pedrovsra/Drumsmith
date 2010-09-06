package guitarjava.game;

/**
 * Contains the data fields of a music note.
 * @author lucasjadami
 */
public class NoteXml
{
    private double time;
    private double duration;
    private int track;

    /**
     * @param time Time that it appears in the music.
     * @param duration Duration.
     * @param track Track.
     */
    public NoteXml(double time, double duration, int track)
    {
        this.time = time;
        this.duration = duration;
        this.track = track;
    }

    /**
     * @return The time.
     */
    public double getTime()
    {
        return time;
    }

    /**
     * @return The duration.
     */
    public double getDuration()
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
