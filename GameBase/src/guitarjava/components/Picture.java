package guitarjava.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Picture extends JPanel
{

    private Image image;

    public Picture(Image image, int x, int y) throws NullPointerException
    {
        if (image == null)
        {
            throw new NullPointerException();
        }

        setLayout(null);

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
