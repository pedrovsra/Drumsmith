package guitarjava.components;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

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
    private Window mainWindow; // The Main Window

    /**
     * Default constructor.
     */
    public ErrorWindow(Window mainWindow, String url)
    {
        this(null, mainWindow, url);
    }

    /**
     * Constructor. It will construct the window, call showWindow to show it..
     * @param ex the error
     * @param url the url report to send the user
     */
    public ErrorWindow(Throwable ex, Window mainWindow, String url)
    {
        // Initializing
        this.mainWindow = mainWindow;
        this.url = url;
        this.ex = ex;
        setTitle("Ops! An Error has occured!");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        library = new Library();
        setModal(true);
    }

    /**
     * Shows the error window.
     * @param ex the error
     * @param url the url report to send the user
     */
    public void showWindow(Throwable ex)
    {
        this.ex = ex;
        ex.printStackTrace();
        showWindow();
    }

    /**
     * Shows the error window.
     */
    public void showWindow()
    {
        // Fires close window to Main Window
        if (mainWindow instanceof JFrame)
            ((JFrame) mainWindow).setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
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
            background = new Picture(library.getPicture("error/ErrorBackground.jpg"), 0, 0);
            okButton = new Button(library.getDefaultFont(), "REPORT!", "REPORT_PRESSED", 
                    library.getPicture("error/ReportButton.png"), 0, 0);
            setSize(background.getWidth(), background.getHeight() + 30);
            intro = new Picture(library.getPicture("error/TopMessage.png"), 0, 0);
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
            setLayout(null);
            Label label = new Label(library.getDefaultFont(), "Could not load ErrorWindow images!", 20, 10);
            label.setForeground(Color.red);
            add(label);
        }
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
        txt.setText(message);
        txt.setFont(library.getDefaultFont());
        txt.setForeground(Color.red);
        scrollPane = new JScrollPane(txt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, intro.getY() + intro.getHeight(), getWidth() - 40, 215);
        add(scrollPane);
        txt.setOpaque(false);
        scrollPane.setOpaque(false);
        //scrollPane.getViewport().setOpaque(false);
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
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("REPORT_PRESSED"))
        {
            trueClose();
        }
    }

    /**
     * Exception happened.
     */
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        showWindow(e);
    }
}
