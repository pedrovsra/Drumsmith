package guitarjava.game;

import guitarjava.components.GameWindow;
import guitarjava.graphics.GraphicsInterface;
import guitarjava.graphics.GraphicsUpdateListener;
import guitarjava.input.InputEvent;
import guitarjava.input.InputInterface;
import guitarjava.input.InputListener;
import guitarjava.timing.TimingInterface;
import java.util.ArrayList;
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
    private float executionTime;
    private GameWindow window;

    /**
     * Constructor of the engine.
     * @param graphics
     * @param timing
     * @param input
     * @param music Music played in the game.
     */
    public GameEngine(GraphicsInterface graphics, TimingInterface timing,
            InputInterface input, GameWindow window, Music music)
    {
        this.graphics = graphics;
        this.timing = timing;
        this.input = input;
        this.window = window;

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
        input.init(window);
        graphics.init(window);
        timing.init(window);

        graphics.setCamera(Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT / 2,
                Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_HEIGHT / 2, 0);

        graphics.setLightPos(Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT / 2);

        input.addInputEventListener(this);
        graphics.addGraphicsUpdateEventListener(this);

        window.showWindow();

        // Reset the deltaTime.
        timing.getDeltaTime();

        music.play();
    }

    /**
     * Update method. Main loop of the game. All magic is done here
     * @param EventObject
     */
    public void graphicsUpdateEvent(EventObject e)
    {
        // Gets the delta time and update the execuiton time.
        float deltaTime = timing.getDeltaTime();

        if (executionTime == 0)
        {
            executionTime = music.getCurrentPosition();
        }
        else
        {
            executionTime += deltaTime;
        }

        float time = executionTime + Constant.FRAME_DURATION * (Math.abs(Note.ORIGIN_Y)
                + TrackObject.BURNING_POSITION_Y + .25f * TrackObject.DEFAULT_OBJECT_SIZE)
                / Note.PIXELS_JUMP_PER_FRAME;
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
                {
                    music.setSilent(true);
                }

                it.remove();
            }
            else
            {
                if (!note.isPowned())
                {
                    graphics.draw(note.getDrawData());
                }

                if (note.getNoteExtension() != null)
                {
                    graphics.draw(note.getNoteExtension().getDrawData());
                }
            }
        }

        // Do the guitar buttons logic/draw operations.
        for (int i = 0; i < 5; ++i)
        {
            guitarButtons[i].think(deltaTime);

            graphics.draw(guitarButtons[i].getDrawData());

            if (guitarButtons[i].getFlame() != null)
            {
                Iterator itp = guitarButtons[i].getFlame().getParticles().iterator();
                while (itp.hasNext())
                {
                    graphics.draw(((Particle) itp.next()).getDrawData());
                }
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
        if (e.getType() == InputEvent.INPUT_KEYBOARD_PRESSED
                || e.getType() == InputEvent.INPUT_KEYBOARD_RELEASED)
        {
            switch (e.getKeyCode())
            {
                case 'A':
                    track = 0;
                    break;
                case 'S':
                    track = 1;
                    break;
                case 'J':
                    track = 2;
                    break;
                case 'K':
                    track = 3;
                    break;
                case 'L':
                    track = 4;
                    break;
                default:
                    return;
            }
        }
        else
        {
            switch (e.getKeyCode())
            {
                case 6:
                    track = 0;
                    break;
                case 4:
                    track = 1;
                    break;
                case 5:
                    track = 2;
                    break;
                case 7:
                    track = 3;
                    break;
                case 2:
                    track = 4;
                    break;
                default:
                    return;
            }
        }

        GuitarButton guitarButton = guitarButtons[track];

        if (e.getType() == InputEvent.INPUT_KEYBOARD_PRESSED || e.getType() == InputEvent.INPUT_JOYSTICK_PRESSED)
        {
            if (guitarButton.press(getNotesOfTrack(track)))
            {
                music.setSilent(false);
            }
        }
        else if (e.getType() == InputEvent.INPUT_KEYBOARD_RELEASED || e.getType() == InputEvent.INPUT_JOYSTICK_RELEASED)
        {
            guitarButton.unpress();
        }
    }

    private List<Note> getNotesOfTrack(int track)
    {
        List<Note> trackNotes = new ArrayList<Note>();

        Iterator<Note> it = notes.iterator();
        while (it.hasNext())
        {
            Note note = it.next();

            if (note.getTrack() == track && !note.isPowned())
            {
                trackNotes.add(note);
            }
        }

        return trackNotes;
    }
}
