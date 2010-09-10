package guitarjava.game;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A flam is an object shown when the player hits a note correctly.
 * @author lucasjadami
 */
public class Flame extends TrackObject
{

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int FLAME_PARTICLES = 30;
    private float duration;
    private float noteDuration;
    private float timeElapsed;
    private boolean extinguish;
    private boolean extinguishNote;
    private List<Particle> flames;

    /**
     * @param track Which track the it is in.
     * @param duration The total duration of the flame.
     * @param noteDuration The duration of the flame burning the note.
     */
    public Flame(int track, float duration, float noteDuration)
    {
        super(track, BURNING_POSITION_Y, 2, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);

        this.duration = duration;
        this.noteDuration = noteDuration;

        flames = new LinkedList<Particle>();
    }

    /**
     * Gets the particles list.
     */
    public List<Particle> getParticles()
    {
        return flames;
    }

    @Override
    public void think(float deltaTime)
    {
        timeElapsed += deltaTime;
        Iterator<Particle> it = flames.iterator();
        while (it.hasNext())
        {
            Particle p = it.next();
            p.think(deltaTime);
            if (p.isDead())
            {
                it.remove();
            }
        }

        if (flames.size() < FLAME_PARTICLES && (!extinguish && !extinguishNote))
        {
            flames.add(new Particle(x, y, z - Particle.PARTICLE_WIDTH, 0.5f,
                    TrackObject.getColorByTrack(track)));
        }

        if (timeElapsed > noteDuration)
        {
            extinguishNote = true;
        }
        if (timeElapsed > duration)
        {
            extinguish = true;
        }
    }

    /**
     * @return True if the total duration has already ended.
     */
    public boolean canExtinguish()
    {
        return extinguish && flames.isEmpty();
    }

    /**
     * @return True if the duration of the fire in the note has ended.
     */
    public boolean canExtinguishNote()
    {
        return extinguishNote && flames.isEmpty();
    }
}
