package guitarjava.game;

import java.awt.Color;

/**
 *
 * @author lucasjadami
 */
public abstract class TrackObject extends GameObject
{
    public static final int TRACK_SPACEMENT = 70;
    public static final int OBJECT_SIZE = 60;

    private int track;

    public TrackObject(int track, int y, int z)
    {
        this(track, 0f, y, z);
    }

    public TrackObject(int track, float spacement, int y, int z)
    {
        super(TRACK_SPACEMENT * (track + 1) + spacement, y, 1, TrackObject.getColorByTrack(track));

        this.track = track;
    }

    public abstract void think(double deltaTime);

    /**
     * @return
     */
    public int getTrack()
    {
        return track;
    }
    
    private static Color getColorByTrack(int track)
    {
        switch (track)
        {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.RED;
            case 4:
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
}
