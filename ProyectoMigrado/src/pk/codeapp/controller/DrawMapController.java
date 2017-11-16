package pk.codeapp.controller;
import pk.codeapp.model.Dupla;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pk.codeapp.model.Frame;

/**
 *
 * @author josep
 */
public class DrawMapController extends JPanel
{

    private int size = 80;
    Frame startMaze = MazeController.startMaze;
    BufferedImage imageWall;
    BufferedImage imageFloor;

    /**
     * Defaul constuctor
     */
    public DrawMapController()
    {
        try {
            this.imageWall = ImageIO.read(new File("src/pk/codeapp/view/tools/Wall.jpg"));
            this.imageFloor = ImageIO.read(new File("src/pk/codeapp/view/tools/green.jpg"));
        } catch (IOException e) {
        }
        this.setBounds(0, 0, 800, 800);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

    }

    /**
     * Paint the elements in the windows
     *
     * @param g
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Frame reco = startMaze;
                while (reco != null) {
                    if (reco.getRow() == i && reco.getColumn() == j) {
                        Dupla XY = new Dupla(reco.getRow(), reco.getColumn());
                        //System.out.println("Posicion en X: " + reco.getRow()+ "Posicion en Y: " + reco.getColumn());
                        if (reco.isAllow()) {
                            g.drawImage(imageFloor, calculatePosition(XY).getPosX(), calculatePosition(XY).getPosY(), this);
                        } else {
                            g.drawImage(imageWall, calculatePosition(XY).getPosX(), calculatePosition(XY).getPosY(), this);
                        }
                        

                        if (reco.getBonus() != null) {
                            BufferedImage img = null;
                            try {
                                img = ImageIO.read(new File(reco.getBonus().getBonusImage()));
                            } catch (IOException e) {
                                
                            }
                            if (img != null) {
                                g.drawImage(img, calculatePosition(XY).getPosX() + 5, calculatePosition(XY).getPosY(), this);
                            }

                        }
//                        g.setColor(java.awt.Color.YELLOW);
//                        g.setFont(new Font("Vendara", Font.PLAIN, 15));
//                        g.drawString(reco.getName() + "", (int) calculatePosition(XY).getPosX() + 25, (int) calculatePosition(XY).getPosY() + 40);
//                        break;
                    }
                    reco = reco.getNextFrame();
                }
            }
        }
    }

    /**
     * Calculate the real position in graphic matrix
     *
     * @param pos
     * @return
     */
    public Dupla calculatePosition(Dupla pos)
    {
        int x = (int) (pos.getPosX() * 80 + 0.0);
        int y = (int) (pos.getPosY() * 80 + 0.0);
        return new Dupla(x, y);
    }
}
