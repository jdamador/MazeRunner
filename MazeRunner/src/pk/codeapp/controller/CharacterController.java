
package pk.codeapp.controller;
import java.util.ArrayList;
import pk.codeapp.model.Frame;
import pk.codeapp.model.Link;

/**
 * This method is charge of all character's functions
 *
 * @author amador
 */
public class CharacterController {

    private int numberCharacters;
    private ArrayList<Frame> listRouteAux = new ArrayList(); //Auxiliary list to know the short route
    private ArrayList<Frame> listRouteShort = new ArrayList(); //Principal list to know the short route
    
    private int counterWeight = 0;
    private int Weight = 0;
    /**
     * This method calc a random number
     *
     * @param limit
     * @return
     */
    
    private Frame rootCharacter1; //Pointer for the first Character
    private Frame rootCharacter2; //Pointer for the second Character
    private Frame rootCharacter3; //Pointer for the third Character
    
    public void crateGraphForCharacter(Frame rootG,Frame principalG){
        insertFrameCharacter(rootG,principalG);
        //Add Linken
        Frame tempPrincipalG = principalG;
        Frame root=rootG;
        while(tempPrincipalG!=null){
             if(tempPrincipalG.isAllow()){
            Link aux=tempPrincipalG.getNextLink();
            while(aux!=null){
                Frame destiny= searchCharacter(aux.getDestiny().getName(),root);
                insertLinkCharacter(root,destiny,aux.getWeight());
                aux=aux.getNextLink();
            }
        } tempPrincipalG=tempPrincipalG.getNextFrame();
        }
       
        }
    private void insertFrameCharacter(Frame rootG,Frame principalG){
        //Add Frame
        Frame tempPrincipalTree = principalG;
        while(tempPrincipalTree!=null){
            Frame newFrame = new Frame(tempPrincipalTree.getName(), false,tempPrincipalTree.getRow(),tempPrincipalTree.getColumn());
            if(rootG ==null){
                rootG=newFrame;
            }else{
                if(tempPrincipalTree.isAllow()==true){
                    newFrame.setNextFrame(rootG);
                    rootG=newFrame;
                }
            }
            tempPrincipalTree = tempPrincipalTree.getNextFrame();
        }
    }
    private void insertLinkCharacter(Frame origen, Frame destiny, int weight){
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
    private Frame searchCharacter(String name,Frame rootCharacterG){
        Frame recoG=rootCharacterG;
        while(recoG!=null){
            if(recoG.getName().equals(name))
                return recoG;
            recoG=recoG.getNextFrame();
        }
        return null;
    }
    public int getRandom(int minimum, int maximum) {
        int randomInt = minimum + (int) (Math.random() * maximum);
        return randomInt;
    }

    public void numbreCharacters() {
        numberCharacters = getRandom(2, 3);
    }

    public void shortRouteJumps(Frame origin, Frame destination) {
        if (origin == null) {
            return;
        }
        else  if (origin.isMark() == true) {
            return;
        }
        else{
        origin.setMark(true);
        Link aux = origin.getNextLink();
        listRouteAux.add(origin);
        if(aux==null){
            listRouteAux.remove(listRouteAux.size()-1);
            return;
        }
        while (aux != null) {
            if(aux.getDestiny().isMark()==true){
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
            if(aux==null){
                listRouteAux.remove(listRouteAux.size()-1);
                return;
        }
        }}
    }
    
    public void clean_Mark(Frame pointerCharacter) {
        Frame temp = pointerCharacter;
        if (pointerCharacter == null) {
            return;
        }
        while (temp != null) {
            temp.setMark(false);
            temp = temp.getNextFrame();
        }
    }
 public void addCharacterFrame(String name, boolean token, int row, int column, boolean allow,Frame rootPointer) {
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

    public Frame getRootCharacter1() {
        return rootCharacter1;
    }

    public void setRootCharacter1(Frame rootCharacter1) {
        this.rootCharacter1 = rootCharacter1;
    }

    public Frame getRootCharacter2() {
        return rootCharacter2;
    }

    public void setRootCharacter2(Frame rootCharacter2) {
        this.rootCharacter2 = rootCharacter2;
    }

    public Frame getRootCharacter3() {
        return rootCharacter3;
    }

    public void setRootCharacter3(Frame rootCharacter3) {
        this.rootCharacter3 = rootCharacter3;
    }
    
    
}
