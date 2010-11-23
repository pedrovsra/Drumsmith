package guitarjava.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author lucasjadami
 */
public class Loading extends javax.swing.JFrame
{

    /** Creates new form Loading */
    public Loading()
    {
        initComponents();
        centralize();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        upperPanel = new javax.swing.JPanel();
        stateLabel = new javax.swing.JLabel();
        lowerPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setUndecorated(true);

        stateLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stateLabel.setText("Loading..");
        stateLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        stateLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stateLabel.setPreferredSize(new java.awt.Dimension(400, 15));
        upperPanel.add(stateLabel);

        getContentPane().add(upperPanel, java.awt.BorderLayout.PAGE_START);

        lowerPanel.setLayout(new java.awt.BorderLayout());
        lowerPanel.add(progressBar, java.awt.BorderLayout.CENTER);

        getContentPane().add(lowerPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JPanel upperPanel;
    // End of variables declaration//GEN-END:variables

    protected Thread loadThread;
    
    public void load()
    {
        if (loadThread != null)
            loadThread.start();
    }
    
    protected void setState(String state)
    {
        stateLabel.setText(state);
    }

    protected void setProgress(int progress)
    {
        progressBar.setValue(progress);
    }

    protected int getProgress()
    {
        return progressBar.getValue();
    }

    private void centralize()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);
    }
}