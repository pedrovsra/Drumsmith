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
    private List<Particle> particles;
    private BurningInterface burningState;

    /**
     * @param track Which track the it is in.
     * @param duration The total duration of the flame.
     */
    public Flame(BurningInterface burningState, int track, float duration)
    {
        super(track, BURNING_POSITION_Y, 2, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE);

        this.burningState = burningState;
        this.duration = duration;

        particles = new LinkedList<Particle>();
    }

    /**
     * Gets the particles list.
     */
    public List<Particle> getParticles()
    {
        return particles;
    }

    @Override
    public void think(float deltaTime)
    {
        timeElapsed += deltaTime;

        Iterator<Particle> it = particles.iterator();
        while (it.hasNext())
        {
            Particle p = it.next();
            p.think(deltaTime);
            if (p.isDead())
            {
                it.remove();
            }
        }

        if (extinguish)
            return;

        if (timeElapsed > duration)
            extinguish = true;
        else
            extinguish = !burningState.isBurning();

        if (particles.size() < FLAME_PARTICLES)
        {
            particles.add(new Particle(x, y, z - Particle.PARTICLE_WIDTH, 0.5f,
                    TrackObject.getColorByTrack(track)));
        }  
    }

    /**
     * @return True if the total duration has already ended.
     */
    public boolean canExtinguish()
    {
        return extinguish && particles.isEmpty();
    }
}
