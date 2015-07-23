package drumsmith.game;

import drumsmith.gui.GameWindow;
import drumsmith.graphics.DrawData;
import drumsmith.graphics.GraphicsInterface;
import drumsmith.graphics.GraphicsUpdateListener;
import drumsmith.gui.ErrorWindow;
import drumsmith.input.InputEvent;
import drumsmith.input.InputInterface;
import drumsmith.input.InputListener;
import drumsmith.timing.TimingInterface;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import src.LineListener;

/**
 * It is the game engine.
 *
 * @author lucasjadami
 */
public class GameEngine implements GraphicsUpdateListener, InputListener, NoteListener {

    private final double TIME_TROUGH_TRACK = (Math.abs(Note.ORIGIN_Y) + TrackObject.BURNING_POSITION_Y
            - TrackObject.DEFAULT_OBJECT_SIZE) / TrackObject.TRACK_DEFAULT_SPEED;
    private GraphicsInterface graphics;
    private TimingInterface timing;
    private InputInterface input;
    private Music music;
    private List<Note> notes;
    private GuitarButton[] guitarButtons;
    private float executionTime;
    private int lastPownedNoteNumber;
    private GameWindow window;
    private Neck neck;
    private float soloMul;
    private float soloEnd;
    private boolean doingSolo;
    private Score score;
    private int trackInc;
    private boolean finished;
    private List<Float> noteTimes;
    private int noteIndex;
    private String path = "temp/note_";
    
    private LineListener line;

    /**
     * @param graphics
     * @param timing
     * @param input
     * @param music Music played.
     */
    public GameEngine(GraphicsInterface graphics, TimingInterface timing,
            InputInterface input, GameWindow window, Music music) {
        this.graphics = graphics;
        this.timing = timing;
        this.input = input;
        this.window = window;

        this.music = music;

        notes = new LinkedList<Note>();
        noteTimes = new ArrayList<Float>();
        this.noteIndex = 0;
        try {
            line = new LineListener();
            line.open();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<NoteXml> aux = this.music.getNotes();

        Iterator<NoteXml> it = aux.iterator();
        float f = 0.0f;
        while (it.hasNext()) {
            if (it.next().getTime() != f) {
                noteTimes.add(it.next().getTime());
                f = it.next().getTime();
                //System.out.println(f);
            }
        }

        //aux.clear();

        guitarButtons = new GuitarButton[5];
        for (int i = 0; i < guitarButtons.length; ++i) {
            guitarButtons[i] = new GuitarButton(i);
        }

        lastPownedNoteNumber = -1;

        neck = new Neck();
        soloMul = 1.0f;
        doingSolo = false;

        score = new Score(music);
    }

    /**
     * Inits the engine.
     */
    public void init() {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });

        input.init(window);
        graphics.init(window);
        timing.init(window);

        graphics.setCamera(Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT / 2,
                Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_HEIGHT / 2, 0);

        graphics.setLightPos(new float[]{Constant.WINDOW_WIDTH / 2, -Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT, 0});
        float[] lightAmbient = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
        graphics.setLight(lightAmbient, lightDiffuse);

