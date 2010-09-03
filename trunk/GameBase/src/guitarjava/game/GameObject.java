package guitarjava.game;

import guitarjava.graphics.DrawData;

/**
 * It is an abstract class containing game object data.
 * @author lucasjadami
 */
public abstract class GameObject
{
    private DrawData drawData;

    /**
     * Method called to do logic operations.
     * @param deltaTime Delta time.
     */
    public abstract void think(double deltaTime);

    public DrawData getDrawData()
    {
        return drawData;
    }
}
