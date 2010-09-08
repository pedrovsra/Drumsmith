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
    private double noteDuration;
    private double timeElapsed;
    private boolean extinguish;
    private boolean extinguishNote;

    /**
     * @param track Which track the it is in.
     * @param duration The total duration of the flame.
     * @param noteDuration The duration of the flame burning the note.
     */
    public Flame(int track, double duration, double noteDuration)
    {
        super(track, GuitarButton.POSITION_Y, 1, SIZE, SIZE, Color.WHITE);

        this.duration = duration;
        this.noteDuration = noteDuration;

        drawData.createAsFilledBox(SIZE, SIZE, 1);
    }
    
    @Override
    public void think(double deltaTime)
    {
        timeElapsed += deltaTime;

        if (timeElapsed > noteDuration)
            extinguishNote = true;
        if (timeElapsed > duration)
            extinguish = true;
    }

    /**
     * @return True if the total duration has already ended.
     */
    public boolean canExtinguish()
    {
        return extinguish;
    }

    /**
     * @return True if the duration of the fire in the note has ended.
     */
    public boolean canExtinguishNote()
    {
        return extinguishNote;
    }
}
