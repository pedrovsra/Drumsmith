/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guitarjava.gui;

import guitarjava.components.GameWindow;
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
 *
 * @author lucasjadami
 */
public class GameLoading extends Loading
{
    private GameEngine gameEngine;
    private Music musicToPlay;
    private GameWindow gameWindow;

    public GameLoading(Music musicToPlay)
    {
        this.musicToPlay = musicToPlay;
    }

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

    public GameEngine getGameEngine()
    {
        return this.gameEngine;
    }

    public GameWindow getGameWindow()
    {
        return this.gameWindow;
    }

    private void loadGame()
    {
        setState("Creating game window..");
        gameWindow = new GameWindow(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        setProgress(5);

        for (WindowListener gui : getWindowListeners())
           gameWindow.addWindowListener(gui);

        setState("Creating exception handler..");
        Thread.setDefaultUncaughtExceptionHandler(new guitarjava.components.ErrorWindow(gameWindow, "http://www.google.com"));
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
            // Just in case the same music was being played in the menu.
            setState("Flushing music..");
            musicToPlay.reopen();
            setProgress(86);
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
