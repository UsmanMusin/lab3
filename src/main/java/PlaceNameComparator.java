import java.util.Comparator;

class PlaceNameComparator implements Comparator <Place>{
    public int compare(Place a, Place b){
        return a.getName().compareTo(b.getName());
    }
}
