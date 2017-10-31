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
 * @author Jose Pablo Brenes
 */
public class NewCharacterController
{

    /*Cont of the Methods*/
 /*List of Routes*/
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route
    private ArrayList<Frame> realRoute = new ArrayList(); //Principal list to know the short route
    /*Root graph */
    private Frame graphRoot;
    private Frame backup;
    private Frame destiny;
    private int contRoad = 0;
    private int contRoadAux = 0;

    public NewCharacterController(Frame characterLocation, Frame graphRoot, Frame destiny)
    {
        this.backup = characterLocation;
        this.graphRoot = graphRoot;
        this.destiny = destiny;
        controllerMethods();
    }

    /**
     * Controller Methods to Character
     */
    public void controllerMethods()
    {
        /*Clean Marks*/
        cleanMark();
        /*Search the short route*/
        shortRouteJumps(backup, destiny);
        bakupList();
        isTelepotation();
        // badElement();
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
     *
     * @param origin
     * @param destination
     */
    public void shortRouteJumps(Frame origin, Frame destination)
    {

        if (origin.isMark() | origin == null) {
            return;
        }
        if (origin.equals(destination)) {
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
                origin.setMark(false);
                listRouteAux.remove(listRouteAux.size() - 1);
                return;
            }
        }
    }

    public ArrayList<Frame> getListRouteShort()
    {
        return realRoute;
    }

    private void bakupList()
    {
        for (int i = 0; i < listRouteShort.size(); i++) {
            realRoute.add(listRouteShort.get(i));
        }
    }

    public void shortRouteWeight(Frame origin, Frame destination)
    {
        if (origin.isMark() | origin == null) {
            return;
        }
        if (origin.equals(destination)) {
            return;
        }
        if (origin.getBonus() != null) {
            contRoadAux += origin.getBonus().getWeight();
        }
        origin.setMark(true);
        listRouteAux.add(origin);
        contRoadAux++;
        Link aux = origin.getNextLink();

        if (aux == null) {
            listRouteAux.remove(listRouteAux.size() - 1);
            contRoadAux--;
            return;
        }
        while (aux != null) {
            if (aux.getDestiny().equals(destination)) {
                if (contRoad == 0 || contRoadAux < contRoad) {
                    listRouteShort.clear();
                    for (int i = 0; i < listRouteAux.size(); i++) {
                        listRouteShort.add(listRouteAux.get(i));
                    }
                    listRouteShort.add(destination);
                    contRoad = contRoadAux;
                    contRoadAux = 0;
                }
            }
            shortRouteJumps(aux.getDestiny(), destination);
            aux = aux.getNextLink();
            if (aux == null) {
                origin.setMark(false);
                listRouteAux.remove(listRouteAux.size() - 1);
                contRoadAux--;
                return;
            }
        }
    }

    private void badElement()
    {
        if (isSomeBadHere()) {
            listRouteAux.clear();
            listRouteShort.clear();
            shortRouteWeight(backup, destiny);
            realRoute.clear();
            bakupList();
        }
    }

    private boolean isSomeBadHere()
    {
        for (int i = 0; i < listRouteShort.size(); i++) {
            Frame reco = listRouteShort.get(i);
            if (reco.getBonus() != null) {
                if (reco.getBonus().getName().equals("Wait N seconds")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void isTelepotation()
    {
        for (int i = 0; i < realRoute.size(); i++) {
            Frame reco = listRouteShort.get(i);
            if (reco.getBonus() != null) {
                if (reco.getBonus().getName().equals("Teleportation")) {
                    cleanList();
                    Frame aux = searchSecond(reco);
                    shortRouteJumps(aux, destiny);
                    if (realRoute.size() - i >= listRouteShort.size()) {
                        for (int j = 0; j < listRouteShort.size(); j++) {
                            realRoute.add(listRouteShort.get(j));
                        }
                        break;
                    }
                }
            }
        }
    }

    public Frame searchSecond(Frame first)
    {
        Frame reco = NewMazeController.endMaze;
        while (reco != null) {
            if (reco.getBonus() != null) {
                if (reco.getBonus().getName().equals(first.getBonus().getName())) {
                    if (reco != first) {
                        return reco;
                    }
                }
            }
        }
        return null;
    }

    public void cleanList()
    {
        listRouteAux.clear();
        listRouteShort.clear();
    }
}
