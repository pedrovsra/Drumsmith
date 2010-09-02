package guitarjava.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * It is the game engine.
 * @author lucasjadami
 */
public class GameEngine
{
    private GraphicsInterface graphicsContext;
    private Music music;
    private List<GameObject> objects;

    /**
     * Constructor of the engine.
     * @param music Music played in the game.
     */
    public GameEngine(GraphicsInterface graphicsContext, TimingInterface timingContext,
            InputInterface inputContext, Music music)
    {
        this.graphicsContext = graphicsContext;
        this.music = music;
        objects = new ArrayList<GameObject>();
    }

    /**
     * Update method. Called so the engine components can do their logic/draw operations.
     */
    public void update()
    {
        float deltaTime = 0;

        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext())
        {
            GameObject object = it.next();
            object.think(deltaTime);
            graphicsContext.draw(object);
        }
    }
}
