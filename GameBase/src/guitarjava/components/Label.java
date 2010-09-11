package guitarjava.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JLabel;

/**
 *
 * @author lucasjadami
 */
public class Label extends JLabel
{

    public Label(Font font, String text, int x, int y) throws NullPointerException
    {
        if (font == null)
        {
            throw new NullPointerException();
        }

        setFont(font);
        setText(text);
        setForeground(Color.white);

        setLocation(x, y);

        FontMetrics fontMetrics = getFontMetrics(font);

        setSize(fontMetrics.stringWidth(text), getFont().getSize());
    }
}
