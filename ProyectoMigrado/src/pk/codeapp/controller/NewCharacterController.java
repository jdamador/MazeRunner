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
public class NewCharacterController {

    /*Cont of the Methods*/
    private int contTeletrasport1 = 0;
    private int contTeletrasport2 = 0;
    private int contRoad = 0;
    private int contRoadAux = 0;
    /*List of Routes*/
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route
    private ArrayList<Frame> listRouteAuxTeleport = new ArrayList(); //Auxiliary list to know the short route of Teleport2
    private ArrayList<Frame> listRouteShortTeleport = new ArrayList(); //Principal list to know the short route of Teleport2
    ArrayList<Frame> backUpList = new ArrayList();
    /*Root graph */
    private Frame teletrasportation1;
    private Frame teletrasportation2;
    private Frame graphRoot;
    private Frame backup;
    private Frame destiny;

    public NewCharacterController(Frame characterLocation, Frame graphRoot, Frame destiny) {
        this.backup = characterLocation;
        this.graphRoot = graphRoot;
        this.destiny = destiny;
        controllerMethods();
    }

    /**
     * Controller Methods to Character
     */
    public void controllerMethods() {
        /*Clean Marks*/
        cleanMark();
        /*Search the short route*/
        shortRouteWeight(backup, destiny);

        for (int i = 0; i < listRouteShort.size(); i++) {
            backUpList.add(listRouteShort.get(i));
        }
        if (isTeleportInRoute() != null) {
            this.teletrasportation1 = isTeleportInRoute();
            this.teletrasportation2 = getSecondTeleport(teletrasportation1);

            if (teletrasportation1 != null & teletrasportation2 != null) {
                System.out.println("Entro en teleport para dividir");
                ArrayList<Frame> listForTeleport = new ArrayList();
                for (int i = 0; i < listRouteShort.size(); i++) {
                    if (listRouteShort.get(i).equals(teletrasportation1)) {
                        listForTeleport.add(listRouteShort.get(i));
                        System.out.println("ENtro en 1");
                        break;
                    }
                    listForTeleport.add(listRouteShort.get(i));
                }
                System.out.println("limpiar");
                clearAll();
                shortRouteWeight(teletrasportation2, destiny);
                for (int i = 0; i < listRouteShort.size(); i++) {
                    System.out.println("Agrego en teleport: " + listRouteShort.get(i).getName());
                    listForTeleport.add(listRouteShort.get(i));
                }
                int sizeOfTeleport2 = contRoad;
                clearAll();
                shortRouteWeight(teletrasportation1, destiny);
                int sizeOfTeleport1 = contRoad;
                if (sizeOfTeleport2 < sizeOfTeleport1) {
                    System.out.println("Es mas corta");
                    for (int i = 0; i < listForTeleport.size(); i++) {

                        listRouteShort.add(listForTeleport.get(i));
                    
                    }}

            }
        }

    }
    private void markInGraph(Frame origin){
        Frame temp = NewMazeController.startMaze;
        while(temp!=null){
            if(temp.equals(origin))
                temp.setMark(true);
            temp = temp.getNextFrame();
        }
    }
    private void clearAll() {
        listRouteAux.clear();
        this.contRoad = 0;
        this.contRoadAux = 0;
        listRouteShort.clear();
    }

    private Frame getSecondTeleport(Frame teleport1) {
        Frame reco = NewMazeController.startMaze;
        System.out.println("Entro a buscar teleport 2");
        while (reco != null) {
            if (reco.getBonus() != null) {
                if (reco.getBonus().getName().equals("Teleportation") & reco != teleport1) {

                    System.out.println("La encontro");
                    return reco;
                }
            }
            reco = reco.getNextFrame();
        }
        return null;
    }

    private Frame isTeleportInRoute() {
        for (int i = 0; i < listRouteShort.size(); i++) {
            if (listRouteShort.get(i).getBonus() != null) {
                if (listRouteShort.get(i).getBonus().getName().equals("Teleportation")) {
                    return listRouteShort.get(i);
                }
            }
        }
        return null;
    }

    public void shortRouteWeight(Frame origin, Frame destination) {
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
            if (listRouteAux.get(listRouteAux.size() - 1).getBonus() != null) {
                if (listRouteAux.get(listRouteAux.size() - 1).getBonus().isIsGood() == false) {
                    contRoadAux -= listRouteAux.get(listRouteAux.size() - 1).getBonus().getWeight();
                }
            }
            listRouteAux.remove(listRouteAux.size() - 1);
            if (contRoadAux > 0) {
                contRoadAux--;
            }
            return;
        }
        while (aux != null) {
            if (aux.getDestiny().equals(destination)) {
                System.out.println("Destination");
                if (contRoad == 0 | contRoadAux < contRoad) {
                    listRouteShort.clear();
                    for (int i = 0; i < listRouteAux.size(); i++) {
                        listRouteShort.add(listRouteAux.get(i));
                    }
                    listRouteShort.add(destination);
                    contRoad = contRoadAux;

                }
            }
            shortRouteWeight(aux.getDestiny(), destination);
            aux = aux.getNextLink();
            if (aux == null) {
                if (listRouteAux.get(listRouteAux.size() - 1).getBonus() != null) {
                    if (listRouteAux.get(listRouteAux.size() - 1).getBonus().isIsGood() == false) {
                        contRoadAux -= listRouteAux.get(listRouteAux.size() - 1).getBonus().getWeight();
                    }
                }
                origin.setMark(false);
                listRouteAux.remove(listRouteAux.size() - 1);
                if (contRoadAux > 0) {
                    contRoadAux--;
                }
                return;
            }
        }
    }

    /**
     * Clean mark for each frame
     */
    public void cleanMark() {
        Frame temp = graphRoot;
        if (graphRoot == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }

    public ArrayList<Frame> getListRouteShort() {
        return listRouteShort;
    }

}
