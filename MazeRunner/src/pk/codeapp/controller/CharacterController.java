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
public class CharacterController implements Runnable {

    private int numberCharacters;
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route

    private int counterWeight = 0;
    private int Weight = 0;
    private int posX;
    private int posY;
    private JLabel characterActual;
    private int sleep;
    private Frame characterRoot, backupRoot;
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
    public CharacterController(JLabel image, int sleep, int posX, int posY, Frame characterRoot, Frame graphRoot, Frame destiny) {
        this.characterActual = image;
        this.sleep = sleep;
        this.posX = posX;
        this.posY = posY;

        this.characterRoot = new Frame(characterRoot.getName(), false, characterRoot.getRow(), characterRoot.getColumn());
        this.backupRoot = characterRoot;
        this.graphRoot = graphRoot;
        this.destiny = new Frame(destiny.getName(), false, destiny.getRow(), destiny.getColumn());
    }

    public void movingImage() throws InterruptedException {

        createGraphForCharacter(graphRoot);

        System.out.println("moving");
        System.out.println("Origen: " + backupRoot.getName() + " Destino: " + destiny.getName());
        clean_Mark();
        shortRouteJumps(backupRoot, destiny);
        System.out.println(listRouteShort.size());
        System.out.println("moving");
        int cont = 0;
        while (true) {
            characterActual.setLocation(listRouteShort.get(cont).getRow() * 80, listRouteShort.get(cont).getColumn() * 80);
            Thread.sleep(sleep);
            cont += 1;
        }

    }

    public void under(Frame reco) {
        if (reco == null) {
            return;
        }
        if (reco.isMark()) {
            return;
        } else {
            reco.setMark(true);
            Link aux = reco.getNextLink();
            while (aux != null) {
                System.out.println("Origin: " + reco.getName() + " " + "Destiny: " + aux.getDestiny().getName());
                under(aux.getDestiny());
                aux = aux.getNextLink();
            }
        }
    }

    public void createGraphForCharacter(Frame graphRoot) {
        insertFrameCharacter(graphRoot);
        makeLink(graphRoot);
    }

    private void makeLink(Frame graphRoot) {
        if (graphRoot == null) {
            return;
        }
        if (graphRoot.isMark()) {
            return;
        } else {
            graphRoot.setMark(true);
            Link aux = graphRoot.getNextLink();
            Frame origin = searchCharacter(graphRoot.getName());
            if (origin != null) {
                while (aux != null) {
                    if (aux.getDestiny().isAllow()) {
                        Frame destiny = searchCharacter(aux.getDestiny().getName());
                        insertLinkCharacter(origin, destiny, aux.getWeight());
                    }
                    makeLink(aux.getDestiny());
                    aux = aux.getNextLink();
                }
            }
        }
    }

    private void insertFrameCharacter(Frame rootGraph) {
        //Add Frame
        Frame tempPrincipalTree = rootGraph;
        while (tempPrincipalTree != null) {
            Frame newFrame = new Frame(tempPrincipalTree.getName(), false, tempPrincipalTree.getRow(), tempPrincipalTree.getColumn());
            if (characterRoot == null) {
                characterRoot = newFrame;
            } else {
                if (tempPrincipalTree.isAllow() == true) {
                    newFrame.setNextFrame(characterRoot);
                    characterRoot = newFrame;
                }
            }
            tempPrincipalTree = tempPrincipalTree.getNextFrame();
        }
    }

    private void insertLinkCharacter(Frame origen, Frame destiny, int weight) {
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

    private Frame searchCharacter(String name) {
        Frame recoG = characterRoot;
        while (recoG != null) {
            if (recoG.getName().equals(name)) {
                return recoG;
            }
            recoG = recoG.getNextFrame();
        }
        return null;
    }

    /**
     * short route
     *
     * @param origin
     * @param destination
     */
    public void shortRouteJumps(Frame origin, Frame destination) {
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

    /**
     * Clean marks in each Frame
     *
     * @param pointerCharacter
     */
    public void clean_Mark() {
        Frame temp = characterRoot;
        if (characterRoot == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }

    public ArrayList<Frame> getListRoute() {
        return listRouteAux;
    }

    public void setListRoute(ArrayList<Frame> listRoute) {
        this.listRouteAux = listRoute;
    }

    public ArrayList<Frame> getListRouteShort() {
        return listRouteShort;
    }

    public void setListRouteShort(ArrayList<Frame> listRouteShort) {
        this.listRouteShort = listRouteShort;
    }

    public ArrayList<Frame> getListRouteAux() {
        return listRouteAux;
    }

    public void setListRouteAux(ArrayList<Frame> listRouteAux) {
        this.listRouteAux = listRouteAux;
    }

    @Override
    public void run() {
        try {

            movingImage();
        } catch (Exception e) {

        }

    }
}
