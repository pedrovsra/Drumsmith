package guitarjava.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author lucasjadami
 */
public class Picture extends JPanel
{

    protected Image image;

    public Picture(Image image, int x, int y) throws NullPointerException
    {
        if (image == null)
        {
            throw new NullPointerException();
        }

        this.image = image;
        setSize(image.getWidth(null), image.getHeight(null));

        setLocation(x, y);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
    }
}
