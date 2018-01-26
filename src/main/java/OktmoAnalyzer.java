import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OktmoAnalyzer {
    public void test_first(OktmoData oktmoData) {
        boolean flag = false;
        ArrayList<Place> filteredList = new ArrayList<>();
        System.out.println("\nПрименили регуларные выражения:");
        Pattern p = Pattern.compile("^.{0,2}ово$");
        for (Place pl : oktmoData.placeList) {
            Matcher m = p.matcher(pl.getName());
            if (m.matches()) {
                flag = true;
                filteredList.add(pl);
                System.out.println(pl);
            }
        }
        if (flag) {
            for (Place filteredPlace : filteredList) {
                System.out.println(filteredPlace);
            }
        } else {
            System.out.println("Таких НП нет");
        }

    }

    public void test_second(OktmoData oktmoData) {
        boolean flag = false;
        ArrayList<Place> filteredList = new ArrayList<>();
        System.out.println("\nНП с названиями, которые начинаются и заканчиваются на одну и ту же согласную букву");
        Pattern p = Pattern.compile("^([бвгджйзклмнпрстфхцчшщ]).*(\\1)$",
                Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);
        for (Place pl : oktmoData.placeList) {
            Matcher m = p.matcher(pl.getName());
            if (m.matches()) {
                flag = true;
                filteredList.add(pl);
            }
        }
        if (flag) {
            for (Place filteredPlace : filteredList) {
                System.out.println(filteredPlace);
            }
        } else {
            System.out.println("Таких НП нет");
        }
    }


    public List<Place> findAllPlaceInRegion(String regionName, OktmoData oktmoData) {
        List<Place> places = new ArrayList<Place>();
        if(oktmoData.getGroupsName().containsKey(regionName)) {
            OktmoGroup group = oktmoData.getGroupsName().get(regionName);
            oktmoData.listPlacesToMap();

            switch (group.getLevel()) {
                case REGION:
                    places.addAll(oktmoData.getPlaceMap().subMap(group.getCode(),
                            (group.getCode() + 1000000000L)).values());
                    break;

                case RAYON:
                    places.addAll(oktmoData.getPlaceMap().subMap(group.getCode(),
                            (group.getCode() + 1000000L)).values());
                    break;

                case POSELENIE:
                    places.addAll(oktmoData.getPlaceMap().subMap(group.getCode(),
                            (group.getCode() + 1000L)).values());
                    break;
            }
            return places;
        }
        else  return places;
    }


    public void findMostPopularPlaceName(String regionName, OktmoData oktmoData){
        if(oktmoData.getGroupsName().containsKey(regionName)){
            ArrayList <String> placesInRegion = new ArrayList<>();
            for (Place place2: findAllPlaceInRegion(regionName, oktmoData)) {
                placesInRegion.add(place2.getName());
            }
            //System.out.println(placesInRegion);
            String place = null;
            long maxCount = 1;
            for (String place1: placesInRegion) {
                if(placesInRegion.stream().filter(place1::equals).count() > maxCount){
                    place = place1;
                    maxCount = placesInRegion.stream().filter(place1::equals).count();
                }
            }
            if(maxCount > 1){
                System.out.println(place);
            }
            else{
                System.out.println("Нет НП с повторяющимися названиями");
            }
        }
        else{
            System.out.println("Такого региона нет");
        }
    }

    public void printStatusTableForRegion(String regionName, OktmoData oktmoData){
        if(oktmoData.getGroupsName().containsKey(regionName)){
            List <Place> placesInRegion = findAllPlaceInRegion(regionName, oktmoData);
            Map<String, Long> mapPlaceEveryStatusCount = placesInRegion.stream()
                    .collect(Collectors.groupingBy(Place::getStatus, Collectors.counting()));
            System.out.println(mapPlaceEveryStatusCount);
        }
        else{
            System.out.println("Такого региона нет");
        }
    }

    public void printInnerGroups(String regionName, OktmoData oktmoData){
        if(oktmoData.getGroupsName().containsKey(regionName)){
            OktmoGroup group = oktmoData.getGroupsName().get(regionName);
            System.out.println("Количество внутренних групп:"+ group.getInnerGroups().size());
            for (OktmoGroup group1: group.getInnerGroups()) {
                System.out.println(group1);
            }
        }
        else{
            System.out.println("Такого региона нет");
        }
    }
}
