package guitarjava.components;

import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author lucasjadami
 */
public class Library
{
    public enum Package
    {
        ERROR
    }

    private Map<String, Image> pictures;
    private Font defaultFont;

    public Library()
    {
        pictures = new TreeMap<String, Image>();
        defaultFont = new Font("arial black", Font.BOLD, 12);
    }

    public Image getPicture(String name, Package p) throws Exception
    {
        if (p == Package.ERROR)
            return getImage(name, Library.class.getResource("/guitarjava/source/error/" + name), pictures);

        return null;
    }

    public Font getDefaultFont()
    {
        return defaultFont;
    }

    private Image getImage(String name, URL url, Map<String, Image> images)
            throws Exception
    {
        Image image = null;

        image = images.get(name);

        if (image == null)
        {
            image = Toolkit.getDefaultToolkit().getImage(url);

            if (image == null)
            {
                throw new Exception("Could not load the image: '" + name + "'.");
            }
            
            images.put(name, image);
        }

        return image;
    }
}
