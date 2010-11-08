package guitarjava.game;

import guitarjava.graphics.DrawData;
import java.awt.Color;
import java.util.Iterator;

/**
 * Represents an object that belongs to the track.
 * @author lucasjadami
 */
public abstract class TrackObject extends GameObject
{

    public static final int TRACK_SPACEMENT = 70;
    public static final int DEFAULT_OBJECT_SIZE = 60;
    public static final int BURNING_POSITION_Y = 490;
    protected static final double START_X = (Constant.WINDOW_WIDTH - TRACK_SPACEMENT * 6) / 2;
    public static final double TRACK_DEFAULT_SPEED = .5;
    protected int track;
    static protected int add = -6;
    static protected int alpha = 255;

    /**
     * @param track The track.
     * @param y Position y.
     * @param z Position z.
     * @param width Width.
     * @param height Height.
     * @param cacheId Cache ID.
     */
    public TrackObject(int track, double y, double z, double width, double height, int cacheId)
    {
        this(track, y, z, width, height, getColorByTrack(track), cacheId);
    }

    /**
     * @param track The track.
     * @param y Position y.
     * @param z Position z.
     * @param width Width.
     * @param height Height.
     * @para color Color.
     * @param cacheId Cache ID.
     */
    public TrackObject(int track, double y, double z, double width, double height, Color color, int cacheId)
    {
        super(START_X + TRACK_SPACEMENT * (track + 1), y, z, width, height, color, cacheId);

        this.track = track;
    }

    /**
     * @param x Position x.
     * @param y Position y.
     * @param z Position z.
     * @param width Width.
     * @param height Height.
     * @param color Color.
     * @param cacheId Cache of the object image.
     */
    public TrackObject(double x, double y, double z, double width, double height, Color color, int cacheId)
    {
        super(x, y, z, width, height, color, cacheId);
    }

    @Override
    public abstract void think(float deltaTime);

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
    protected static Color getColorByTrack(int track)
    {
        switch (track)
        {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Updates alpha and effects for solo.
     */
    protected void updateSolo()
    {
        if (doingSolo)
        {
            Iterator<DrawData> it = drawDatas.iterator();
            while (it.hasNext())
            {
                DrawData data = it.next();
                Color col = data.getColor();
                data.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha));
            }
        }
        else
        {
            Iterator<DrawData> it = drawDatas.iterator();
            while (it.hasNext())
            {
                DrawData data = it.next();
                Color col = data.getColor();
                if (col.getAlpha() != 255)
                {
                    data.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 255));
                }
            }
        }
    }

    /**
     * Updates the solo alpha.
     */
    static public void updateSoloAlpha()
    {
        if (add < 0)
        {
            alpha += add;
            if (alpha <= 50)
            {
                add = -add;
            }
        }
        else
        {
            alpha += add;
            if (alpha >= 255)
            {
                add = -add;
                alpha = 255;
            }
        }
    }
}
