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
    private int number;
    private int special;

    /**
     * @param time Time that it appears in the music.
     * @param duration Duration.
     * @param track Track.
     */
    public NoteXml(float time, float duration, int track, int number, int special)
    {
        this.time = time;
        this.duration = duration;
        this.track = track;
        this.number = number;
        this.special = special;
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

    /**
     * @return The number of the note on the notes list.
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * @return The special value.
     */
    public int getSpecial()
    {
        return special;
    }
}
