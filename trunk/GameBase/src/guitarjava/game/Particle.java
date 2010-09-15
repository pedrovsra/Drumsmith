package guitarjava.game;

import java.awt.Color;

/**
 * Particle class.
 * @author brunojadami
 */
public class Particle extends GameObject
{

    protected static final double PARTICLE_WIDTH = 5;
    protected static final double PARTICLE_HEIGHT = 5;
    private static final double Z_DES = 0.005;
    private double vx;
    private double vy;
    private double vz;

    public Particle(double x, double y, double z, double maxVel, Color color, int cacheId)
    {
        super(x, y, z, PARTICLE_WIDTH, PARTICLE_HEIGHT, color, cacheId);
        vx = (Math.random() * maxVel) - maxVel / 2;
        vy = (Math.random() * maxVel) - maxVel / 2;
        vz = (Math.random() * maxVel) + maxVel / 2;
        drawData.createAs3DSphere((float) width);
    }

    @Override
    public void think(float deltaTime)
    {
        x += vx * deltaTime;
        y += vy * deltaTime;
        z += vz * deltaTime;
        vz -= Z_DES * deltaTime;
        updateDrawDataPosition();
    }

    /**
     * Check if particle is dead.
     */
    public boolean isDead()
    {
        return z <= 0;
    }
}
