package guitarjava.main;

import guitarjava.components.ErrorWindow;
import guitarjava.game.Constant;
import guitarjava.game.GameEngine;
import guitarjava.game.Music;
import guitarjava.graphics.Graphics2DContext;
import guitarjava.input.InputContext;
import guitarjava.timing.TimingContext;

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
    public static void main(String[] args) throws Exception
    {
        Thread.setDefaultUncaughtExceptionHandler(new ErrorWindow(null));

        Graphics2DContext graphicsContext = new Graphics2DContext(Constant.FRAME_DURATION);
        TimingContext timingContext = new TimingContext();
        InputContext inputContext = new InputContext();

        GameEngine gameEngine = new GameEngine(graphicsContext, timingContext, inputContext, new Music("TestMusic.xml", "TestMusic.mp3"));

        graphicsContext.addGraphicsUpdateEventListener(gameEngine);
        inputContext.addInputEventListener(gameEngine);
        
        gameEngine.start();
    }
}
