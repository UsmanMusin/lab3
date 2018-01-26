import java.util.ArrayList;
import java.util.List;

public class OktmoGroup {
    private Oktmolevel level;
    private String name;
    private long code;
    ArrayList <OktmoGroup> innerGroups = new ArrayList<>();

    public OktmoGroup(long code, String name, Oktmolevel level){
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public void addInnerGroups(OktmoGroup group){
        innerGroups.add(group);
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<OktmoGroup> getInnerGroups(){
        return innerGroups;
    }

    public Oktmolevel getLevel() {
        return level;
    }


    @Override
    public String toString(){
        return Long.toString(code)+" "+name+" "+level;
    }
}
