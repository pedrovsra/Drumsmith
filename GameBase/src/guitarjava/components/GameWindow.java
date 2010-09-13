package guitarjava.components;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
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
        setUndecorated(true);
    }

    /**
     * Shows de window.
     */
    public void showWindow()
    {
        requestFocus();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//gd.setFullScreenWindow(this);
        final Window window = this;
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    dispose();
                }
            }
        });
        setVisible(true);
    }
}
