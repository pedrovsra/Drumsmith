package guitarjava.game;

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
    public abstract void think(float deltaTime);
}
