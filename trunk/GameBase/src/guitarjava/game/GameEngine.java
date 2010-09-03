package guitarjava.game;

import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputEvent;
import guitarjava.input.InputInterface;
import guitarjava.input.InputListener;
import guitarjava.timing.TimingInterface;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * It is the game engine.
 * @author lucasjadami
 */
public class GameEngine implements GraphicsUpdateListener, InputListener
{
    private GraphicsInterface graphics;
    private TimingInterface timing;
    private InputInterface input;
    private Music music;
    private List<Note> notes;
    private GuitarButton[] guitarButtons;
    private double executionTime;

    /**
     * Constructor of the engine.
     * @param graphics
     * @param timing
     * @param input
     * @param music Music played in the game.
     */
    public GameEngine(GraphicsInterface graphics, TimingInterface timing,
            InputInterface input, Music music)
    {
        this.graphics = graphics;
        this.timing = timing;
        this.input = input;

        this.music = music;

        notes = new LinkedList<Note>();

        guitarButtons = new GuitarButton[5];
        for (int i = 0; i < guitarButtons.length; ++i)
        {
            guitarButtons[i] = new GuitarButton();
        }
    }

    /**
     * Update method. Main loop of the game. All magic is done here
     * @param EventObject
     */
    public void graphicsUpdateEvent(EventObject e)
    {
        // Gets the delta time and update the execuiton time.
        double deltaTime = timing.getDeltaTime();
        executionTime += deltaTime;

        // Creates new notes that need to appear on the track.
        for (NoteXml noteXml = music.getNextNote(executionTime); noteXml != null; 
            noteXml = music.getNextNote(executionTime))
        {
            Note note = new Note();
            notes.add(note);
        }

        // Updates notes, checking if they are beeing powned or removing them if necessary.
        Iterator<Note> it = notes.iterator();
        while (it.hasNext())
        {
            Note note = it.next();
            note.think(deltaTime);

            if (!note.isVisible())
            {
                // The note needs to be removed. Checks if it was missed and removes.
                if (!note.isPowned())
                    music.setSilent(true);

                notes.remove(note);
            }
            else
            {
                // Checks if the note is going to be powned.
                GuitarButton guitarButton = guitarButtons[note.getTrack()];

                if (!note.isPowned() && guitarButton.collide(note))
                    music.setSilent(false);

                graphics.draw(note.getDrawData());
            }
        }
    }

    /**
     *
     * @param e
     */
    public void inputEvent(InputEvent e)
    {
        int track = -1;

        switch (e.getKeyCode())
        {
            case 'a':
                track = 0; break;
            case 's':
                track = 1; break;
            case 'd':
                track = 2; break;
            case 'f':
                track = 3; break;
            case 'g':
                track = 4; break;
            default:
                return;
        }

        GuitarButton guitarButton = guitarButtons[track];

        if (e.getType() == InputEvent.INPUT_PRESSED)
            guitarButton.setPressed(true);
        else if (e.getType() == InputEvent.INPUT_RELEASED)
            guitarButton.setPressed(false);
    }
}
