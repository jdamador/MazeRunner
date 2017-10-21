package pk.codeapp.controller;

import pk.codeapp.model.Dupla;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import pk.codeapp.model.Frame;

/**
 *
 * @author josep
 */
public class DrawMapController extends JPanel {
    private int size = 80;
    Frame startMaze = MazeController.startMaze;
    BufferedImage imageWall;
    BufferedImage imageFloor;
    public DrawMapController()  {
        try{
        this.imageWall = ImageIO.read(new File("src/pk/codeapp/view/tools/Wall.jpg"));
        this.imageFloor = ImageIO.read(new File("src/pk/codeapp/view/tools/green.jpg"));
        }catch(IOException e){}
        this.setBounds(0, 0, 800, 800);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame reco = startMaze;
                while (reco != null) {
                    Dupla XY = new Dupla(reco.getColumn(), reco.getRow());
                    if (reco.getRow() == i && reco.getColumn() == j) {
                       // System.out.println("Posicion en X: " + reco.getColumn() + "Posicion en Y: " + reco.getRow());
                        if (reco.isAllow()) {
                            g.drawImage(imageFloor, calculatePosition(XY).getPosX(), calculatePosition(XY).getPosY(), this);
                        } else {
                            g.drawImage(imageWall, calculatePosition(XY).getPosX(), calculatePosition(XY).getPosY(), this);
                        }
                        
                        break;
                    } else {
                        reco = reco.getNextFrame();
                    }

                }
            }
        }
    }


    public Dupla calculatePosition(Dupla pos) {
        int x = (int) (pos.getPosX() * 80 + 0.0);
        int y = (int) (pos.getPosY() * 80 + 0.0);
        return new Dupla(x, y);
    }
}
