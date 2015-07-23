package drumsmith.game;

/**
 * Represents a listener of the note miss event.
 * @author lucasjadami
 */
public interface NoteListener
{
    /**
     * Proccess miss event.
     * @param noteNumber The number in the notes list of the note powned.
     */
    public void proccessMissEvent(int noteNumber);
}
