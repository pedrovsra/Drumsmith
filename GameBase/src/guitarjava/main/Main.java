package guitarjava.main;

import guitarjava.game.GameEngine;
import guitarjava.graphics.Graphics2DContext;

/**
 * Main class of this project.
 * @author lucasjadami
 */
public class Main
{
    /**
     * Main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args)
    {
        Graphics2DContext graphicsContext = null; // TODO: Create it.
        GameEngine gameEngine = null; // TODO: Create it.
        graphicsContext.addGraphicsUpdateEventListener(gameEngine);
    }
}
