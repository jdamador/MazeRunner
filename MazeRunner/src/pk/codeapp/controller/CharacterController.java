package pk.codeapp.controller;

import java.util.ArrayList;
import javax.swing.JLabel;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;

/**
 * This method is charge of all character's functions
 *
 * @author amador
 */
public class CharacterController implements Runnable
{

    private int numberCharacters;
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route

    private int counterWeight = 0;
    private int Weight = 0;
    private int posX;
    private int posY;
    private JLabel characterActual;
    private int sleep;
    private Frame characterRoot;
    private Frame graphRoot;
    private Frame destiny;
    /**
     * This method calc a random number
     *
     * @param limit
     * @return
     */
    /**
     * Inicialize the instance of controllerCharacter
     *
     * @param image
     * @param sleep
     * @param posX
     * @param posY
     */
    public CharacterController(JLabel image, int sleep, int posX, int posY, Frame characterRoot, Frame graphRoot,Frame destiny)
    {
        this.characterActual = image;
        this.sleep = sleep;
        this.posX = posX;
        this.posY = posY;
        this.characterRoot = characterRoot;
        this.graphRoot = graphRoot;
        this.destiny=destiny;
    }

    public void movingImage() throws InterruptedException
    {
        
        createGraphForCharacter(characterRoot, graphRoot);
        System.out.println("moving");
        shortRouteJumps(graphRoot,destiny);
        System.out.println(listRouteShort.size());
        System.out.println("moving");
        while(true){
            
            
            characterActual.setLocation(posX++,posY);
            Thread.sleep(sleep);
        }
            
           
        
    }

    public void createGraphForCharacter(Frame characterRoot, Frame graphRoot)
    {
        insertFrameCharacter(characterRoot, graphRoot);
        //Add Linken
        Frame tempPrincipalG = graphRoot;
        Frame root = characterRoot;
        while (tempPrincipalG != null) {
            if (tempPrincipalG.isAllow()) {
                Link aux = tempPrincipalG.getNextLink();
                while (aux != null) {
                    Frame destiny = searchCharacter(aux.getDestiny().getName(), root);
                    insertLinkCharacter(root, destiny, aux.getWeight());
                    aux = aux.getNextLink();
                }
            }
            tempPrincipalG = tempPrincipalG.getNextFrame();
        }
    }

    private void insertFrameCharacter(Frame rootG, Frame principalG)
    {
        //Add Frame
        Frame tempPrincipalTree = principalG;
        while (tempPrincipalTree != null) {
            Frame newFrame = new Frame(tempPrincipalTree.getName(), false, tempPrincipalTree.getRow(), tempPrincipalTree.getColumn());
            if (rootG == null) {
                rootG = newFrame;
            } else {
                if (tempPrincipalTree.isAllow() == true) {
                    newFrame.setNextFrame(rootG);
                    rootG = newFrame;
                }
            }
            tempPrincipalTree = tempPrincipalTree.getNextFrame();
        }
    }

    private void insertLinkCharacter(Frame origen, Frame destiny, int weight)
    {
        Link link = new Link(weight);
        link.setDestiny(destiny);
        if (origen.getNextLink() == null) {
            origen.setNextLink(link);
        } else {
            Link temp = origen.getNextLink();
            while (temp != null) {
                if (temp.getDestiny() == destiny) {
                }
                temp = temp.getNextLink();
            }
            link.setNextLink(origen.getNextLink());
            origen.setNextLink(link);
        }
    }

    private Frame searchCharacter(String name, Frame rootCharacterG)
    {
        Frame recoG = rootCharacterG;
        while (recoG != null) {
            if (recoG.getName().equals(name)) {
                return recoG;
            }
            recoG = recoG.getNextFrame();
        }
        return null;
    }

    public int getRandom(int minimum, int maximum)
    {
        int randomInt = minimum + (int) (Math.random() * maximum);
        return randomInt;
    }

    public void numbreCharacters()
    {
        numberCharacters = getRandom(2, 3);
    }

    public void shortRouteJumps(Frame origin, Frame destination)
    {
        if (origin == null) {
            return;
        } else if (origin.isMark() == true) {
            return;
        } else {
            origin.setMark(true);
            Link aux = origin.getNextLink();
            listRouteAux.add(origin);
            if (aux == null) {
                listRouteAux.remove(listRouteAux.size() - 1);
                return;
            }
            while (aux != null) {
                if (aux.getDestiny().isMark() == true) {
                    aux.getDestiny().setMark(false);
                }
                if (aux.getDestiny().equals(destination)) {
                    if (listRouteShort.isEmpty() || (listRouteAux.size() < listRouteShort.size())) {
                        listRouteShort.clear();
                        for (int i = 0; i < listRouteAux.size(); i++) {
                            listRouteShort.add(listRouteAux.get(i));
                        }
                        listRouteShort.add(aux.getDestiny());
                    }
                }
                shortRouteJumps(aux.getDestiny(), destination);
                aux = aux.getNextLink();
                if (aux == null) {
                    listRouteAux.remove(listRouteAux.size() - 1);
                    return;
                }
            }
        }
    }

    public void clean_Mark(Frame pointerCharacter)
    {
        Frame temp = pointerCharacter;
        if (pointerCharacter == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }

    public void addCharacterFrame(String name, boolean token, int row, int column, boolean allow, Frame rootPointer)
    {
        Frame square = new Frame(name, token, row, column);
        square.setAllow(allow);
        square.setNextFrame(null);
        if (rootPointer == null) {
            rootPointer = square;
        } else {
            square.setNextFrame(rootPointer);
            rootPointer = square;
        }
    }

    public ArrayList<Frame> getListRoute()
    {
        return listRouteAux;
    }

    public void setListRoute(ArrayList<Frame> listRoute)
    {
        this.listRouteAux = listRoute;
    }

    public ArrayList<Frame> getListRouteShort()
    {
        return listRouteShort;
    }

    public void setListRouteShort(ArrayList<Frame> listRouteShort)
    {
        this.listRouteShort = listRouteShort;
    }

    public ArrayList<Frame> getListRouteAux()
    {
        return listRouteAux;
    }

    public void setListRouteAux(ArrayList<Frame> listRouteAux)
    {
        this.listRouteAux = listRouteAux;
    }

    @Override
    public void run()
    {
        try {
          
            movingImage();
        } catch (Exception e) {

        }

    }
}
