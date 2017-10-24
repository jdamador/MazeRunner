
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
    ArrayList<Frame> listRoute = new ArrayList();
    ArrayList<Frame> listRouteShort = new ArrayList();
        ArrayList<Frame> listRoadRoute = new ArrayList();
    ArrayList<Frame> listRoadRouteShort = new ArrayList();

    private int counterWeight = 0;
    private int Weight = 0;
    /**
     * This method calc a random number
     *
     * @param limit
     * @return
     */
    
    private Frame GlobalOriginP1;

    public int getRandom(int minimum, int maximum) {
        int randomInt = minimum + (int) (Math.random() * maximum);
        return randomInt;
    }

    public void numbreCharacters() {
        numberCharacters = getRandom(2, 3);
    }

    public void shortRouteJumps(Frame origin, Frame destination,Frame GlobalOrigin,Frame pointerCharacter) {
        if (origin == null) {
            System.out.println("Nulo");
            return;
        }
        if (origin.isMark() == true) {
            System.out.println("marca");
            if (origin == GlobalOrigin) {
                clean_Mark(pointerCharacter);
            }
        }
        origin.setMark(true);
        Link aux = origin.getNextLink();
        System.out.println("Agrego: " + origin.getName());
        listRoute.add(origin);
        while (aux != null) {
            if (aux.getDestiny().equals(destination)) {
                if (listRouteShort.size() == 0) {
                    listRoute.add(aux.getDestiny());
                    for (int i = 0; i < listRoute.size(); i++) {
                        listRouteShort.add(listRoute.get(i));
                    }
                    return;
                }
                if (listRoute.size() < listRouteShort.size()) {
                     listRoute.add(aux.getDestiny());
                    for (int i = 0; i < listRoute.size(); i++) {
                        listRouteShort.add(listRoute.get(i));
                    }
                    return;
                }
            }
            shortRouteJumps(aux.getDestiny(), destination,GlobalOrigin,pointerCharacter);
            aux = aux.getNextLink();

        }
    }
    public void roadShortJumps(Frame origin, Frame destination,Frame globalOrigin,Frame pointerCharacter)
        {
             if (origin == null){
                 System.out.println("Nulo");
                return;}
             if (origin.isMark() == true){
                 System.out.println("marca");
              if(origin==globalOrigin){
                    clean_Mark(pointerCharacter);
              }
                    } 
                origin.setMark(true);
                Link aux = origin.getNextLink();
                System.out.println("Agrego: "+origin.getName());
                listRoadRouteShort.add(origin);
                while (aux != null)
                {
                   if(aux.getDestiny().equals(destination)){
                       listRoadRouteShort.add(destination);
                         if(listRoadRoute.size()==0){ 
                               listRoadRoute=listRoadRouteShort;
                               listRoadRouteShort.add(globalOrigin);
                           return;
                       }
                       if(listRoadRouteShort.size()> listRoadRoute.size()){
                           System.out.println("Listas Iguales");
                           listRoadRoute=listRoadRouteShort;
                           }
                      listRoadRouteShort.add(globalOrigin);
                   }
                   roadShortJumps(aux.getDestiny(), destination, globalOrigin,pointerCharacter);
                    aux = aux.getNextLink();
            }
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
 
    
    public Frame getGlobalOriginP1() {
        return GlobalOriginP1;
    }

    public void setGlobalOriginP1(Frame GlobalOrigin) {
        this.GlobalOriginP1 = GlobalOrigin;
    }

    public ArrayList<Frame> getListRoute() {
        return listRoute;
    }

    public void setListRoute(ArrayList<Frame> listRoute) {
        this.listRoute = listRoute;
    }

    public ArrayList<Frame> getListRouteShort() {
        return listRouteShort;
    }

    public void setListRouteShort(ArrayList<Frame> listRouteShort) {
        this.listRouteShort = listRouteShort;
    }

    public ArrayList<Frame> getListRoadRoute() {
        return listRoadRoute;
    }

    public void setListRoadRoute(ArrayList<Frame> listRoadRoute) {
        this.listRoadRoute = listRoadRoute;
    }

    public ArrayList<Frame> getListRoadRouteShort() {
        return listRoadRouteShort;
    }

    public void setListRoadRouteShort(ArrayList<Frame> listRoadRouteShort) {
        this.listRoadRouteShort = listRoadRouteShort;
    }


}
