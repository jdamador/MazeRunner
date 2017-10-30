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
    private int contRoadToWin = 0;
    private int contRoad=0;
    private int contRoadAux=0;
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
        shortRouteJumps(backup, destiny);
        int contRoads = contRoadToWin-1;
        for (int i = 0; i < contRoads; i++) {
            if (therIsSomethingBadThere()) {
                if(teletrasportation1!=null && teletrasportation2!=null){
                    cutList(teletrasportation1);
                     shortRouteJumpsForTeletrasportation2(backup, destiny);
                     for (int j = 0; j < listRouteShortTeleport.size(); j++) {
                    listRouteShort.add(listRouteShortTeleport.get(j));}
                }else{
                markRoad();
                listRouteShort.clear();
                listRouteAux.clear();
                shortRouteJumps(backup, destiny);}
            }
        }
        if(listRouteShort.isEmpty()){
            cleanMark();
            listRouteAux.clear();
            listRouteShort.clear();
            this.contRoad=0;
            this.contRoadAux=0;
            shortRouteWeight(backup,destiny);
        }
    }
    public void cutList(Frame teletrasportation1){
        int numCut=0;
        for (int i = 0; i < listRouteShort.size(); i++) {
            if(listRouteShort.get(i).equals(teletrasportation1))
                numCut=i;
        }
        for (int i = 0; i < listRouteShort.size(); i++) {
            if(i>numCut){ 
                listRouteShort.remove(i);
            }
        }
    }
    public void shortRouteWeight(Frame origin,Frame destination){
        if (origin.isMark() | origin == null) {
            return;
        }
        if (origin.equals(destination)) {
            return;
        }
        if(!origin.getBonus().equals(null)){
            contRoadAux+=origin.getBonus().getWeight();
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
                if (contRoad==0|| contRoadAux< contRoad) {
                    listRouteShort.clear();
                    for (int i = 0; i < listRouteAux.size(); i++) {
                        listRouteShort.add(listRouteAux.get(i));
                    }
                    listRouteShort.add(destination);
                    contRoad=contRoadAux;
                    contRoadAux=0;
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
    private void markRoad() {
        for (int i = 1; i < listRouteShort.size() - 1; i++) {
            listRouteShort.get(i).setMark(true);
        }
    }

    /**
     * Verify that it do not have bad bonuses
     *
     * @return
     */
    private boolean therIsSomethingBadThere() {
        // "Teleportation", "Wait N seconds "
        for (int i = 0; i < listRouteShort.size(); i++) {
            if (("Wait N seconds ".equals(listRouteShort.get(i).getBonus().getName()))) {
                return true;
            } else if (("Teleportation".equals(listRouteShort.get(i).getBonus().getName()))) {
                return isBadTeleportation(listRouteShort.get(i));
            }
        }
        return false;
    }

    /**
     * Verify to Teletrasportation is bad
     *
     * @return
     */
    private boolean isBadTeleportation(Frame teleport1) {
        this.contTeletrasport1 = 0;
        this.contTeletrasport2 = 0;
        teletrasportation1 = teleport1;
        teletrasportation2 = searchTeleprotInGraph("Teleportation", teleport1);
        if (teletrasportation1 != null && teletrasportation2 != null) {
            teleportationDistance(teletrasportation1, 0, "Teleportation1");
            teleportationDistance(teletrasportation2, 0, "Teleportation2");
            if (contTeletrasport1 < contTeletrasport2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtain Distance of Teletransport to Cup
     *
     * @param origin
     * @param cont
     * @param type
     */
    private void teleportationDistance(Frame origin, int cont, String type) {
        if (origin.isMark() | origin == null) {
            return;
        }
        origin.setMark(true);
        cont++;
        Link aux = origin.getNextLink();
        if (aux == null) {
            cont--;
            return;
        }
        while (aux != null) {
            if (aux.getDestiny().equals(destiny)) {
                if (type.equals("Teleportation1")) {
                    if (contTeletrasport1 == 0 || cont < contTeletrasport1) {
                        this.contTeletrasport1 = cont + 1;
                    }
                } else {
                    if (contTeletrasport2 == 0 || cont < contTeletrasport2) {
                        this.contTeletrasport2 = cont + 1;
                    }
                }
            }
            teleportationDistance(aux.getDestiny(), cont, type);
            aux = aux.getNextLink();
            if (aux == null) {
                origin.setMark(false);
                cont--;
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

    /**
     * Search the short route
     *
     * @param origin
     * @param destination
     */
    public void shortRouteJumps(Frame origin, Frame destination) {

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

      /**
     * Search the short route for teleport 2
     *
     * @param origin
     * @param destination
     */
    public void shortRouteJumpsForTeletrasportation2(Frame origin, Frame destination) {

        if (origin.isMark() | origin == null) {
            return;
        }
        if (origin.equals(destination)) {
            return;
        }
        origin.setMark(true);
        listRouteAuxTeleport.add(origin);
        Link aux = origin.getNextLink();

        if (aux == null) {
            listRouteAuxTeleport.remove(listRouteAuxTeleport.size() - 1);
            return;
        }
        while (aux != null) {
            if (aux.getDestiny().equals(destination)) {
                if ((listRouteShortTeleport.isEmpty()) || (listRouteAuxTeleport.size() < listRouteShortTeleport.size())) {
                    listRouteShortTeleport.clear();
                    for (int i = 0; i < listRouteAuxTeleport.size(); i++) {
                        listRouteShortTeleport.add(listRouteAuxTeleport.get(i));
                    }
                    listRouteShortTeleport.add(destination);
                }
            }
            shortRouteJumps(aux.getDestiny(), destination);
            aux = aux.getNextLink();
            if (aux == null) {
                origin.setMark(false);
                listRouteAuxTeleport.remove(listRouteAuxTeleport.size() - 1);
                return;
            }
        }
    }
    public Frame searchTeleprotInGraph(String search, Frame notSearch) {
        Frame reco = graphRoot;
        while (reco != null) {
            if (reco.getName().equals(search) && !reco.equals(notSearch)) {
                return reco;
            }
            reco = reco.getNextFrame();
        }
        return null;
    }

    public ArrayList<Frame> getListRouteShort() {
        return listRouteShort;
    }

}
