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

        clean_Mark();
        Frame destiny = searchCharacter(this.destiny.getName());
        Frame origin = searchCharacter(backupRoot.getName());
       
        under(characterRoot);
//        shortRouteJumps(origin, destiny);
//        System.out.println(listRouteShort.size());
//        System.out.println("moving");
//        int cont = 0;
//        while (true) {
//            characterActual.setLocation(listRouteShort.get(cont).getRow() * 80, listRouteShort.get(cont).getColumn() * 80);
//            Thread.sleep(sleep);
//            cont += 1;
//        }

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
        Frame tempGraph = graphRoot;
        while (tempGraph != null) {
            Frame originGraph = null;
            if (tempGraph.isAllow()) {
                originGraph = searchCharacter(tempGraph.getName());
            }
            Link aux = tempGraph.getNextLink();
            while(aux!=null){
                if(aux.getDestiny().isAllow()){
                    Frame linkGraph = searchCharacter(aux.getDestiny().getName());
                    if(originGraph != null & linkGraph!=null){
                        insertLinkCharacter(originGraph, linkGraph,aux.getWeight());
                    }
                }
                aux = aux.getNextLink();
            }
            tempGraph=tempGraph.getNextFrame();
        }
    }
    private void insertFrameCharacter(Frame rootGraph) {
        //Add Frame
        Frame tempGraph = rootGraph;
        while (tempGraph != null) {
            Frame newFrame = new Frame(tempGraph.getName(), false, tempGraph.getRow(), tempGraph.getColumn());
            if (characterRoot == null) {
                characterRoot = newFrame;
            } else {
                if (tempGraph.isAllow() == true) {
                    newFrame.setNextFrame(characterRoot);
                    characterRoot = newFrame;
                }
            }
            tempGraph = tempGraph.getNextFrame();
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

    public Frame searchCharacter(String name) {
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
            System.out.println("ENTRO NULO");
            return;
        }
        if (origin.isMark() == true) {
            System.out.println("ENTRO MARCA");
            return;
        } else {
            origin.setMark(true);
            Link aux = origin.getNextLink();
            listRouteAux.add(origin);
            System.out.println("AGREGO EN LIST AUX: " + origin.getName());
            if (aux == null) {
                System.out.println("ELIMINO ARRIBA");
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
                            System.out.println("AGREGO EN LIST: " + listRouteShort.get(i).getName());
                        }
                        listRouteShort.add(aux.getDestiny());
                        System.out.println("AGREGO EN LIST: " + aux.getDestiny().getName());
                    }
                }
                shortRouteJumps(aux.getDestiny(), destination);
                aux = aux.getNextLink();
                if (aux == null) {
                    System.out.println("ELIMINO: " + listRouteAux.get(listRouteAux.size() - 1).getName());
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
