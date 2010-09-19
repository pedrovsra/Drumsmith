/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guitarjava.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author lucasjadami
 */
public class ScrollPane extends JScrollPane
{
    private Image background;

    public ScrollPane(Image background, JTextArea textArea, int x, int y, int width, int height)
    {
        super(textArea, VERTICAL_SCROLLBAR_ALWAYS,
                HORIZONTAL_SCROLLBAR_NEVER);

        setBounds(x, y, width, height);
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(new EmptyBorder(7, 7, 7, 7));
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.drawImage(background, 0, 0, null);
        super.paintComponent(g);
    }
}
