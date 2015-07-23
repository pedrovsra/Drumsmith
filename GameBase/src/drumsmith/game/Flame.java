package drumsmith.game;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A flame is an object shown when the player hits a note correctly.
 * @author lucasjadami
 */
public class Flame extends TrackObject
{
    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int FLAME_PARTICLES = 20;
    private double duration;
    private double timeElapsed;
    private boolean extinguish;
    private List<Particle> particles;
    private BurningInterface burningState;

    /**
     * @param BruningInterface An interface to indicate if it needs to keep burning.
     * @param track Which track the it is in.
     * @param duration The total duration.
     */
    public Flame(BurningInterface burningState, int track, double duration, boolean doingSolo)
    {
        super(track, BURNING_POSITION_Y, 2, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.WHITE, -1);

        this.burningState = burningState;
        this.duration = duration;
        this.doingSolo = doingSolo;

        particles = new LinkedList<Particle>();

        for (int x = 0; x < FLAME_PARTICLES / 4; ++x)
        {
            double px = x + Math.random() * DEFAULT_WIDTH - DEFAULT_WIDTH/2;
            double py = y + Math.random() * DEFAULT_WIDTH - DEFAULT_WIDTH/2;
            particles.add(new Particle(px, py, z, 0.5,
                    TrackObject.getColorByTrack(track), Constant.CACHEID_FLAME));
        }
    }

    /**
     * @return The particles of the flame.
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
            p.setDoingSolo(doingSolo);
            p.think(deltaTime);
            if (p.isDead())
                it.remove();
        }

        // Checks if it is needed to keep adding particles.
        if (extinguish)
            return;

        if (timeElapsed > duration)
            extinguish = true;
        else
            extinguish = !burningState.isBurning();

        if (particles.size() < FLAME_PARTICLES)
        {
            float vel = 0.5f;
            if (doingSolo)
                vel = 1.0f;
            double px = x + Math.random() * DEFAULT_WIDTH - DEFAULT_WIDTH/2;
            double py = y + Math.random() * DEFAULT_WIDTH - DEFAULT_WIDTH/2;
            particles.add(new Particle(px, py, z, vel,
                    TrackObject.getColorByTrack(track), Constant.CACHEID_FLAME));
        }  
    }

    /**
     * @return True if the total duration has already ended and there are no particles to be drawn.
     */
    public boolean canExtinguish()
    {
        return extinguish && particles.isEmpty();
    }
}
