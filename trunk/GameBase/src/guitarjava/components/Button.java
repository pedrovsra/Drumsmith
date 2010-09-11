package guitarjava.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author lucasjadami
 */
public class Button extends JButton
{

    private Image image;

    public Button(Font font, String title, String actionCommand, Image image, int x, int y) throws NullPointerException
    {
        if (font == null || image == null)
        {
            throw new NullPointerException();
        }

        setFont(font);
        setText(title);
        setForeground(Color.WHITE);

        setActionCommand(actionCommand);

        setOpaque(false);
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        setLocation(x, y);

        this.image = image;
        setSize(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public void processMouseEvent(MouseEvent e)
    {
        if (e.getID() == MouseEvent.MOUSE_ENTERED)
        {
            setForeground(Color.red);
        }
        else if (e.getID() == MouseEvent.MOUSE_EXITED)
        {
            setForeground(Color.white);
        }
        else
        {
            super.processMouseEvent(e);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
        super.paintComponent(g);
    }
}
