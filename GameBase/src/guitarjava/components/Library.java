package guitarjava.components;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
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
    private Font specialFont;

    public Library() throws Exception
    {
        pictures = new TreeMap<String, Image>();
        defaultFont = new Font("arial black", Font.BOLD, 10);
        specialFont = Font.createFont(Font.TRUETYPE_FONT, Library.class.getResourceAsStream
                ("/guitarjava/source/fonts/NightmareHero.ttf"));
        specialFont = defaultFont.deriveFont(Font.BOLD, 25f);
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
