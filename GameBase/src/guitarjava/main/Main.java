package guitarjava.main;

import guitarjava.components.ErrorWindow;
import guitarjava.components.GameWindow;
import guitarjava.game.Constant;
import guitarjava.game.GameEngine;
import guitarjava.game.Music;
import guitarjava.graphics.Graphics3DContext;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.input.InputNoRepeatContext;
import guitarjava.timing.TimingContext;

/**
 *
 * @author brunojadami
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

        GameWindow gameWindow = new GameWindow(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        GraphicsInterface graphicsContext = new Graphics3DContext();
        TimingContext timingContext = new TimingContext();
        InputNoRepeatContext inputContext = new InputNoRepeatContext();

        GameEngine gameEngine = new GameEngine(graphicsContext, timingContext, inputContext,
                gameWindow, new Music("musics/xml/Music2_4.xml", "musics/mp3/Music2.mp3"));

        gameEngine.start();
    }
}
