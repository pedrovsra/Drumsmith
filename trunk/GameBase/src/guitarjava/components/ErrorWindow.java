package guitarjava.components;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class to show a fatal error and finalize application. 
 * In the future this class must use components from Components package
 * to make things work. When the window is closed the application is finalized.
 *
 * @author brunojadami
 */
public class ErrorWindow extends JDialog implements ActionListener, Thread.UncaughtExceptionHandler
{

    private Picture background; // Background image
    private Picture intro; // The top message
    private Library library; // Lib to load things
    private Button okButton; // Button to report
    private String url; // Url to go when button pressed
    private Throwable ex; // Exception
    private JScrollPane scrollPane; // Scroll pane of text area

    /**
     * Default constructor.
     */
    public ErrorWindow(String url)
    {
        this.url = url;
    }

    /**
     * Constructor. It will construct the window, call showWindow to show it..
     * @param ex the error
     * @param url the url report to send the user
     */
    public ErrorWindow(Throwable ex, String url)
    {
        // Initializing
        this.url = url;
        this.ex = ex;
    }
    
    /**
     * Shows the error window.
     * @param ex the error
     * @param url the url report to send the user
     */
    public void showWindow(Throwable ex)
    {
        this.ex = ex;
        showWindow();
    }

    /**
     * Shows the error window.
     */
    public void showWindow()
    {
        setTitle("Ops! An Error has occured!");
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
        try
        {
            background = new Picture(library.getPicture("ErrorBackground"), 0, 0);
            okButton = new Button(library.getDefaultFont(), "REPORT!", "REPORT_PRESSED", library.getPicture("ReportButton"), 0, 0);
            setSize(background.getWidth(), background.getHeight() + 30);
            intro = new Picture(library.getPicture("TopMessage"), 0, 0);
            intro.setLocation((background.getWidth() - intro.getWidth()) / 2, 10);
            okButton.addActionListener(this);
            add(intro);
            add(okButton);
            prepareTrace(ex);
            okButton.setLocation((background.getWidth() - okButton.getWidth()) / 2,
                    scrollPane.getY() + scrollPane.getHeight());
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
    private void prepareTrace(Throwable ex)
    {
        String message = ex.toString();
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (int x = 0; x < stackTrace.length; ++x)
        {
            message += "\n\t" + stackTrace[x].toString();
        }
        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        scrollPane = new JScrollPane(txt);
        scrollPane.setBounds(20, intro.getY() + intro.getHeight(), getWidth() - 40, 215);
        txt.setText(message);
        txt.setFont(library.getDefaultFont());
        txt.setForeground(Color.red);
        add(scrollPane);
        txt.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.3f));
        scrollPane.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.3f));
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

    public void uncaughtException(Thread t, Throwable e)
    {
        showWindow(e);
    }
}
