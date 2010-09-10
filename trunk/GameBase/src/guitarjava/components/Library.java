package guitarjava.components;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Lucas
 */
public class Library
{

    private Map<String, BufferedImage> pictures;
    private Map<String, BufferedImage> things;
    private Font defaultFont;

    public Library()
    {
        pictures = new TreeMap<String, BufferedImage>();
        things = new TreeMap<String, BufferedImage>();
        defaultFont = new Font("arial black", Font.BOLD, 12);
    }

    public BufferedImage getPicture(String name) throws Exception
    {
        return getImage(name, pictures);
    }

    public BufferedImage getThing(String name) throws Exception
    {
        return getImage(name, things);
    }

    public Font getDefaultFont()
    {
        return defaultFont;
    }

    private BufferedImage getImage(String name, Map<String, BufferedImage> images) throws Exception
    {
        BufferedImage image = null;

        image = images.get(name);

        if (image == null)
        {
            image = ImageIO.read(new File(name + ".png"));

            if (image == null)
            {
                throw new Exception("Could not load the image: '" + name + "'.");
            }

            images.put(name, image);
        }

        return image;
    }
}
