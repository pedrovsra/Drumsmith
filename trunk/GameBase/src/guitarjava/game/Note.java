package guitarjava.game;

/**
 * Represents a music note.
 * @author lucasjadami
 */
public class Note extends GameObject
{
    @Override
    public void think(double deltaTime)
    {
        
    }

    /**
     * @return
     */
    public boolean isVisible()
    {
        return true;
    }

    /**
     * @return
     */
    public int getTrack()
    {
        return 0;
    }

    /**
     * @return
     */
    public boolean isPowned()
    {
        return true;
    }
}
