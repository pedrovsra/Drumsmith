package guitarjava.game;

import java.awt.Color;

/**
 * Particle class.
 * @author brunojadami
 */
public class Particle extends GameObject
{

    protected static final float PARTICLE_WIDTH = 5;
    protected static final float PARTICLE_HEIGHT = 5;
    private static final float Z_DES = 0.005f;
    private float vx;
    private float vy;
    private float vz;

    public Particle(float x, float y, float z, float maxVel, Color color)
    {
        super(x, y, z, PARTICLE_WIDTH, PARTICLE_HEIGHT, color);
        vx = (float) (Math.random() * maxVel) - maxVel / 2;
        vy = (float) (Math.random() * maxVel) - maxVel / 2;
        vz = (float) (Math.random() * maxVel) * 2;
        drawData.createAs3DSphere(width);
    }

    @Override
    public void think(float deltaTime)
    {
        x += vx * deltaTime;
        y += vy * deltaTime;
        z += vz * deltaTime;
        vz -= Z_DES * deltaTime;
        drawData.setPosition(x, y, z);
    }

    /**
     * Check if particle is dead.
     */
    public boolean isDead()
    {
        return z <= 0;
    }
}
