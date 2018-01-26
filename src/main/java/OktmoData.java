import java.util.*;

public class OktmoData {
    ArrayList <Place> placeList = new ArrayList<>();
    HashSet <String> allStatus = new HashSet<>();
    ArrayList <Place> sortedPlaceList = new ArrayList<>();
    private Map<String, OktmoGroup> groupsName = new HashMap<String, OktmoGroup>();
    private Map<Long, Place> placeMap = new TreeMap<Long, Place>();

    public void addPlace(Place place){
        placeList.add(place);
        if(!(place.getStatus().equals(""))){
            allStatus.add(place.getStatus());
        }

    }

    public void printAllStatus(){
        System.out.println(allStatus);
    }

    public void printOktmodata(){
        System.out.println("Считано НП: " + placeList.size());
        for (Place placeForPrint: placeList) {
            System.out.println(placeForPrint);
        }
    }
    public void sortPlace(){
        sortedPlaceList.clear();
        sortedPlaceList.addAll(placeList);
        Collections.sort(sortedPlaceList, new PlaceNameComparator());
        System.out.println("\bОтсортированный список по названию:");
        for(Place p: sortedPlaceList){
            System.out.println(p);
        }
    }


     public static boolean equalLists(OktmoData oktmoData1, OktmoData oktmoData2) {
         boolean flag = true;
         if (oktmoData1.placeList.size() != oktmoData2.placeList.size()) {
             return false;
         } else {

             for (int i = 0; i < oktmoData1.placeList.size(); i++) {
                 if (!oktmoData2.placeList.get(i).equals(oktmoData1.placeList.get(i))) {
                     flag = false;
                     break;
                 }
             }
             return flag;
         }
     }


    public void  addGroup(OktmoGroup group ){
        groupsName.put(group.getName(), group);
    }

    public void listPlacesToMap(){
        placeList.stream().forEach((p) -> placeMap.put(p.getCode(), p));
    }

    public TreeMap<Long, Place> getPlaceMap() {
        return (TreeMap<Long, Place>) placeMap;
    }
    public Map<String, OktmoGroup> getGroupsName() {
        return groupsName;
    }
    public void printGroups(){
        for (Map.Entry<String, OktmoGroup> entry : groupsName.entrySet()) {
            System.out.println("Key" + entry.getKey() + "  group" + entry.getValue());
        }
    }

}
