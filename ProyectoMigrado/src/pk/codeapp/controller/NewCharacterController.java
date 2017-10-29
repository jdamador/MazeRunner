/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.codeapp.controller;

import java.util.ArrayList;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;

/**
 *
 * @author Daniel Amador
 */
public class NewCharacterController
{

    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route
    /*Root graph */
    private Frame graphRoot;
    private Frame backup;
    private Frame destiny;

    public NewCharacterController(Frame characterLocation, Frame graphRoot, Frame destiny)
    {

        this.backup = characterLocation;
        this.graphRoot = graphRoot;
        this.destiny = destiny;
        controllerMethods();
    }

    public void controllerMethods()
    {
        /*Clean Marks*/
        cleanMark();
        /*Search the short route*/
        shortRouteJumps(backup, destiny);
        System.out.println(listRouteShort.size());
        for (int i = 0; i < listRouteShort.size(); i++) {
            System.out.println("Lista: "+ listRouteShort.get(i).getName());
        }
    }

    /**
     * Clean mark for each frame
     */
    public void cleanMark()
    {
        Frame temp = graphRoot;
        if (graphRoot == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }
    /**
     * Search the short route
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
}
