
package pk.codeapp.view;

import javax.swing.JLabel;
import pk.codeapp.controller.DrawMapController;
import pk.codeapp.controller.MazeController;

public class GameWindows extends javax.swing.JFrame {

    /**
     * Creates new form windowGame
     */
    DrawMapController mapGame;
    MazeController maze;

    public GameWindows() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        maze = new MazeController();
        mapGame = new DrawMapController();
        this.add(mapGame);
       
   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
<<<<<<< HEAD
        imageCharacter2 = new javax.swing.JLabel();
        imageCharacter3 = new javax.swing.JLabel();
        imageCharacter1 = new javax.swing.JLabel();
=======
>>>>>>> developer

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 800));
<<<<<<< HEAD
        jPanel1.setLayout(null);

        imageCharacter2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/Character2.gif"))); // NOI18N
        imageCharacter2.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter2);
        imageCharacter2.setBounds(540, 100, 72, 72);

        imageCharacter3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/character3.gif"))); // NOI18N
        imageCharacter3.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter3);
        imageCharacter3.setBounds(70, 120, 80, 80);

        imageCharacter1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/character1.gif"))); // NOI18N
        imageCharacter1.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter1);
        imageCharacter1.setBounds(340, 270, 80, 80);
=======
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
>>>>>>> developer

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindows().setVisible(true);
            }
        });
    }

    //<editor-fold desc="getter and setter" defaultstate="collapsed">
   

    //</editor-fold>
    
    public JLabel getImageCharacter1() {
        return imageCharacter1;
    }

    public JLabel getImageCharacter2() {
        return imageCharacter2;
    }

    //</editor-fold>
    public JLabel getImageCharacter3() {
        return imageCharacter3;
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
<<<<<<< HEAD
    public static javax.swing.JLabel imageCharacter1;
    public static javax.swing.JLabel imageCharacter2;
    public static javax.swing.JLabel imageCharacter3;
=======
>>>>>>> developer
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
