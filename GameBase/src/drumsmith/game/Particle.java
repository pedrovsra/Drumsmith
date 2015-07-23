package drumsmith.game;

import java.awt.Color;

/**
 * Particle class.
 * @author brunojadami
 */
public class Particle extends TrackObject
{
    protected static final double PARTICLE_WIDTH = 5;
    protected static final double PARTICLE_HEIGHT = 5;
    private static final double Z_DES = 0.005;

    private double vx;
    private double vy;
    private double vz;

    /**
     * @param x The x position.
     * @param y The y position.
     * @param z The z position.
     * @param maxVel The maximum speed of it.
     * @param color Color.
     * @param cacheId Cache ID.
     */
    public Particle(double x, double y, double z, double maxVel, Color color, int cacheId)
    {
        super(x, y, z, PARTICLE_WIDTH, PARTICLE_HEIGHT, color, cacheId);
        vx = (Math.random() * maxVel) - maxVel / 2;
        vy = (Math.random() * maxVel) - maxVel / 1.5f;
        vz = (Math.random() * maxVel) + maxVel / 2;
        drawDatas.getFirst().createAs3DSphere((float) width);
    }

    @Override
    public void think(float deltaTime)
    {
        updateSolo();
        x += vx * deltaTime;
        y += vy * deltaTime;
        z += vz * deltaTime;
        vz -= Z_DES * deltaTime;
        updateDrawDataPosition(drawDatas.getFirst());
    }

    /**
     * Check if particle is dead.
     */
    public boolean isDead()
    {
        return z <= 0 || x <= 0 || y <= 0 || x >= Constant.WINDOW_WIDTH;
    }
}
