package guitarjava.components;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The Game Window.
 * @author brunojadami
 */
public class GameWindow extends JFrame
{
    /**
     * Constructor. Call showWindow to show it.
     * @param width the window width
     * @param height the window height
     */
    public GameWindow(int width, int height)
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIgnoreRepaint(true);
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    /**
     * Shows de window.
     */
    public void showWindow()
    {
        setVisible(true);
        requestFocus();
    }
}
