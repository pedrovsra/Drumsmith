package guitarjava.game;

import java.awt.Color;

/**
 * A flam is an object shown when the player hits a note correctly.
 * @author lucasjadami
 */
public class Flame extends TrackObject
{
    private static final int SIZE = 30;

    private double duration;
    private double timeElapsed;
    private boolean extinguish;

    /**
     * @param track Which track the it is in.
     * @param duration The duration of the flame.
     */
    public Flame(int track, double duration)
    {
        super(track, SIZE, GuitarButton.POSITION_Y, 1, Color.WHITE);

        this.duration = duration;

        drawData.createAsFilledBox(SIZE, SIZE, 1);
    }
    
    @Override
    public void think(double deltaTime)
    {
        timeElapsed += deltaTime;
        if (timeElapsed > duration)
            extinguish = true;
    }

    /**
     * @return True if the duration has already ended.
     */
    public boolean canExtinguish()
    {
        return extinguish;
    }
}
