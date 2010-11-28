/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ErrorWindow.java
 *
 * Created on 27/11/2010, 22:01:34
 */

package guitarjava.gui;

import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import javax.swing.JOptionPane;

/**
 * Error window class.
 * @author lucasjadami
 */
public class ErrorWindow extends javax.swing.JDialog implements Thread.UncaughtExceptionHandler{

    /** Creates new form ErrorWindow */
    public ErrorWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        panel = new javax.swing.JPanel();
        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ops! An Error has occured!");
        setAlwaysOnTop(true);
        setModal(true);
        setName("dialog"); // NOI18N
        setResizable(false);

        label.setText("Sorry, the application had an unespected error and needs to be closed!");
        getContentPane().add(label, java.awt.BorderLayout.PAGE_START);

        textArea.setColumns(40);
        textArea.setEditable(false);
        textArea.setRows(15);
        scrollPane.setViewportView(textArea);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        button.setText("Report");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });
        panel.add(button);

        getContentPane().add(panel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        trueClose();
}//GEN-LAST:event_buttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JLabel label;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

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
        this(null, true);

        // Initializing
        this.mainWindow = mainWindow;
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

    /**
     * Exception happened.
     */
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        showWindow(e);
    }

    private Throwable ex; // Exception
    private Window mainWindow; // The Main Window
    private String url; // Url to go when button pressed
    static private boolean showing;
}
