package guitarjava.game;

import java.awt.Color;

/**
 * Represents an object that belongs to the track.
 * @author lucasjadami
 */
public abstract class TrackObject extends GameObject
{
    public static final int TRACK_SPACEMENT = 70;
    public static final int DEFAULT_OBJECT_SIZE = 60;
    public static final int BURNING_POSITION_Y = 490;
    private static final double START_X = (Constant.WINDOW_WIDTH - TRACK_SPACEMENT * 6) / 2;
    public static final double TRACK_DEFAULT_SPEED = .4;

    private int track;

    /**
     * @param track The track.
     * @param horizontalSize Adjustable spacement for objects with width smaller than the track object size.
     * @param y Position y.
     * @param z Position z.
     */
    public TrackObject(int track, double y, double z, double width, double height)
    {
        this(track, y, z, width, height, getColorByTrack(track));
    }

    /**
     * @param track The track.
     * @param horizontalSize Adjustable spacement for objects with width smaller than the track object size.
     * @param y Position y.
     * @param z Position z.
     */
    public TrackObject(int track, double y, double z, double width, double height, Color color)
    {
        super(START_X + TRACK_SPACEMENT * (track + 1), y, z, width, height, color);

        this.track = track;
    }

    public abstract void think(double deltaTime);

    /**
     * @return The track.
     */
    public int getTrack()
    {
        return track;
    }

    /**
     * @param track The track.
     * @return A different color for each track.
     */
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
