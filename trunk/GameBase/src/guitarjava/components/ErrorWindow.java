package guitarjava.components;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Class to show a fatal error and finalize application. To use it you need the
 * ErrorBackground.png and ReportButton.png. In the future this class must use
 * components from Components package to make things work. When the window is
 * closed the application is finalized.
 *
 * @author brunojadami
 */
public class ErrorWindow extends JDialog implements ActionListener
{

    private Picture background; // Background image
    private Library library; // Lib to load things
    private Button okButton; // Button to report
    private String url; // Url to go when button pressed
    private Exception ex; // Exception

    /**
     * Constructor. It will construct the window, call showWindow to show it..
     * @param ex the error
     * @param url the url report to send the user
     */
    public ErrorWindow(Exception ex, String url)
    {
        // Initializing
        this.url = url;
        setTitle("An Error has occured!");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        library = new Library();
        // Adding other events and stuff
        addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosing(WindowEvent we)
            {
                trueClose();
            }
        });
        this.ex = ex;
    }

    /**
     * Shows the error window.
     */
    public void showWindow()
    {
        try
        {
            background = new Picture(library.getPicture("ErrorBackground"), 0, 0);
            okButton = new Button(library.getDefaultFont(), "REPORT!", "REPORT_PRESSED", library.getPicture("ReportButton"), 0, 0);
            setSize(background.getWidth(), background.getHeight() + 30);
            okButton.setLocation((background.getWidth() - okButton.getWidth()) / 2, (background.getHeight() - okButton.getHeight() * 2));
            okButton.addActionListener(this);
            add(okButton);
            prepareTrace(ex);
            add(background);
        }
        catch (Exception e)
        {
            // Images not found
            setSize(300, 65);
            Label label = new Label(library.getDefaultFont(), "Could not load ErrorWindow images!", 20, 10);
            label.setForeground(Color.red);
            add(label);
        }
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Preparing error image and message.
     * @param ex the error
     */
    private void prepareTrace(Exception ex)
    {
        String message = ex.toString();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (int x = 0; x < stackTrace.length; ++x)
        {
            message += "\n\t" + stackTrace[x].toString();
        }
        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setBounds(20, 80, getWidth() - 40, getHeight() - 80);
        txt.setOpaque(false);
        txt.setText(message);
        txt.setFont(library.getDefaultFont());
        txt.setForeground(Color.red);
        txt.setLineWrap(true);
        add(txt);
    }

    /**
     * Finalizing the application and sending user to the url.
     */
    private void trueClose()
    {
        // If I can open it
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                Desktop.getDesktop().browse(new URI(url));
            }
            catch (Exception exc)
            {
                // Not possible to report
                JOptionPane.showMessageDialog(this, "Could not report! Please do it manually by acessing: " + url,
                        "Error Reporting!", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            // Not possible to report
            JOptionPane.showMessageDialog(this, "Could not report! Please do it manually by acessing: " + url,
                    "Error Reporting!", JOptionPane.ERROR_MESSAGE);
        }
        this.setVisible(false);
        this.dispose();
        System.exit(1);
    }

    /**
     * Event happened.
     * @param e the event
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("REPORT_PRESSED"))
        {
            trueClose();
        }
    }
}
