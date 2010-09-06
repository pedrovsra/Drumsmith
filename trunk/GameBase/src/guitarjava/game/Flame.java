package guitarjava.game;

import java.awt.Color;

/**
 *
 * @author lucasjadami
 */
public class Flame extends GameObject
{
    private static final int SIZE = 30;

    private double duration;
    private double timeElapsed;
    private boolean extinguish;

    public Flame(int track, double duration)
    {
        super(TrackObject.TRACK_SPACEMENT * (track + 1) + (TrackObject.OBJECT_SIZE - SIZE) / 2,
                GuitarButton.POSITION_Y, 1, Color.WHITE);

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

    public boolean canExtinguish()
    {
        return extinguish;
    }
}
