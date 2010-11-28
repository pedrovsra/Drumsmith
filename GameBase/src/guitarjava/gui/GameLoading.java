/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guitarjava.gui;

import guitarjava.game.Constant;
import guitarjava.game.GameEngine;
import guitarjava.game.Music;
import guitarjava.graphics.Graphics3DContext;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.input.InputNoRepeatContext;
import guitarjava.timing.TimingContext;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This class load the game.
 * @author lucasjadami
 */
public class GameLoading extends Loading
{
    private GameEngine gameEngine; // Game engine.
    private Music musicToPlay; // Music to play.
    private GameWindow gameWindow; // The game window.

    /**
     * @param musicToPlay The music to play on the game.
     */
    public GameLoading(Music musicToPlay)
    {
        this.musicToPlay = musicToPlay;
    }

    /**
     * Loads the game.
     */
    @Override
    public void load()
    {
        loadThread = new Thread()
        {

            @Override
            public void run()
            {
                loadGame();

                for (WindowListener gui : getWindowListeners())
                    gui.windowClosed(new WindowEvent(GameLoading.this, 0));
            }

        };

        super.load();
    }

    /**
     * @return The game engine ready to be played.
     */
    public GameEngine getGameEngine()
    {
        return this.gameEngine;
    }

    /**
     * @return The game window.
     */
    public GameWindow getGameWindow()
    {
        return this.gameWindow;
    }

    /**
     * Loads the game.
     */
    private void loadGame()
    {
        setState("Creating game window..");
        gameWindow = new GameWindow(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        setProgress(5);

        for (WindowListener gui : getWindowListeners())
           gameWindow.addWindowListener(gui);

        setState("Creating exception handler..");
        Thread.setDefaultUncaughtExceptionHandler(new guitarjava.gui.ErrorWindow(gameWindow, "http://www.google.com"));
        setProgress(15);

        setState("Creating graphics context..");
        GraphicsInterface graphicsContext = new Graphics3DContext();
        setProgress(30);

        setState("Creating timing context..");
        TimingContext timingContext = new TimingContext();
        setProgress(45);

        setState("Creating input context..");
        InputNoRepeatContext inputContext = new InputNoRepeatContext();
        setProgress(60);

        try
        {
            setState("Loading music notes..");
            musicToPlay.readNotes();
            setProgress(80);
        }
        catch (Exception ex)
        {
            // TODO
        }

        setState("Creating game engine..");
        gameEngine = new GameEngine(graphicsContext, timingContext, inputContext,
                gameWindow, musicToPlay);
        setProgress(93);

        setState("Initializing..");
        gameEngine.init();
        setProgress(100);
    }
}
