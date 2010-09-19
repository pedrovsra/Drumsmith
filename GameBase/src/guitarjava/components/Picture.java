package guitarjava.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.JPanel;

/**
 *
 * @author lucasjadami
 */
public class Picture extends JPanel
{
    private Image image;
    private ImageObserver observer;

    public Picture(Image image, ImageObserver observer, int x, int y) throws NullPointerException
    {
        if (image == null || observer == null)
        {
            throw new NullPointerException();
        }

        this.image = image;
        setSize(image.getWidth(observer), image.getHeight(observer));

        this.observer = observer;

        setLocation(x, y);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, image.getWidth(observer), image.getHeight(observer), observer);
    }
}