        input.addInputEventListener(this);
        graphics.addGraphicsUpdateEventListener(this);
    }

    /**
     * Start method.
     */
    public void start() {
        window.showWindow();
        music.play();
    }

    /**
     * Stops the music.
     */
    public void stop() {
        music.stop();
    }

    /**
     * Update method. Main loop of the game. All magic is done here.
     *
     * @param EventObject
     */
    @Override
    public synchronized void graphicsUpdateEvent(EventObject e) {
        // Allways check if the music got an error while playing.
        if (music.getPlayException() != null) {
            music.stop();
            ErrorWindow error = new ErrorWindow(music.getPlayException(),
                    window, "http://www.google.com");
            error.showWindow();
        }

        // Gets the delta time and update the execution time.
        double deltaTime = timing.getDeltaTime();

        if (executionTime == 0) {
            executionTime = music.getCurrentPosition();
            // Set the deltaTime to zero to don't do wrong calculations.
            deltaTime = 0;
        } else {
            executionTime += deltaTime;
        }

        // Checks if the music hasn't started yet.
        if (executionTime == 0) {
            return;
        }

        double time = (TIME_TROUGH_TRACK + executionTime) / 1000;

        // Creates new notes that need to appear on the track.
        for (NoteXml noteXml = music.getNextNote((float) time); noteXml != null; noteXml = music.getNextNote((float) time)) {
            Note note = new Note(this, noteXml.getNumber(), noteXml.getTrack(), noteXml.getDuration(), noteXml.getTime());
            //System.out.println(noteXml.getTime());
            double deltaY = (time - noteXml.getTime()) * 1000 * TrackObject.TRACK_DEFAULT_SPEED;

            note.forward(deltaY);

            notes.add(note);
        }

        // Updating solo alpha, the blinking effect
        TrackObject.updateSoloAlpha();

        // Updates notes, checking if they are beeing powned or removing them if necessary.
        Iterator<Note> it = notes.iterator();
        while (it.hasNext()) {
            Note note = it.next();
            note.setDoingSolo(doingSolo);
            note.think((float) deltaTime);
            //System.out.println(note.getTime());

            if (!note.isVisible()) {
                it.remove();
            } else {
                if (note.canDraw()) {
                    Iterator<DrawData> d = note.getDrawDatas().iterator();
                    while (d.hasNext()) {
                        graphics.draw(d.next());
                    }
                }

                if (note.getNoteExtension() != null) {
                    Iterator<DrawData> d = note.getNoteExtension().getDrawDatas().iterator();
                    while (d.hasNext()) {
                        graphics.draw(d.next());
                    }
                }
            }
        }

        // Do the guitar buttons logic/draw operations.
        for (int i = 0; i < 5; ++i) {
            guitarButtons[i].setDoingSolo(doingSolo);
            guitarButtons[i].think((float) deltaTime);

            Iterator<DrawData> d = guitarButtons[i].getDrawDatas().iterator();
            while (d.hasNext()) {
                graphics.draw(d.next());
            }

            Iterator<Flame> itF = guitarButtons[i].getFlames().iterator();
            while (itF.hasNext()) {
                Flame flame = itF.next();

                Iterator<Particle> itP = flame.getParticles().iterator();
                while (itP.hasNext()) {
                    d = itP.next().getDrawDatas().iterator();
                    while (d.hasNext()) {
                        graphics.draw(d.next());
                    }
                }
            }
        }

        // Neck uptade.
        neck.setDoingSolo(doingSolo);
        neck.think((float) deltaTime);
        Iterator<DrawData> d = neck.getDrawDatas().iterator();
        while (d.hasNext()) {
            DrawData n = d.next();
            n.setColorMul(soloMul);
            graphics.draw(n);
        }

        // Solo check.
        if (doingSolo) {
            if (soloMul > 0.1f) {
                soloMul -= 0.01f;
            }
            if (soloEnd < (float) time) {
                doingSolo = false;
                graphics.setClearColor(new float[]{0, 0, 0, 1.0f});
            }
        } // Back to normal
        else if (soloMul < 1.0f) {
            soloMul += 0.01f;
        } else {
            // Gets new solos
            SoloXml soloXml = music.getNextSolo((float) time);
            if (soloXml != null) {
                doingSolo = true;
                soloEnd = soloXml.getTime() + soloXml.getDuration();
            }
        }

        // Score
        score.think((float) deltaTime);
        graphics.draw(score.getDrawDatas().getFirst());
        for (int i = 1; i < score.getDrawDatas().size(); i++) {
            graphics.draw(score.getDrawDatas().get(i));
        }
        //graphics.draw(score.getDrawDatas().get(2));

        // Check if song finished
        if ((int) time - 5 >= music.getLength()) {
            finished = true;
            window.setVisible(false);
            window.dispose();
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
        
        //System.out.println(Math.abs((executionTime/1000) - noteTimes.get(noteIndex)));
        if(Math.abs((executionTime/1000) - noteTimes.get(noteIndex)) <= 0.100) {
            //path += 
            //noteIndex++;
            
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        //System.out.println(noteIndex++);
                            line.record(path + noteIndex++);
                            Thread.sleep(300);
                            line.stop();
                    } catch (IOException ex) {
                        Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                        
                    
                }
            }).start();
            
        }
    }

    /**
     * Input event method.
     *
     * @param e Event object.
     */
    @Override
    public synchronized void inputEvent(InputEvent e) {
        int track = -1;
        if (e.getType() == InputEvent.INPUT_KEYBOARD_PRESSED
                || e.getType() == InputEvent.INPUT_KEYBOARD_RELEASED) {
            switch (e.getKeyCode()) {
                case 'A':
                    track = 0;
                    break;
                case 'S':
                    track = 1;
                    break;
                case 'H':
                    track = 2;
                    break;
                case 'J':
                    track = 3;
                    break;
                case 'K':
                    track = 4;
                    break;
                default:
                    return;
            }
        } else {
            switch (e.getKeyCode()) {
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

        if (e.getType() == InputEvent.INPUT_KEYBOARD_PRESSED || e.getType() == InputEvent.INPUT_JOYSTICK_PRESSED) {
            ++trackInc;
            if (trackInc == 5) {
                trackInc = 0;
            }
            // Update solo color
            if (doingSolo) {
                Color col = TrackObject.getColorByTrack(trackInc);
                graphics.setClearColor(new float[]{col.getRed() / 256f, col.getGreen() / 256.f, col.getBlue() / 256f, 1.0f});
            }
            // Press button, checking if missed
            int noteNumber = guitarButton.press(getNotesOfTrack(track));
            if (noteNumber == -1) {
                music.setSilent(true);
                score.resetStreak();
            } else {
                music.setSilent(false);
                lastPownedNoteNumber = noteNumber;
                score.addToScore(1);
                score.incStreak();
            }
        } else if (e.getType() == InputEvent.INPUT_KEYBOARD_RELEASED || e.getType() == InputEvent.INPUT_JOYSTICK_RELEASED) {
            if (doingSolo) {
                //Color col = createColor();
                //graphics.setClearColor(new float[] { col.getRed()/256f, col.getGreen()/256.f, col.getBlue()/256f, 1.0f });
            }
            guitarButton.unpress();
        }
    }

    @Override
    public void proccessMissEvent(int noteNumber) {
        if (noteNumber >= lastPownedNoteNumber) {
            music.setSilent(true);
        }
    }

    /**
     *
     * @param track The track.
     * @return The notes that are not powned of the track.
     */
    private List<Note> getNotesOfTrack(int track) {
        List<Note> trackNotes = new ArrayList<Note>();

        Iterator<Note> it = notes.iterator();
        while (it.hasNext()) {
            Note note = it.next();

            if (note.getTrack() == track && !note.isPowned()) {
                trackNotes.add(note);
            }
        }

        return trackNotes;
    }

    /**
     * @return if there is a beaten high score
     */
    public boolean hasHighScore() {
        return score.getScore() > music.getHighScore() && finished;
    }

    /**
     * Saves the high score.
     *
     * @param player the player name
     * @return True if success.
     */
    public boolean saveHighScore(String player) {
        return music.saveHighScore(player, score.getScore());
    }
}
