package pk.codeapp.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import pk.codeapp.controller.DrawMapController;
import pk.codeapp.controller.MazeController;

public class GameWindows extends javax.swing.JFrame
{

    /**
     * Creates new form windowGame
     */
    DrawMapController mapGame;
    MazeController maze;
    AudioClip sound;
    File audio = new File("src/pk/codeapp/view/tools/song.wav");

    public GameWindows()
    {
        initComponents();
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audio);
        } catch (UnsupportedAudioFileException ex) {
          
        } catch (IOException ex) { 
           
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
              clip.open(audioInputStream);
              FloatControl gainControl= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
              gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
        } catch (LineUnavailableException ex) {
            
        } catch (IOException ex) {
           
        }
        clip.start();
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
        imageCharacter2 = new javax.swing.JLabel();
        imageCharacter3 = new javax.swing.JLabel();
        imageCharacter1 = new javax.swing.JLabel();
        objective = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 800));
        setSize(new java.awt.Dimension(800, 800));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 800));
        jPanel1.setLayout(null);

        imageCharacter2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/character2.gif"))); // NOI18N
        imageCharacter2.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter2);
        imageCharacter2.setBounds(440, 150, 80, 80);

        imageCharacter3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/character3.gif"))); // NOI18N
        imageCharacter3.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter3);
        imageCharacter3.setBounds(120, 240, 80, 80);

        imageCharacter1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageCharacter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/character1.gif"))); // NOI18N
        imageCharacter1.setBorder(new javax.swing.border.MatteBorder(null));
        imageCharacter1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(imageCharacter1);
        imageCharacter1.setBounds(360, 40, 80, 80);

        objective.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        objective.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pk/codeapp/view/tools/trofeo.png"))); // NOI18N
        objective.setBorder(new javax.swing.border.MatteBorder(null));
        objective.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(objective);
        objective.setBounds(320, 140, 80, 80);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new GameWindows().setVisible(true);
            }
        });
    }

    //<editor-fold desc="getter and setter" defaultstate="collapsed">
    //</editor-fold>
    public JLabel getImageCharacter1()
    {
        return imageCharacter1;
    }

    public JLabel getImageCharacter2()
    {
        return imageCharacter2;
    }

    //</editor-fold>
    public JLabel getImageCharacter3()
    {
        return imageCharacter3;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel imageCharacter1;
    public static javax.swing.JLabel imageCharacter2;
    public static javax.swing.JLabel imageCharacter3;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel objective;
    // End of variables declaration//GEN-END:variables
}
