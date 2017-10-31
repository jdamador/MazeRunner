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
    private int contTeletrasport1 = 0;
    private int contTeletrasport2 = 0;
    private int contRoadToWin = 0;
    private int contRoad = 0;
    private int contRoadAux = 0;
    /*List of Routes*/
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route
    private ArrayList<Frame> listRouteAuxTeleport = new ArrayList(); //Auxiliary list to know the short route of Teleport2
    private ArrayList<Frame> listRouteShortTeleport = new ArrayList(); //Principal list to know the short route of Teleport2
    /*Root graph */
    private Frame teletrasportation1;
    private Frame teletrasportation2;
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

    /**
     * Controller Methods to Character
     */
    public void controllerMethods()
    {
        /*Clean Marks*/
        cleanMark();
        /*Search the short route*/
        shortRouteWeight(backup, destiny);
        System.out.println("Origen: " + backup.getName());
        int contRoads = contRoadToWin - 1;
        System.out.println("Contador de caminos: " + contRoads);
        for (int i = 0; i < contRoads; i++) {

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
            if (origin.getBonus().isIsGood() == false) {
                contRoadAux += origin.getBonus().getWeight();
            }
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
                this.contRoadToWin++;
                if ((contRoad == 0) || (contRoadAux < contRoad)) {
                    listRouteShort.clear();
                    for (int i = 0; i < listRouteAux.size(); i++) {
                        listRouteShort.add(listRouteAux.get(i));
                    }
                    listRouteShort.add(destination);
                    contRoad = contRoadAux;
                    contRoadAux = 0;
                }
            }
            shortRouteWeight(aux.getDestiny(), destination);
            aux = aux.getNextLink();
            if (aux == null) {
                origin.setMark(false);
                listRouteAux.remove(listRouteAux.size() - 1);
                contRoadAux--;
                return;
            }
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

    public ArrayList<Frame> getListRouteShort()
    {
        return listRouteShort;
    }

}
