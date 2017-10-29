/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.ArrayList;
import java.util.Stack;
import static pk.codeapp.controller.MazeController.startMaze;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;

/**
 *
 * @author Daniel Amador
 */
public class CharacterController
{

    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route

    private int counterWeight = 0;
    private int Weight = 0;
    private Frame characterRoot, backupRoot, end;
    private Frame graphRoot;
    private Frame destiny;
   
    public CharacterController(Frame characterLocation, Frame graphRoot, Frame destiny)
    {
        this.characterRoot = null;
        this.backupRoot = characterLocation;
        this.graphRoot = graphRoot;
        this.destiny = destiny;
        createGraphForCharacter();
    }

    public void insertFramesGraph(Frame reco)
    {
        if (reco == null) {
            return;
        }
        if (reco.isMark()) {
            return;
        }
        if (reco.isAllow() == false) {
            return;
        } else {
            reco.setMark(true);
            Link aux = reco.getNextLink();
            Frame newFrame = new Frame(reco.getName(), false, reco.getRow(), reco.getColumn());
            newFrame.setAllow(reco.isAllow());
            if (characterRoot == null) {
                characterRoot = end = newFrame;
            } else {
                newFrame.setNextFrame(characterRoot);
                characterRoot = newFrame;
            }
            while (aux != null) {
                insertFramesGraph(aux.getDestiny());
                aux = aux.getNextLink();
            }
        }
    }

    public void createGraphForCharacter()
    {
        /*Add each frames*/
        insertFramesGraph(backupRoot);

        /*search the position in the graph*/
        Frame aux = searchCharacter(backupRoot.getName());
        Frame destiny = searchCharacter(this.destiny.getName());
        /*clean marks*/
        clean_Mark2();
        clean_Mark();
        /*make links*/
        makeLink(backupRoot, this.destiny);

        /*clean marks and arrays*/
        clean_Mark2();
        clean_Mark();
        listRouteAux.clear();
        listRouteShort.clear();

        /*show quantity of links*/
        //cantidadEnlaces();
        System.out.println("origen :" + aux.getName());
        /*Search short route*/
        depth(aux);
        shortRouteJumps(aux, destiny);
    }

    //<editor-fold desc="Get array" defaultstate="collapsed">
    public ArrayList<Frame> getArray()
    {
        return listRouteShort;
    }
    //</editor-fold>
    ArrayList<Frame> linking = new ArrayList();

    private void makeLink(Frame reco, Frame dest)
    {
        if (reco == null) {
            return;
        }
        if (reco.isMark()) {
            return;
        }
        if (reco.isAllow() == false) {
            return;
        }
        reco.setMark(true);
        linking.add(reco);
        Link aux = reco.getNextLink();
        if(reco==dest){
           linking(linking);
           return;
        }
        while (aux != null) {
            
            makeLink(aux.getDestiny(),dest);
            aux=aux.getNextLink();
        }
        

    }

    public void imprimir(ArrayList<Frame> list)
    {
        System.out.println("----------------------");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null);
            System.out.println(list.get(i).getName());

        }
    }

    public void linking(ArrayList<Frame> list)
    {
        for (int i = 0; i < list.size() - 1; i++) {

            Frame origin = searchCharacter(list.get(i).getName());
            Frame here = searchCharacter(list.get(i + 1).getName());
            if (origin != null && here != null) {
                insertLinkCharacter(origin, here, 1);
            }
        }

    }

    private boolean insertLinkCharacter(Frame origen, Frame destiny, int weight)
    {
        Link link = new Link(weight);
        link.setDestiny(destiny);
        if (origen.getNextLink() == null) {
            origen.setNextLink(link);
            return true;
        } else {
            Link temp = origen.getNextLink();
            boolean cond = false;
            while (temp != null) {
                if (temp.getDestiny() == destiny) {
                    cond = true;
                    break;
                }
                temp = temp.getNextLink();
            }
            if (!cond) {
                if (!existLink(destiny, origen)) {
                    link.setNextLink(origen.getNextLink());
                    origen.setNextLink(link);
                    return true;
                }

            }
            return false;
        }

    }

    public boolean existLink(Frame origin, Frame destiny)
    {
        if (origin.getNextLink() == null) {
            return false;
        } else {
            Link temp = origin.getNextLink();
            while (temp != null) {
                if (temp.getDestiny().getName().equals(destiny.getName())) {
                    return true;
                }
                temp = temp.getNextLink();
            }

        }
        return false;

    }

    public Frame searchCharacter(String name)
    {
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
    public void shortRouteJumps(Frame origin, Frame destination)
    {
        if (origin == null) {
            return;
        }
        if (origin.isMark()) {
            return;
        }
        origin.setMark(true);
        listRouteAux.add(origin);
        Link aux = origin.getNextLink();
        if (aux == null) {
            listRouteAux.remove(listRouteAux.size() - 1);
            return;
        }
        while (aux != null) {
            if (aux.getDestiny().isMark()) {
                aux.getDestiny().setMark(false);
            }

            if (aux.getDestiny().equals(destination)) {
                if ((listRouteShort.isEmpty()) || (listRouteAux.size() < listRouteShort.size())) {
                    listRouteShort.clear();
                    for (int i = 0; i < listRouteAux.size(); i++) {
                        listRouteShort.add(listRouteAux.get(i));
                    }
                    listRouteShort.add(destination);
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

    /**
     * Clean marks in each Frame
     *
     * @param pointerCharacter
     */
    public void clean_Mark()
    {
        Frame temp = characterRoot;
        if (characterRoot == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }

    public void clean_Mark2()
    {
        Frame temp = startMaze;
        if (startMaze == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Developer Methods">
    public void cantidadEnlaces()
    {
        Frame reco = characterRoot;
        while (reco != null) {
            int cont = 0;
            Link aux = reco.getNextLink();
            while (aux != null) {
                cont++;
                aux = aux.getNextLink();
            }
            System.out.println(reco.getName() + " cantidad enlaces: " + cont);
            reco = reco.getNextFrame();
        }
    }

    public void depth(Frame reco)
    {
        if (reco == null) {
            return;
        }
        if (reco.isMark()) {
            return;
        } else {
            reco.setMark(true);
            Link aux = reco.getNextLink();
            while (aux != null) {
                if (aux.getDestiny() != null) {
                    System.out.println(" Destiny: " + aux.getDestiny().getName());
                }
                depth(aux.getDestiny());
                aux = aux.getNextLink();

            }
        }
    }
    //</editor-fold>
}
