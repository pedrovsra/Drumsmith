package guitarjava.game;

import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputEvent;
import guitarjava.input.InputInterface;
import guitarjava.input.InputListener;
import guitarjava.timing.TimingInterface;
import java.awt.Window;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javazoom.jl.decoder.JavaLayerException;

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
            guitarButtons[i] = new GuitarButton(i);
        }
    }

    /**
     * Start the music.
     * @throws JavaLayerException
     */
    public void start() throws JavaLayerException
    {
        graphics.init((Window) graphics);
        input.init((Window) graphics);
        timing.init((Window) graphics);

        music.play();
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

        double time = executionTime + Constant.FRAME_DURATION * (GuitarButton.POSITION_Y - TrackObject.OBJECT_SIZE) /
                Note.PIXELS_JUMP_PER_FRAME;
        time /= 1000;

        // Creates new notes that need to appear on the track.
        for (NoteXml noteXml = music.getNextNote(time); noteXml != null; noteXml = music.getNextNote(time))
        {
            Note note = new Note(noteXml.getTrack(), noteXml.getDuration());
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

                it.remove();
            }
            else
            {
                // Checks if the note is going to be powned.
                GuitarButton guitarButton = guitarButtons[note.getTrack()];

                if (!note.isPowned() && guitarButton.collide(note))
                    music.setSilent(false);

                if (!note.isPowned())
                    graphics.draw(note.getDrawData());

                if (note.getNoteExtension() != null)
                    graphics.draw(note.getNoteExtension().getDrawData());
            }
        }

        // Do the guitar buttons logic/draw operations.
        for (int i = 0; i < 5; ++i)
        {
            guitarButtons[i].think(deltaTime);

            graphics.draw(guitarButtons[i].getDrawData());

            if (guitarButtons[i].getFlame() != null)
                graphics.draw(guitarButtons[i].getFlame().getDrawData());
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
            case 'A':
                track = 0; break;
            case 'S':
                track = 1; break;
            case 'J':
                track = 2; break;
            case 'K':
                track = 3; break;
            case 'L':
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
