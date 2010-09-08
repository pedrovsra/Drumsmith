package guitarjava.main;

import guitarjava.components.ErrorWindow;
import guitarjava.game.Constant;
import guitarjava.game.GameEngine;
import guitarjava.game.Music;
import guitarjava.graphics.DrawData;
import guitarjava.graphics.Graphics2DContext;
import guitarjava.graphics.Graphics3DContext;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputContext;
import guitarjava.input.InputInterface;
import guitarjava.timing.TimingContext;
import guitarjava.timing.TimingInterface;
import java.awt.Window;
import java.util.EventObject;

/**
 *
 * @author brunojadami
 */
public class Main3D
{

    /**
     * Main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) throws Exception
    {
        Thread.setDefaultUncaughtExceptionHandler(new ErrorWindow(null));

        GraphicsInterface graphicsContext = new Graphics3DContext();
        TimingContext timingContext = new TimingContext();
        InputContext inputContext = new InputContext();

        GameEngine gameEngine = new GameEngine(graphicsContext, timingContext, inputContext, new Music("Smoke2.xml", "Smoke.mp3"));

        graphicsContext.addGraphicsUpdateEventListener(gameEngine);
        inputContext.addInputEventListener(gameEngine);

        gameEngine.start();
    }
}
