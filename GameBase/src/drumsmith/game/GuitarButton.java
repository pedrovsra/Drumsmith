package drumsmith.game;

import drumsmith.graphics.DrawData;
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the button where when the notes are passing trough, you should
 * press the key.
 *
 * @author lucasjadami
 */
public class GuitarButton extends TrackObject implements BurningInterface {

    private boolean pressed;
    private List<Flame> flames;
    private DrawData bp;
    private DrawData bu;

    /**
     * @param track Track where it is located.
     */
    public GuitarButton(int track) {
        super(track, BURNING_POSITION_Y, 1, DEFAULT_OBJECT_SIZE / 2, DEFAULT_OBJECT_SIZE / 2, Constant.CACHEID_BUTTON);

        this.track = track;

        flames = new LinkedList<Flame>();

        drawDatas.getFirst().createAs2DFilledCircle((float) width);
        bu = drawDatas.getFirst();
        bp = new DrawData(Constant.CACHEID_PBUTTON);
        bp.createAs2DFilledCircle((float) width);
        bp.setColor(GuitarButton.getColorByTrack(track));
        bp.setPosition((float) (START_X + TRACK_SPACEMENT * (track + 1)), BURNING_POSITION_Y, 1);
    }

    @Override
    public void think(float deltaTime) {
        updateSolo();
        Iterator<Flame> it = flames.iterator();
        while (it.hasNext()) {
            Flame flame = it.next();
            flame.setDoingSolo(doingSolo);
            flame.think(deltaTime);

            if (flame.canExtinguish()) {
                it.remove();
            }
        }
    }

    /**
     * Set the button unpressed.
     */
    public void unpress() {
        pressed = false;
        drawDatas.removeFirst();
        drawDatas.add(bu);
    }

    /**
     * @param notes Notes of the button track.
     * @return The number of the note that was powned. If no note was powned,
     * returns -1.
     */
    public int press(List<Note> notes) {
        pressed = true;
        drawDatas.removeFirst();
        drawDatas.add(bp);

        Iterator<Note> it = notes.iterator();
        while (it.hasNext()) {
            Note note = it.next();

            boolean result = collide(note);
            if (result) {
                return note.getNumber();
            }
        }

        return -1;
    }

    /**
     * @return The flame.
     */
    public List<Flame> getFlames() {
        return flames;
    }

    /**
     * @return True if it is pressed.
     */
    @Override
    public boolean isBurning() {
        return pressed;
    }

    /**
     * Test the collision. If it happens, creates a flame.
     *
     * @param note The note to test the collision.
     * @return True if the collision happened.
     */
    private boolean collide(Note note) {
        if (note.getY() + note.getHeight() / 2 > y - height && note.getY() + note.getHeight() / 2
                < y + height + note.getHeight()) {
            // Passes this to the note as BurningState so the track extension of the note can check if
            // it still have to burn.
            note.setPowned(this);

            // After powning the note, calculates the flame duration and create it.
            double duration = DEFAULT_OBJECT_SIZE / TRACK_DEFAULT_SPEED;
            double totalDuration = duration + note.getDuration() * 1000;

            Flame flame = new Flame(this, track, totalDuration, doingSolo);
            flames.add(flame);

            return true;
        }

        return false;
    }
}
