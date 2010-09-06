package guitarjava.game;

import java.awt.Color;

/**
 *
 * @author lucasjadami
 */
public class Flame extends GameObject
{
    private double duration;
    private double timeElapsed;
    private boolean extinguish;

    public Flame(int track, double duration)
    {
        super(TrackObject.TRACK_SPACEMENT * (track + 1), GuitarButton.POSITION_Y, 1, Color.WHITE);

        this.duration = duration;
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
