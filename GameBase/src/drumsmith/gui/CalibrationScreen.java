/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drumsmith.gui;

/**
 *
 * @author Pedro Tiago
 */
public class CalibrationScreen extends javax.swing.JFrame {

    /**
     * Creates new form CalibrationScreen
     */
    public CalibrationScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoTreinar = new javax.swing.JButton();
        botaoVoltar = new javax.swing.JButton();
        botaoChimbal = new javax.swing.JButton();
        botaoPrato = new javax.swing.JButton();
        botaoSurdo = new javax.swing.JButton();
        botaoCaixa = new javax.swing.JButton();
        botaoBumbo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botaoTreinar.setText("Treinar");

        botaoVoltar.setText("Voltar");

        botaoChimbal.setText("Chimbal");

        botaoPrato.setText("Prato");

        botaoSurdo.setText("Surdo");

        botaoCaixa.setText("Caixa");

        botaoBumbo.setText("Bumbo");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Calibração do Sistema");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(botaoTreinar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoVoltar)
                .addGap(55, 55, 55))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(botaoChimbal)
                        .addGap(37, 37, 37)
                        .addComponent(botaoCaixa)
                        .addGap(44, 44, 44)
                        .addComponent(botaoPrato))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(botaoBumbo)
                        .addGap(60, 60, 60)
                        .addComponent(botaoSurdo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel1)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoChimbal)
                    .addComponent(botaoCaixa)
                    .addComponent(botaoPrato))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoBumbo)
                    .addComponent(botaoSurdo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoTreinar)
                    .addComponent(botaoVoltar))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBumbo;
    private javax.swing.JButton botaoCaixa;
    private javax.swing.JButton botaoChimbal;
    private javax.swing.JButton botaoPrato;
    private javax.swing.JButton botaoSurdo;
    private javax.swing.JButton botaoTreinar;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
