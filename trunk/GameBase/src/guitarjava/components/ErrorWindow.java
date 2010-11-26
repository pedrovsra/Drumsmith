package guitarjava.components;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private Button viewErrorButton; // Button to see the exception
    private Button okButton; // Button to report
    private String url; // Url to go when button pressed
    private Throwable ex; // Exception
    private ScrollPane scrollPane; // Scroll pane of text area
    private Window mainWindow; // The Main Window
    static private boolean showing;

    /**
     * If an error has ocurred and error windows is being showed.
     */
    static public boolean hasError()
    {
	return showing;
    }
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
	ErrorWindow.showing = true;
        // Fires close window to Main Window
        mainWindow.setVisible(false);
        mainWindow.dispose();
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
            Image backgroundImage = library.getPicture("ErrorBackground.jpg", Library.Package.ERROR);
            Image introImage = library.getPicture("TopMessage.png", Library.Package.ERROR);
            Image buttonImage = library.getPicture("ReportButton.png", Library.Package.ERROR);
            Image scrollPanelImage = library.getPicture("ErrorPanelBackground.png", Library.Package.ERROR);

            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(backgroundImage, 0);
            tracker.addImage(introImage, 1);
            tracker.addImage(buttonImage, 2);
            tracker.addImage(scrollPanelImage, 3);

            tracker.waitForAll();

            background = new Picture(backgroundImage, this, 0, 0);

            setSize(background.getWidth(), background.getHeight() + 30);
            
            intro = new Picture(introImage, this, 0, 0);
            intro.setLocation((background.getWidth() - intro.getWidth()) / 2, 10);  

            viewErrorButton = new Button(library.getDefaultFont(), "SHOW", 
                    "VIEW_PRESSED", buttonImage, 0, 0);
            viewErrorButton.addActionListener(this);
            viewErrorButton.setLocation((intro.getX() + intro.getWidth() -
                    viewErrorButton.getWidth()),
                    intro.getY() + intro.getHeight() + 4);
            
            okButton = new Button(library.getDefaultFont(), "REPORT!", 
                    "REPORT_PRESSED", buttonImage, 0, 0);
            okButton.addActionListener(this);
            okButton.setLocation(intro.getX(),
                    intro.getY() + intro.getHeight() + 4);

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setFont(library.getDefaultFont());
            textArea.setForeground(Color.WHITE);
            textArea.setOpaque(false);

            scrollPane = new ScrollPane(scrollPanelImage, textArea, 20, intro.getY() + intro.getHeight() + 4,
                getWidth() - 40, 215);

            textArea.setText(prepareTrace(ex));

            scrollPane.setVisible(false);

            getContentPane().add(intro);
            getContentPane().add(viewErrorButton);
            getContentPane().add(okButton);
            getContentPane().add(scrollPane);
            getContentPane().add(background);
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
    private String prepareTrace(Throwable ex) throws Exception
    {
        String message = ex.toString();
        StackTraceElement[] stackTrace = ex.getStackTrace();

        for (int x = 0; x < stackTrace.length; ++x)
        {
            message += "\n\t" + stackTrace[x].toString();
        }

        return message;
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
            catch (Exception e)
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

    private void changeTraceView()
    {
        int viewErrorButtonY = 0;

        if (!scrollPane.isVisible())
        {
            scrollPane.setVisible(true);
            viewErrorButtonY = scrollPane.getY() + scrollPane.getHeight() + 4;
        }
        else
        {
            scrollPane.setVisible(false);
            viewErrorButtonY = intro.getY() + intro.getHeight() + 4;
        }

        viewErrorButton.setLocation(viewErrorButton.getX(), viewErrorButtonY);
        okButton.setLocation(okButton.getX(), viewErrorButtonY);
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
        else if (e.getActionCommand().equals("VIEW_PRESSED"))
        {
            changeTraceView();
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
