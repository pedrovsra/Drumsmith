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

                WindowListener gui = getWindowListeners()[0];
                if (gui != null)
                    gui.windowClosed(new WindowEvent(GameLoading.this, 0));
            }

        };

        super.load();
    }

    public GameEngine getGameEngine()
    {
        return this.gameEngine;
    }

    private void loadGame()
    {
        setState("Creating game window..");
        GameWindow gameWindow = new GameWindow(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
        setProgress(5);

        WindowListener gui = getWindowListeners()[0];
        gameWindow.addWindowListener(gui);

        setState("Creating exception handler..");
        Thread.setDefaultUncaughtExceptionHandler(new guitarjava.components.ErrorWindow(gameWindow, null));
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
        setProgress(90);

        setState("Initializing..");
        gameEngine.init();
        setProgress(100);
    }
}
