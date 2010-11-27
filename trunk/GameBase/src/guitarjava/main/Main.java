package guitarjava.main;

import guitarjava.gui.Gui;
import javax.swing.UIManager;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;


/**
 *
 * @author brunojadami
 */
public class Main
{

    /**
     * Main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
        Gui gui = new Gui();
    }
}
